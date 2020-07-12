package swing_package;

import command.Command;
import commons.Route;
import program.MainClient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoutesTable {
    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    RoutesTableModel rtm = new RoutesTableModel();
    JTable routes = new JTable(rtm);
    JScrollPane routesScrollPane = new JScrollPane(routes);

    public void display(){
        routes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected)
                    c.setBackground(column % 2 == 1 ? new Color(61,97,133) :  new Color(73,117,160));
                else
                    c.setBackground(column % 2 == 1 ? (ProgramWindow.isDark ? new Color(60,63,65) : Color.LIGHT_GRAY) : (ProgramWindow.isDark ?  new Color(69,73,74) : Color.WHITE));
                return this;
            }
        });
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(routes.getModel());
        routes.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        sorter.setComparator(1, new LongComparator());

        sorter.setComparator(3, new LongComparator());
        sorter.setComparator(4, new LongComparator());

        sorter.setComparator(6, new LongComparator());
        sorter.setComparator(7, new LongComparator());
        sorter.setComparator(8, new LongComparator());

        sorter.setComparator(10, new LongComparator());
        sorter.setComparator(11, new LongComparator());
        sorter.setComparator(12, new LongComparator());

        sorter.setComparator(14, new LongComparator());

        routes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    UpdateWindow o = new UpdateWindow();
                    Route r = MainClient.collection.getRouteById(Integer.parseInt(String.valueOf(table.getValueAt(row,1))));
                    String login = String.valueOf(table.getValueAt(row,0));
                    o.display(r, MainClient.user.login.equals(login));
                }
            }
        });

        frame = new JFrame("program");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        panel.setLayout(new BorderLayout());

        panel.add(routesScrollPane );
        frame.add(panel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        RoutesTable o = new RoutesTable();
        o.display();
    }
}

class LongComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        String s1 = String.valueOf(o1);
        String s2 = String.valueOf(o2);
        if (s1.equals("null"))
            return -1;
        else if (s2.equals("null"))
            return 1;
        Long int1 = Long.parseLong(s1);
        Long int2 = Long.parseLong(s2);
        return int1.compareTo(int2);
    }

    public boolean equals(Object o2) {
        return this.equals(o2);
    }
}
