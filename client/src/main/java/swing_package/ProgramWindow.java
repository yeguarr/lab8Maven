package swing_package;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import command.Command;
import command.Commands;
import program.Client;
import program.MainClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProgramWindow {
    public static boolean isDark = false;
    JFrame frame;
    JPanel panelLeft = new JPanel();
    JPanel panelBottom = new JPanel();
    JPanel coordinates = new JPanel();
    public MyComponent component = new MyComponent();
    JButton add = new JButton("add");
    //JButton update = new JButton("update");
    //JButton remove_by_id = new JButton("remove by id");
    JButton clear = new JButton("clear");
    JButton add_if_min = new JButton("add if min");
    JButton remove_greater = new JButton("remove greater");
    JButton remove_lower = new JButton("remove lower");
    JButton average_of_distance = new JButton("average of distance");
    JButton min_by_creation_date = new JButton("min by creation_date");
    JButton print_field_ascending_distance = new JButton("print field ascending distance");

    JTable Routes = new JTable();

    public void display(){
        frame = new JFrame("program");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        panelLeft.setLayout(new GridLayout(15, 1, 10, 0));
        add.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(add);
        //update.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        //panelLeft.add(update);
        //remove_by_id.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        //panelLeft.add(remove_by_id);
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
                RoutesTable o = new RoutesTable();
                o.display();
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isDark = !isDark;
                try {
                    UIManager.setLookAndFeel( isDark ? new FlatDarculaLaf() : new FlatIntelliJLaf());
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
                NewPortWindow o = new NewPortWindow();
                o.display();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                LoginWindow o = new LoginWindow();
                o.display();
            }
        });

        panelLeft.setBackground(Color.GRAY);
        panelBottom.setBackground(Color.DARK_GRAY);
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
        frame.add(panelLeft, BorderLayout.LINE_START);
        frame.add(coordinates, BorderLayout.CENTER);
        frame.setMinimumSize(frame.getSize());

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        ProgramWindow o = new ProgramWindow();
        o.display();
    }
}
