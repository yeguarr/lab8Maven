package swing_package;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import command.Command;
import command.Commands;
import commons.User;
import program.Client;
import program.MainClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProgramWindow {
    JFrame frame;
    JPanel panelLeft = new JPanel();
    JPanel coordinates = new JPanel();
    JPanel topPanel = new JPanel(new BorderLayout());
    MyComponent component = new MyComponent();
    JButton add = new JButton("add");
    JButton clear = new JButton("clear");
    JButton add_if_min = new JButton("add if min");
    JButton remove_greater = new JButton("remove greater");
    JButton remove_lower = new JButton("remove lower");
    JButton average_of_distance = new JButton("average of distance");
    JButton min_by_creation_date = new JButton("min by creation_date");
    JButton print_field_ascending_distance = new JButton("print field ascending distance");
    RoutesTable routesTable = new RoutesTable();

    public void display(){
        frame = new JFrame("program");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(MainClient.img.getImage());

        panelLeft.setLayout(new GridLayout(15, 1, 10, 0));
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
        routesTable.panel.setPreferredSize(new Dimension( 700,150));

        coordinates.setLayout(new BorderLayout());

        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("FILE");
        JMenu language = new JMenu("language");
        JMenuItem help = new JMenuItem("HELP");
        JMenuItem show = new JMenuItem("routes");
        jMenuBar.add(file);
        jMenuBar.add(language);
        jMenuBar.add(show);
        jMenuBar.add(help);
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem langRus = new JMenuItem("Русский");
        JMenuItem LangEng = new JMenuItem("English");
        JMenuItem logout = new JMenuItem("log Out");
        file.add(logout);
        file.addSeparator();
        file.add(exit);
        language.add(langRus);
        language.add(LangEng);


        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainClient.rtm.update();
                MainClient.rtm.fireTableDataChanged();
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainClient.isDark = !MainClient.isDark;
                try {
                    UIManager.setLookAndFeel( MainClient.isDark ? new FlatDarculaLaf() : new FlatIntelliJLaf());
                    panelLeft.setBackground(MainClient.isDark ? new Color(60,63,65) : new Color(230,230,230));
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch ( UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                MainClient.globalKillFlag.set(true);
                MainClient.collection.map.clear();
                NewPortWindow o = new NewPortWindow();
                o.display();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                MainClient.collection.map.clear();
                LoginWindow o = new LoginWindow();
                o.display();
            }
        });

        panelLeft.setBackground(MainClient.isDark ? new Color(60,63,65) : new Color(230,230,230));
        coordinates.add(component);

        add.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.ADD, "Add new Route", "add");
        });

        add_if_min.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.ADD_IF_MIN, "Add new Route", "add");
        });

        remove_greater.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.REMOVE_GREATER, "Remove greater Routes", "remove");
        });

        remove_lower.addActionListener(actionEvent -> {
            ObjectWindow o = new ObjectWindow();
            o.display(Commands.REMOVE_LOWER, "Remove lower Routes", "remove");
        });

        print_field_ascending_distance.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user,Commands.PRINT_FIELD_ASCENDING_DISTANCE));
            } catch (IOException e) {
                new AlarmWindow().display("ERROR", e.getMessage());
            }
        });
        min_by_creation_date.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user,Commands.MIN_BY_CREATION_DATE));
            } catch (IOException e) {
                new AlarmWindow().display("ERROR", e.getMessage());
            }
        });

        average_of_distance.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user,Commands.AVERAGE_OF_DISTANCE));
            } catch (IOException e) {
                new AlarmWindow().display("ERROR", e.getMessage());
            }
        });
        clear.addActionListener(actionEvent -> {
            try {
                Client.SendCommand(new Command(MainClient.user, Commands.CLEAR));
            } catch (IOException e) {
                new AlarmWindow().display("ERROR", e.getMessage());
            }
        });

        frame.getContentPane().add(BorderLayout.PAGE_START, jMenuBar);
        topPanel.add(panelLeft, BorderLayout.LINE_START);
        topPanel.add(coordinates, BorderLayout.CENTER);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, routesTable.panel);
        frame.add(jSplitPane);
        frame.setMinimumSize(frame.getSize());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    FileWriter fileWriter = new FileWriter("client.txt");
                    fileWriter.write(MainClient.user.login + '\n');
                    fileWriter.write(MainClient.user.hashPassword+ '\n');
                    fileWriter.write(MainClient.port+ '\n');
                    fileWriter.write(String.valueOf(MainClient.isDark)+ '\n');
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);

    }
}
