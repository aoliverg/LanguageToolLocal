import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LanguageToolTrayApp {

    private static final int PORT = 8081;
    private static final String SERVER_URL = "http://localhost:" + PORT;
    private static final String APP_NAME = "LanguageToolLocal";

    private static boolean isRunning = false;
    private static Thread serverThread;
    private static TrayIcon trayIcon;
    private static JFrame mainFrame;
    private static JLabel statusLabel;
    private static JButton toggleButton;
    private static JCheckBox startupCheckBox;
    private static MenuItem trayStatusItem;
    private static MenuItem trayToggleItem;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            createMainWindow();
            setupSystemTray();
            startServer();

            boolean startMinimized = args.length > 0 && args[0].equals("--minimized");
            if (!startMinimized) {
                mainFrame.setVisible(true);
            }
        });
    }

    // Calcula un factor d'escala real segons la resolució del monitor (soluciona el problema a Linux/Wayland 2K/4K)
    private static double getScaleFactor() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        double dpiScale = dpi / 96.0;

        // Si el sistema reporta DPI alt (ex. 144 o 192), fem servir aquest valor
        if (dpiScale > 1.1) {
            return dpiScale;
        }
        // Si Linux/Wayland reporta 96 DPI falsos però la pantalla té més de 2200px d'ample (2K/4K), escalem manualment
        if (screen.width >= 3200) {
            return 2.0;
        } else if (screen.width >= 2200) {
            return 1.5;
        }
        return 1.0;
    }

    private static int s(int basePixels, double scale) {
        return (int) Math.round(basePixels * scale);
    }

    private static void createMainWindow() {
        double scale = getScaleFactor();

        mainFrame = new JFrame("LanguageTool Local");
        mainFrame.setResizable(false);
        mainFrame.setIconImage(createStatusIcon(new Color(46, 125, 50)));
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(s(24, scale), s(28, scale), s(24, scale), s(28, scale)));
        panel.setPreferredSize(new Dimension(s(480, scale), s(295, scale)));

        // Títol
        JLabel titleLabel = new JLabel("Servidor Local de LanguageTool");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, s(17, scale)));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Estat
        statusLabel = new JLabel("Estat: Iniciant servidor...");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, s(14, scale)));
        statusLabel.setBorder(new EmptyBorder(s(10, scale), 0, s(18, scale), 0));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botó Encendre / Aturar
        toggleButton = new JButton("Aturar el servidor");
        toggleButton.setFont(new Font("SansSerif", Font.BOLD, s(13, scale)));
        toggleButton.setFocusPainted(false);
        toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        toggleButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, s(42, scale)));
        toggleButton.addActionListener(e -> {
            if (isRunning) stopServer();
            else startServer();
        });

        // Secció URL per al navegador
        JPanel urlPanel = new JPanel(new BorderLayout(s(10, scale), 0));
        urlPanel.setBorder(new EmptyBorder(s(16, scale), 0, s(12, scale), 0));
        urlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        urlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, s(58, scale)));

        JTextField urlField = new JTextField(SERVER_URL);
        urlField.setFont(new Font("SansSerif", Font.PLAIN, s(13, scale)));
        urlField.setEditable(false);
        urlField.setBackground(Color.WHITE);

        JButton copyButton = new JButton("Copia URL");
        copyButton.setFont(new Font("SansSerif", Font.PLAIN, s(13, scale)));
        copyButton.setFocusPainted(false);
        copyButton.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(SERVER_URL), null);
            copyButton.setText("Copiat!");
            Timer t = new Timer(2000, evt -> copyButton.setText("Copia URL"));
            t.setRepeats(false);
            t.start();
        });

        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.add(copyButton, BorderLayout.EAST);

        // Casella d'inici automàtic
        startupCheckBox = new JCheckBox("Arrenca automàticament en encendre l'ordinador");
        startupCheckBox.setFont(new Font("SansSerif", Font.PLAIN, s(13, scale)));
        startupCheckBox.setFocusPainted(false);
        startupCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        startupCheckBox.setSelected(isStartupEnabled());
        startupCheckBox.addActionListener(e -> toggleStartup(startupCheckBox.isSelected()));

        // Botó sortir completament
        JButton quitButton = new JButton("Tanca l'aplicació completament");
        quitButton.setFont(new Font("SansSerif", Font.PLAIN, s(12, scale)));
        quitButton.setForeground(Color.GRAY);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        quitButton.setBorder(new EmptyBorder(s(14, scale), 0, 0, 0));
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(titleLabel);
        panel.add(statusLabel);
        panel.add(toggleButton);
        panel.add(urlPanel);
        panel.add(startupCheckBox);
        panel.add(quitButton);

        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    private static void setupSystemTray() {
        if (!SystemTray.isSupported()) {
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        PopupMenu popup = new PopupMenu();

        trayStatusItem = new MenuItem("Estat: Iniciant...");
        trayStatusItem.setEnabled(false);

        trayToggleItem = new MenuItem("Aturar servidor");
        trayToggleItem.addActionListener(e -> {
            if (isRunning) stopServer();
            else startServer();
        });

        MenuItem openWindowItem = new MenuItem("Obre la finestra de control");
        openWindowItem.addActionListener(e -> {
            mainFrame.setVisible(true);
            mainFrame.toFront();
        });

        MenuItem exitItem = new MenuItem("Surt");
        exitItem.addActionListener(e -> System.exit(0));

        popup.add(trayStatusItem);
        popup.addSeparator();
        popup.add(trayToggleItem);
        popup.add(openWindowItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon = new TrayIcon(createStatusIcon(Color.ORANGE), "LanguageTool Local", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> {
            mainFrame.setVisible(true);
            mainFrame.toFront();
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    private static void startServer() {
        if (isRunning) return;

        if (isPortInUse(PORT)) {
            updateUIState(true, "En marxa (ja estava actiu al port " + PORT + ")");
            return;
        }

        serverThread = new Thread(() -> {
            try {
                Class<?> serverClass = Class.forName("org.languagetool.server.HTTPServer");
                Method mainMethod = serverClass.getMethod("main", String[].class);
                String[] serverArgs = {"--port", String.valueOf(PORT), "--allow-origin", "*"};

                SwingUtilities.invokeLater(() -> updateUIState(true, "En marxa (" + SERVER_URL + ")"));
                mainMethod.invoke(null, (Object) serverArgs);
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    updateUIState(false, "Error en iniciar el servidor");
                    JOptionPane.showMessageDialog(mainFrame,
                            "No s'ha pogut iniciar LanguageTool:\n" + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private static void stopServer() {
        int confirm = JOptionPane.showConfirmDialog(mainFrame,
                "Per alliberar la memòria RAM del servidor cal tancar l'aplicació.\nVols tancar-la ara?",
                "Aturar LanguageTool", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private static void updateUIState(boolean running, String message) {
        isRunning = running;
        Color color = running ? new Color(46, 125, 50) : new Color(198, 40, 40);

        statusLabel.setText("Estat: " + message);
        statusLabel.setForeground(color);
        toggleButton.setText(running ? "Aturar el servidor" : "Inicia el servidor");

        if (trayIcon != null) {
            trayIcon.setImage(createStatusIcon(color));
            trayStatusItem.setLabel("Estat: " + (running ? "Actiu (port " + PORT + ")" : "Aturat"));
            trayToggleItem.setLabel(running ? "Aturar servidor" : "Inicia servidor");
        }
    }

    private static boolean isPortInUse(int port) {
        try {
            HttpURLConnection conn = (HttpURLConnection) URI.create("http://localhost:" + port + "/v2/languages").toURL().openConnection();
            conn.setConnectTimeout(500);
            conn.setReadTimeout(500);
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private static Image createStatusIcon(Color bgColor) {
        int size = 32;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(bgColor);
        g.fillRoundRect(2, 2, size - 4, size - 4, 10, 10);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        FontMetrics fm = g.getFontMetrics();
        int x = (size - fm.stringWidth("LT")) / 2;
        int y = ((size - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString("LT", x, y);
        g.dispose();
        return img;
    }

    // --- GESTIÓ DE L'ARRENCADA AUTOMÀTICA (LINUX, WINDOWS I MAC) ---

    private static String getExecutableCommand() {
        return ProcessHandle.current().info().command().orElse("java");
    }

    private static boolean isStartupEnabled() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            Path desktopFile = Paths.get(System.getProperty("user.home"), ".config", "autostart", APP_NAME + ".desktop");
            return Files.exists(desktopFile);
        } else if (os.contains("win")) {
            Path lnk = Paths.get(System.getenv("APPDATA"), "Microsoft", "Windows", "Start Menu", "Programs", "Startup", APP_NAME + ".lnk");
            return Files.exists(lnk);
        } else if (os.contains("mac")) {
            Path plist = Paths.get(System.getProperty("user.home"), "Library", "LaunchAgents", "cat.languagetool.local.plist");
            return Files.exists(plist);
        }
        return false;
    }

    private static void toggleStartup(boolean enable) {
        String os = System.getProperty("os.name").toLowerCase();
        String execPath = getExecutableCommand();

        try {
            if (os.contains("linux")) {
                Path desktopFile = Paths.get(System.getProperty("user.home"), ".config", "autostart", APP_NAME + ".desktop");
                if (enable) {
                    Files.createDirectories(desktopFile.getParent());
                    String content = "[Desktop Entry]\n" +
                            "Type=Application\n" +
                            "Name=LanguageTool Local\n" +
                            "Exec=\"" + execPath + "\" --minimized\n" +
                            "Terminal=false\n" +
                            "X-GNOME-Autostart-enabled=true\n";
                    Files.writeString(desktopFile, content, StandardCharsets.UTF_8);
                } else {
                    Files.deleteIfExists(desktopFile);
                }
            } else if (os.contains("win")) {
                Path lnk = Paths.get(System.getenv("APPDATA"), "Microsoft", "Windows", "Start Menu", "Programs", "Startup", APP_NAME + ".lnk");
                if (enable) {
                    String psCommand = String.format(
                            "$s=(New-Object -COM WScript.Shell).CreateShortcut('%s');$s.TargetPath='%s';$s.Arguments='--minimized';$s.Save()",
                            lnk.toString().replace("'", "''"),
                            execPath.replace("'", "''")
                    );
                    new ProcessBuilder("powershell", "-NoProfile", "-Command", psCommand).start().waitFor();
                } else {
                    Files.deleteIfExists(lnk);
                }
            } else if (os.contains("mac")) {
                Path plist = Paths.get(System.getProperty("user.home"), "Library", "LaunchAgents", "cat.languagetool.local.plist");
                if (enable) {
                    Files.createDirectories(plist.getParent());
                    String plistContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                            "<plist version=\"1.0\">\n<dict>\n" +
                            "    <key>Label</key><string>cat.languagetool.local</string>\n" +
                            "    <key>ProgramArguments</key>\n" +
                            "    <array>\n" +
                            "        <string>" + execPath + "</string>\n" +
                            "        <string>--minimized</string>\n" +
                            "    </array>\n" +
                            "    <key>RunAtLoad</key><true/>\n" +
                            "</dict>\n</plist>";
                    Files.writeString(plist, plistContent, StandardCharsets.UTF_8);
                } else {
                    Files.deleteIfExists(plist);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "No s'ha pogut configurar l'inici automàtic:\n" + ex.getMessage());
            startupCheckBox.setSelected(!enable);
        }
    }
}
