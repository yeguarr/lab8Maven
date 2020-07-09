package swing_package;

import javax.swing.*;
import java.awt.*;

public class RoutesTable {
    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    RoutesTableModel rtm = new RoutesTableModel();
    JTable routes = new JTable(rtm);
    JScrollPane routesScrollPane = new JScrollPane(routes);

    public void display(){
        frame = new JFrame("program");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        panel.add(routesScrollPane);
        frame.add(panel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        RoutesTable o = new RoutesTable();
        o.display();
    }
}
