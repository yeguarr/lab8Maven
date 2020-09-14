package swing_package;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import command.Command;
import command.Commands;
import command.ErrorCommand;
import commons.Utils;
import program.Client;
import program.CommanderClient;
import program.MainClient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class ProgramWindow {
    JFrame frame;
    JPanel panelLeft = new JPanel();
    JPanel coordinates = new JPanel();
    JPanel topPanel = new JPanel(new BorderLayout());
    MyComponent component = new MyComponent();
    JButton add = new JButton(MainClient.stats.getString("Add"));
    JButton clear = new JButton(MainClient.stats.getString("clear"));
    JButton add_if_min = new JButton(MainClient.stats.getString("add if min"));
    JButton remove_greater = new JButton(MainClient.stats.getString("remove greater"));
    JButton remove_lower = new JButton(MainClient.stats.getString("remove lower"));
    JButton average_of_distance = new JButton(MainClient.stats.getString("average of distance"));
    JButton min_by_creation_date = new JButton(MainClient.stats.getString("min by creation_date"));
    JButton print_field_ascending_distance = new JButton(MainClient.stats.getString("print field ascending distance"));
    RoutesTable routesTable = new RoutesTable();
    JTextField filterText = new JTextField(20);
    GhostText ghostText = new GhostText(filterText, MainClient.stats.getString("filter..."));
    JMenu file = new JMenu(MainClient.stats.getString("Program"));
    JMenu language = new JMenu(MainClient.stats.getString("Language"));
    JMenuItem exit = new JMenuItem(MainClient.stats.getString("Exit"));
    JMenuItem logout = new JMenuItem(MainClient.stats.getString("log Out"));
    JMenuItem appearance = new JMenuItem(MainClient.isDark ? MainClient.stats.getString("Сhange to the light side") : MainClient.stats.getString("Сhange to the dark side"));
    JMenuItem show = new JMenuItem("<html>" + MainClient.stats.getString("Username") + ": <b>" + MainClient.user.login + "</b>. &emsp;" + MainClient.stats.getString("Your color is") + ": <font color=#" + Integer.toHexString(Color.getHSBColor(((float) Math.abs(Utils.sha1(MainClient.user.login).hashCode())) / Integer.MAX_VALUE, 1.f, 1.f).getRGB()).substring(2) + ">⬛</font> </html>");

    private void changeLanguage() {
        add.setText(MainClient.stats.getString("Add"));
        clear.setText(MainClient.stats.getString("clear"));
        add_if_min.setText(MainClient.stats.getString("add if min"));
        remove_greater.setText(MainClient.stats.getString("remove greater"));
        remove_lower.setText(MainClient.stats.getString("remove lower"));
        average_of_distance.setText(MainClient.stats.getString("average of distance"));
        min_by_creation_date.setText(MainClient.stats.getString("min by creation_date"));
        print_field_ascending_distance.setText(MainClient.stats.getString("print field ascending distance"));
        file.setText(MainClient.stats.getString("Program"));
        language.setText(MainClient.stats.getString("Language"));
        exit.setText(MainClient.stats.getString("Exit"));
        logout.setText(MainClient.stats.getString("log Out"));
        frame.setTitle(MainClient.stats.getString("Program"));
        ghostText.setGhostText(MainClient.stats.getString("filter..."));
        //if ((filterText.getText().equals("")||filterText.getText().equals(MainClient.stats.getString("filter..."))))//filterText.getText().equals("filter...")||filterText.getText().equals("szűrő...")||filterText.getText().equals("фильтровать...")))
        ghostText.focusLost();
        show.setText("<html>" + MainClient.stats.getString("Username") + ": <b>" + MainClient.user.login + "</b>. &emsp;" + MainClient.stats.getString("Your color is") + ": <font color=#" + Integer.toHexString(Color.getHSBColor(((float) Math.abs(Utils.sha1(MainClient.user.login).hashCode())) / Integer.MAX_VALUE, 1.f, 1.f).getRGB()).substring(2) + ">⬛</font> </html>");
        appearance.setText(MainClient.isDark ? MainClient.stats.getString("Сhange to the light side") : MainClient.stats.getString("Сhange to the dark side"));
        routesTable.routes.createDefaultColumnsFromModel();
        routesTable.routes.getColumnModel().getColumn(5).setCellRenderer(RoutesTable.tableCellRenderer);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void display() {
        frame = new JFrame(MainClient.stats.getString("Program"));
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(MainClient.img.getImage());

        panelLeft.setLayout(new GridLayout(10, 1, 10, 0));
        add.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(add);
        clear.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(clear);
        add_if_min.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(add_if_min);
        remove_greater.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(remove_greater);
        remove_lower.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(remove_lower);
        average_of_distance.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(average_of_distance);
        min_by_creation_date.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(min_by_creation_date);
        panelLeft.add(print_field_ascending_distance);


        filterText.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }

            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            private void newFilter() {
                if (!(filterText.getText().equals("") || filterText.getText().equals(MainClient.stats.getString("filter..."))))
                    try {
                        routesTable.sorter.setRowFilter(RowFilter.regexFilter(filterText.getText()));
                    } catch (java.util.regex.PatternSyntaxException ignored) {
                    }
                else
                    routesTable.sorter.setRowFilter(null);
            }
        });
        panelLeft.add(new JPanel());
        panelLeft.add(filterText);

        coordinates.setLayout(new BorderLayout());

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(file);
        jMenuBar.add(language);
        jMenuBar.add(show);
        jMenuBar.add(appearance);
        JMenuItem langRus = new JMenuItem("Русский (Russian)");
        JMenuItem LangEng = new JMenuItem("English (India)");
        JMenuItem langHun = new JMenuItem("Magyar (Hungarian)");
        JMenuItem LangDut = new JMenuItem("Nederlandse (Dutch)");
        file.add(logout);
        file.addSeparator();
        file.add(exit);
        language.add(langRus);
        language.add(LangEng);
        language.add(langHun);
        language.add(LangDut);

        langRus.addActionListener(actionEvent -> {
            try {
                MainClient.currentLocale = 0;
                MainClient.stats = ResourceBundle.getBundle("locales.Label", MainClient.locales[MainClient.currentLocale]);
                changeLanguage();
            } catch (Exception e) {
                Command com = new ErrorCommand(MainClient.user, "client.error.lang");
                CommanderClient.switcher(com, MainClient.collection);
            }
        });

        LangEng.addActionListener(actionEvent -> {
            try {
                MainClient.currentLocale = 2;
                MainClient.stats = ResourceBundle.getBundle("locales.Label", MainClient.locales[MainClient.currentLocale]);
                changeLanguage();
            } catch (Exception e) {
                Command com = new ErrorCommand(MainClient.user, "client.error.lang");
                CommanderClient.switcher(com, MainClient.collection);
            }
        });

        langHun.addActionListener(actionEvent -> {
            try {
                MainClient.currentLocale = 1;
                MainClient.stats = ResourceBundle.getBundle("locales.Label", MainClient.locales[MainClient.currentLocale]);
                changeLanguage();
            } catch (Exception e) {
                Command com = new ErrorCommand(MainClient.user, "client.error.lang");
                CommanderClient.switcher(com, MainClient.collection);
            }
        });

        LangDut.addActionListener(actionEvent -> {
            try {
                MainClient.currentLocale = 3;
                MainClient.stats = ResourceBundle.getBundle("locales.Label", MainClient.locales[MainClient.currentLocale]);
                changeLanguage();
            } catch (Exception e) {
                Command com = new ErrorCommand(MainClient.user, "client.error.lang");
                CommanderClient.switcher(com, MainClient.collection);
            }
        });

        appearance.addActionListener(actionEvent -> {
            try {
                MainClient.isDark = !MainClient.isDark;
                appearance.setText(MainClient.isDark ? MainClient.stats.getString("Сhange to the light side") : MainClient.stats.getString("Сhange to the dark side"));
                ghostText.setGhostColor(MainClient.isDark ? Color.GRAY : Color.LIGHT_GRAY);
                ghostText.focusLost();
                coordinates.requestFocus();
                UIManager.setLookAndFeel(MainClient.isDark ? new FlatDarculaLaf() : new FlatIntelliJLaf());
                panelLeft.setBackground(MainClient.isDark ? new Color(60, 63, 65) : new Color(230, 230, 230));
                SwingUtilities.updateComponentTreeUI(frame);
            } catch (UnsupportedLookAndFeelException e) {
                Command com = new ErrorCommand(MainClient.user, "client.error.lookandfeel");
                CommanderClient.switcher(com, MainClient.collection);
            }
        });

        exit.addActionListener(actionEvent -> {
            frame.dispose();
            MainClient.globalKillFlag.set(true);
            MainClient.collection.map.clear();
            MainClient.rtm.update();
            IpPortWindow o = new IpPortWindow();
            o.display();
        });

        logout.addActionListener(actionEvent -> {
            frame.dispose();
            MainClient.collection.map.clear();
            MainClient.rtm.update();
            LoginWindow o = new LoginWindow();
            o.display();
        });

        panelLeft.setBackground(MainClient.isDark ? new Color(60, 63, 65) : new Color(230, 230, 230));
        coordinates.add(component);

        add.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.ADD, MainClient.stats.getString("Add new Route"), MainClient.stats.getString("add"));
        });

        add_if_min.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.ADD_IF_MIN, MainClient.stats.getString("Add new Route"), MainClient.stats.getString("add"));
        });

        remove_greater.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.REMOVE_GREATER, MainClient.stats.getString("Remove greater Routes"), MainClient.stats.getString("remove"));
        });

        remove_lower.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.REMOVE_LOWER, MainClient.stats.getString("Remove lower Routes"), MainClient.stats.getString("remove"));
        });

        print_field_ascending_distance.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user, Commands.PRINT_FIELD_ASCENDING_DISTANCE));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), MainClient.stats.getString("ERROR"), JOptionPane.WARNING_MESSAGE);
                //new AlarmWindow().display("ERROR", e.getMessage());
            }
        });
        min_by_creation_date.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user, Commands.MIN_BY_CREATION_DATE));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), MainClient.stats.getString("ERROR"), JOptionPane.WARNING_MESSAGE);
                //new AlarmWindow().display("ERROR", e.getMessage());
            }
        });

        average_of_distance.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user, Commands.AVERAGE_OF_DISTANCE));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), MainClient.stats.getString("ERROR"), JOptionPane.WARNING_MESSAGE);
                //new AlarmWindow().display("ERROR", e.getMessage());
            }
        });
        clear.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user, Commands.CLEAR));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), MainClient.stats.getString("ERROR"), JOptionPane.WARNING_MESSAGE);
                //new AlarmWindow().display("ERROR", e.getMessage());
            }
        });

        frame.getContentPane().add(BorderLayout.PAGE_START, jMenuBar);
        topPanel.add(panelLeft, BorderLayout.LINE_START);
        topPanel.add(coordinates, BorderLayout.CENTER);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, routesTable.panel);
        jSplitPane.setDividerLocation(frame.getHeight() - 250);
        frame.add(jSplitPane);
        frame.setMinimumSize(frame.getSize());


        frame.addComponentListener(new ComponentAdapter() {
            int old = frame.getHeight();

            public void componentResized(ComponentEvent e) {
                jSplitPane.setDividerLocation((int) (jSplitPane.getDividerLocation() * ((float) frame.getHeight() / old)));
                old = frame.getHeight();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    FileWriter fileWriter = new FileWriter("client.txt");
                    fileWriter.write(MainClient.user.login + '\n');
                    fileWriter.write(MainClient.user.hashPassword + '\n');
                    fileWriter.write(MainClient.ip + '\n');
                    fileWriter.write(MainClient.port + '\n');
                    fileWriter.write(String.valueOf(MainClient.isDark) + '\n');
                    fileWriter.write(String.valueOf(MainClient.currentLocale) + '\n');
                    fileWriter.close();
                } catch (IOException ex) {
                    Command com = new ErrorCommand(MainClient.user, "client.error.file");
                    CommanderClient.switcher(com, MainClient.collection);
                }
            }
        });

        frame.setVisible(true);
    }
}
