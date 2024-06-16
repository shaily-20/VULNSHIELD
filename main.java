import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    public Main() {
        setTitle("VULNSHIELD");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        authenticateUser();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        createMenuBar();
        createTabs();

        setVisible(true);
    }

    private void authenticateUser() {
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Exiting application.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else {
            System.exit(0); // User clicked cancel or closed the dialog
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Notification button
        JButton notificationButton = new JButton();
        notificationButton.setIcon(new ImageIcon("download.png")); // Provide your notification icon path
        notificationButton.setToolTipText("Notifications");
        notificationButton.addActionListener(e -> showNotifications());
        menuBar.add(notificationButton);

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Settings menu
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> showAboutDialog());
        JMenuItem themeMenuItem = new JMenuItem("Theme");
        themeMenuItem.addActionListener(e -> showThemeDialog());
        JMenuItem accountMenuItem = new JMenuItem("Account");
        settingsMenu.add(aboutMenuItem);
        settingsMenu.add(themeMenuItem);
        settingsMenu.add(accountMenuItem);
        menuBar.add(settingsMenu);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem usageMenuItem = new JMenuItem("Usage Guide");
        usageMenuItem.addActionListener(e -> showUsageGuide());
        helpMenu.add(usageMenuItem);
        menuBar.add(helpMenu);

        // History menu
        JMenu historyMenu = new JMenu("History");
        JMenuItem viewHistoryMenuItem = new JMenuItem("View History");
        viewHistoryMenuItem.addActionListener(e -> showHistory());
        historyMenu.add(viewHistoryMenuItem);
        menuBar.add(historyMenu);

        setJMenuBar(menuBar);
    }

    private void createTabs() {
        tabbedPane = new JTabbedPane();
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("Dashboard", dashboardPanel);

        JPanel inputPanel = createInputPanel();
        tabbedPane.addTab("Input", inputPanel);

        JPanel scannerComparisonPanel = createScannerComparisonPanel();
        tabbedPane.addTab("Scanner Comparison", scannerComparisonPanel);

        JPanel researchPanel = createResearchPanel();
        tabbedPane.addTab("Research & Q&A", researchPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }
    private JPanel createResearchPanel() {
        JPanel researchPanel = new JPanel(new GridLayout(1, 2)); // Split panel into two parts

        // Q&A Panel
        JPanel qaPanel = new JPanel(new BorderLayout());
        JLabel qaLabel = new JLabel("<html><center><h1>QUESTION AND ANSWER</h1></center></html>");
        qaPanel.add(qaLabel, BorderLayout.NORTH);

        // Add questions dynamically
        List<String> questions = new ArrayList<>();
        questions.add("How can I prevent SQL injection vulnerabilities?");
        questions.add("What are the best practices for securing a web server?");
        questions.add("How does Cross-Site Scripting (XSS) work?");
        questions.add("Which vulnerability scanner is recommended for large-scale applications?");
        questions.add("What are the common signs of a denial of service (DoS) attack?");
        questions.add("How do commercial WVSs compare with open-source WVSs, in terms of detection capability, for all vulnerability types in web applications?");
        questions.add("How well do commercial WVSs compare with open-source WVSs in terms of the number of false-positives generated?");
        questions.add("Which WVS is the most effective for vulnerability detection?");

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));

        for (String question : questions) {
            JButton questionButton = new JButton(question);
            questionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            questionButton.addActionListener(e -> showAnswer(question));
            questionsPanel.add(questionButton);
        }

        JScrollPane questionsScrollPane = new JScrollPane(questionsPanel);
        qaPanel.add(questionsScrollPane, BorderLayout.CENTER);

        // Research Community Panel
        JPanel communityPanel = new JPanel(new BorderLayout());
        JLabel communityLabel = new JLabel("<html><h2>Research Community</h2>" +
                "<p>Email: research@example.com</p>" +
                "<p>Join our community to contribute and collaborate on web security research!</p></html>");
        communityLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        communityPanel.add(communityLabel, BorderLayout.NORTH);
        JLabel recentResearchLabel = new JLabel("<html><h2>Recent Research Topics</h2>" +
                "<ul>" +
                "<li>Analysis of Emerging XSS Attack Vectors</li>" +
                "<li>Comparative Study of WAFs for Web Application Security</li>" +
                "<li>Advanced Techniques in SQL Injection Prevention</li>" +
                "<li>Impact of API Security on Web Application Vulnerabilities</li>" +
                "<li>Machine Learning Approaches for Intrusion Detection</li>" +
                "</ul></html>");
        recentResearchLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        communityPanel.add(recentResearchLabel, BorderLayout.CENTER);

        // Contribution Opportunities
        JLabel contributeLabel = new JLabel("<html><h2>Contribute to Research</h2>" +
                "<p>Interested in contributing to ongoing projects or starting new research initiatives?</p>" +
                "<p>Contact us at research@example.com to get involved!</p></html>");
        contributeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        communityPanel.add(contributeLabel, BorderLayout.SOUTH);


        researchPanel.add(qaPanel);
        researchPanel.add(communityPanel);

        return researchPanel;
    }

    private void showAnswer(String question) {
        // Here you can implement logic to display answers based on the selected question
        String answer = getAnswerForQuestion(question);

        JOptionPane.showMessageDialog(this, answer, "Answer", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getAnswerForQuestion(String question) {
        // Example: provide static answers based on selected question
        switch (question) {
            case "How can I prevent SQL injection vulnerabilities?":
                return "To prevent SQL injection, use parameterized queries, input validation, and escape special characters.";
            case "What are the best practices for securing a web server?":
                return "Secure your web server by applying patches, using strong authentication, and configuring firewalls.";
            case "How does Cross-Site Scripting (XSS) work?":
                return "Cross-Site Scripting (XSS) involves injecting malicious scripts into web pages viewed by other users.";
            case "Which vulnerability scanner is recommended for large-scale applications?":
                return "Tools like Acunetix, AppScan, and Burp Suite are popular for large-scale vulnerability scanning.";
            case "What are the common signs of a denial of service (DoS) attack?":
                return "Common signs of a DoS attack include slow network performance, unavailability of services, and high resource usage.";
            case "How do commercial WVSs compare with open-source WVSs, in terms of detection capability, for all vulnerability types in web applications?":
                return "Commercial WVSs often have more advanced detection capabilities due to automated crawling and extensive vulnerability databases, while open-source WVSs rely on community contributions and may have varying coverage.";
            case "How well do commercial WVSs compare with open-source WVSs in terms of the number of false-positives generated?":
                return "Commercial WVSs typically exhibit lower false-positive rates compared to open-source WVSs, thanks to more refined scanning algorithms and dedicated support.";
            case "Which WVS is the most effective for vulnerability detection?":
                return "The effectiveness of a WVS can vary based on specific use cases and requirements. Tools like Acunetix, Burp Suite, and Netsparker are often recommended for comprehensive vulnerability detection.";
            default:
                return "Answer not available.";
        }
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());

        // About information with link, version, and OS details
        JLabel aboutLabel = new JLabel("<html><center><h1>Welcome to the Web Vulnerability Scanner Comparison Tool:VULNSHIELD</h1>" +

                "<p>This open-source GUI allows users to compare the effectiveness of various web vulnerability scanners.</p>" +

                "<p>Website: <a href=\"https://example.com\">www.vulnshield.com</a></p>" +
                "<p>Version: 1.0</p>" +
                "<p>Supported OS: Windows, macOS, Linux</p>" +
                "<p>Protocol Support : Get, post, cookie, header, secret, pname, custom, proxy, gzip, eflate, ssl.</p>"+
                "<p>Session Management : Custom cookie, custom, header, logout, detection, exclude, log-out, exclude, url, exclude, param</p>"+
                "<p>Type of Vulnerabilities: Denial of Service, Code Execution, Buffer Overflow, Authentication Flaws, Cross-Site Scripting XSS, Cross-Site-Request, SQL Injection, File inclusion, Reflected Cross-site scripting RXSS, Access Control Flaws</p>" +
                "<p>Examples of attacks include: command injection, buffer overflow, data or path manipulation, access control, session hijacking and cookie poisoning</center></p></html>");
        aboutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://example.com"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dashboardPanel.add(aboutLabel, BorderLayout.CENTER);

        // Progress bars for metrics
        JPanel progressPanel = new JPanel(new GridLayout(4, 1, 0, 5)); // 4 rows, 1 column, vertical gap of 5

        addProgressBar(progressPanel, "Market Value:", 55);
        addProgressBar(progressPanel, "Set of Vulnerabilities:", 70);
        addProgressBar(progressPanel, "User Adoption:", 62);
        addProgressBar(progressPanel, "Web Server Security:", 40);

        dashboardPanel.add(progressPanel, BorderLayout.SOUTH);

        return dashboardPanel;
    }

    private void addProgressBar(JPanel parentPanel, String label, int value) {
        JPanel progressBarPanel = new JPanel(new BorderLayout());
        JLabel progressLabel = new JLabel(label);
        JProgressBar progressBar = new JProgressBar(0, 100); // Progress bar range from 0 to 100
        progressBar.setValue(value);
        progressBar.setStringPainted(true); // Display percentage text on the progress bar
        progressBarPanel.add(progressLabel, BorderLayout.WEST);
        progressBarPanel.add(progressBar, BorderLayout.CENTER);
        parentPanel.add(progressBarPanel);
    }

    private void showNotifications() {
        // Simulate real-time notifications (replace this with actual implementation)
        String[] notifications = {
                "Notification1:new message from server",
                "Notification2:no new update",
                "Notification3:Server downtime tomorrow"
        };

        StringBuilder notificationMessage = new StringBuilder();
        for (String notification : notifications) {
            notificationMessage.append(notification).append("\n");
        }

        JOptionPane.showMessageDialog(this, notificationMessage.toString(), "Notifications", JOptionPane.INFORMATION_MESSAGE);
    }



    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "<html><h2>About This Application</h2>" +
                        "<p>This application is a Web Vulnerability Scanner Tool designed to help users assess and compare web vulnerability scanners.</p>" +
                        "<p>For more information, visit <a href=\"https://example.com\">webvulnscanner</a>.</p>" +
                        "<p>Version: 1.0<br>Supported OS: Windows, macOS, Linux</p></html>",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUsageGuide() {
        JOptionPane.showMessageDialog(this,
                "<html><h2>Web Vulnerability Scanner Usage Guide</h2>" +
                        "<p>1. Enter the IP or Host of the target web application.</p>" +
                        "<p>2. Select the desired Scan Types and Scan Level.</p>" +
                        "<p>3. Choose the method to export results (e.g., Phone Number, Email Address, Fax).</p>" +
                        "<p>4. Click the 'Scan' button to initiate the vulnerability scan.</p>" +
                        "<p>5. View scan results and recommendations in the popup dialog.</p>" +
                        "<p>For more details and options, explore the tabs in the application.</p></html>",
                "Usage Guide",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(10, 2, 10, 10));

        JLabel hostLabel = new JLabel("IP or Host:");
        JTextField hostTextField = new JTextField();
        fieldsPanel.add(hostLabel);
        fieldsPanel.add(hostTextField);

        JLabel scanTypeLabel = new JLabel("Scan Types:");
        JCheckBox basicScanCheckBox = new JCheckBox("Basic Scan");
        JCheckBox advanceScanCheckBox = new JCheckBox("Advance Scan");
        JCheckBox malwareScanCheckBox = new JCheckBox("Malware Scan");
        JCheckBox detectionCheckBox = new JCheckBox("Detection");
        JCheckBox xssCheckBox = new JCheckBox("XSS");
        JCheckBox sqlInjectionCheckBox = new JCheckBox("SQL Injection");
        fieldsPanel.add(scanTypeLabel);
        fieldsPanel.add(basicScanCheckBox);
        fieldsPanel.add(new JLabel());
        fieldsPanel.add(advanceScanCheckBox);
        fieldsPanel.add(new JLabel());
        fieldsPanel.add(malwareScanCheckBox);
        fieldsPanel.add(new JLabel());
        fieldsPanel.add(detectionCheckBox);
        fieldsPanel.add(new JLabel());
        fieldsPanel.add(xssCheckBox);
        fieldsPanel.add(new JLabel());
        fieldsPanel.add(sqlInjectionCheckBox);

        JLabel scanLevelLabel = new JLabel("Scan Level:");
        JComboBox<String> scanLevelComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Complex"});
        fieldsPanel.add(scanLevelLabel);
        fieldsPanel.add(scanLevelComboBox);

        JLabel exportLabel = new JLabel("Export Results To:");
        JComboBox<String> exportComboBox = new JComboBox<>(new String[]{"Phone Number", "Email Address", "Fax"});
        fieldsPanel.add(exportLabel);
        fieldsPanel.add(exportComboBox);

        JLabel exportIdLabel = new JLabel("Export ID:");
        JTextField exportIdTextField = new JTextField();
        fieldsPanel.add(exportIdLabel);
        fieldsPanel.add(exportIdTextField);

        inputPanel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton scanButton = new JButton("Scan");
        scanButton.addActionListener(e -> onScanClicked(hostTextField.getText(), scanLevelComboBox.getSelectedItem().toString(), exportIdTextField.getText()));
        buttonsPanel.add(scanButton);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        buttonsPanel.add(goBackButton);

        inputPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return inputPanel;
    }

    private void onScanClicked(String host, String scanLevel, String exportId) {
        String vulnerabilityType = "Denial of Service, Code Execution, Buffer Overflow, XSS, SQL Injection";
        String affectedHost = host;
        String recommendation = "To overcome these vulnerabilities, update software patches and use secure coding practices";
        String resource = "https://example.com/resources";
        String userInput = "IP or Host: " + host +
                "\nScan Types: Basic Scan, Advance Scan" +
                "\nScan Level: " + scanLevel;

        StringBuilder scanResultMessage = new StringBuilder();
        scanResultMessage.append("Scan Results:\n\n");
        scanResultMessage.append("1] Vulnerability Type: ").append(vulnerabilityType).append("\n");
        scanResultMessage.append("2] Affected Host or URL: ").append(affectedHost).append("\n");
        scanResultMessage.append("3] Recommendation to Overcome: ").append(recommendation).append("\n");
        scanResultMessage.append("4] Resource of Host: ").append(resource).append("\n");
        scanResultMessage.append("5] User Input Configuration: ").append(userInput).append("\n");

        JOptionPane.showMessageDialog(this, scanResultMessage.toString(), "Scan Results", JOptionPane.INFORMATION_MESSAGE);

        saveScanToHistory(host, scanLevel, exportId, scanResultMessage.toString());
    }

    private void saveScanToHistory(String host, String scanLevel, String exportId, String scanResult) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = String.format("[%s] Host: %s, Scan Level: %s, Export ID: %s\n%s\n\n", timestamp, host, scanLevel, exportId, scanResult);

        try (FileWriter writer = new FileWriter("scan_history.txt", true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel createScannerComparisonPanel() {
        JPanel scannerComparisonPanel = new JPanel();
        scannerComparisonPanel.setLayout(new BorderLayout());

        // Introduction Text
        JTextArea introTextArea = new JTextArea();
        introTextArea.setText("In this section, we compare various web vulnerability scanners based on their detection rate, accuracy, speed, and ease of use. "
                + "The table below summarizes the key features and performance metrics of each scanner. "
                + "Choose the scanner that best suits your requirements for web application security testing."+
                "One of the efficient scanner is Scanner A,invicti;and with less functionality kismet");

        introTextArea.setWrapStyleWord(true);
        introTextArea.setLineWrap(true);
        introTextArea.setEditable(false);
        introTextArea.setBackground(scannerComparisonPanel.getBackground());

        JScrollPane introScrollPane = new JScrollPane(introTextArea);
        introScrollPane.setPreferredSize(new Dimension(600, 100));
        scannerComparisonPanel.add(introScrollPane, BorderLayout.NORTH);

        // Scanner Comparison Table
        String[] columnNames = {"Scanner Name", "Detection Rate", "Accuracy", "Speed", "Supported","Protocol Supported"};
        Object[][] data = {
                {"Scanner A", "75%", "92%", "Fast", "YES","15"},
                {"Scanner B", "88%", "88%", "Medium", "YES","13"},
                {"Scanner C", "68%", "93%", "Slow", "YES","5"},
                {"XYZ", "49%", "66%", "Slow", "YES","10"},
                {"Acunetix", "95%", "90%", "Fast", "Yes","10"},
                {"AppScan", "88%", "85%", "Medium", "Yes","6"},
                {"Burp Suite", "90%", "88%", "Medium", "Yes","7"},
                {"Detectify", "92%", "90%", "Fast", "Yes","9"},
                {"Invicti", "85%", "92%", "Medium", "Yes","12"},
                {"hackHawk", "90%", "88%", "Fast", "Option","14"},
                {"ZAP", "82%", "85%", "Slow", "Yes","9"},
                {"cacti", "75%", "78%", "Medium", "Option","7"},
                {"Nagios", "80%", "82%", "Slow", "Option","6"},
                {"Kismet", "70%", "75%", "Slow", "No","5"},
                {"ManageEngine OpManager", "85%", "90%", "Medium", "No","10"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable comparisonTable = new JTable(tableModel);
        comparisonTable.setAutoCreateRowSorter(true);

        JScrollPane tableScrollPane = new JScrollPane(comparisonTable);
        scannerComparisonPanel.add(tableScrollPane, BorderLayout.CENTER);

        return scannerComparisonPanel;
    }


    private void showThemeDialog() {
        String[] themes = {"System Default", "Dark", "Light"};
        String selectedTheme = (String) JOptionPane.showInputDialog(
                this,
                "Select Theme:",
                "Theme Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                themes,
                themes[0]);

        if (selectedTheme != null) {
            switch (selectedTheme) {
                case "Dark":
                    setDarkTheme();
                    break;
                case "Light":
                    setLightTheme();
                    break;
                default:
                    setSystemDefaultTheme();
                    break;
            }
        }
    }

    private void setDarkTheme() {
        mainPanel.setBackground(Color.DARK_GRAY);
    }

    private void setLightTheme() {
        mainPanel.setBackground(Color.WHITE);
    }

    private void setSystemDefaultTheme() {
        mainPanel.setBackground(UIManager.getColor("Panel.background"));
    }

    private void showHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("scan_history.txt"))) {
            StringBuilder historyText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                historyText.append(line).append("\n");
            }
            JTextArea historyTextArea = new JTextArea(historyText.toString());
            JScrollPane scrollPane = new JScrollPane(historyTextArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "Scan History", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve scan history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
