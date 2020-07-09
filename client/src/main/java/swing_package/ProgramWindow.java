package swing_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramWindow {
    JFrame frame;
    JPanel panelLeft = new JPanel();
    JPanel panelBottom = new JPanel();
    JPanel coordinates = new JPanel();
    public MyComponent component = new MyComponent();
    JButton add = new JButton("add");
    JButton update = new JButton("update");
    JButton remove_by_id = new JButton("remove by id");
    JButton clear = new JButton("clear");
    JButton add_if_min = new JButton("add if min");
    JButton remove_greater = new JButton("remove greater");
    JButton remove_lower = new JButton("remove lower");
    JButton average_of_distance = new JButton("average of distance");
    JButton min_by_creation_date = new JButton("min by creation_date");
    JButton print_field_ascending_distance = new JButton("print field ascending distance");

    JTable Routes = new JTable();
    JScrollPane RoutesScrollPane = new JScrollPane(Routes);

    public void display(){
        frame = new JFrame("program");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        panelLeft.setLayout(new GridLayout(15, 1, 10, 0));
        add.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(add);
        update.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(update);
        remove_by_id.setPreferredSize(print_field_ascending_distance.getPreferredSize());
        panelLeft.add(remove_by_id);
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

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //do something
                SetObjectWindow o = new SetObjectWindow();
                o.display();
            }
        });

        frame.getContentPane().add(BorderLayout.PAGE_START, jMenuBar);
        frame.add(panelLeft, BorderLayout.LINE_START);
        frame.add(coordinates, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ProgramWindow o = new ProgramWindow();
        o.display();
    }
}
