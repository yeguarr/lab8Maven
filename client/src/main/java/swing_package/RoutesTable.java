package swing_package;

import commons.Route;
import program.MainClient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoutesTable {
    public static TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected)
                c.setBackground(column % 2 == 1 ? new Color(61, 97, 133) : new Color(73, 117, 160));
            else
                c.setBackground(column % 2 == 1 ? (MainClient.isDark ? new Color(60, 63, 65) : new Color(230, 230, 230)) : (MainClient.isDark ? new Color(69, 73, 74) : Color.WHITE));
            value = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(MainClient.stats.getLocale()).format(ZonedDateTime.parse(value.toString()));
            setValue(value);
            return c;
        }
    };
    public TableRowSorter<TableModel> sorter;
    JPanel panel = new JPanel();
    JTable routes = new JTable(MainClient.rtm);
    JScrollPane routesScrollPane = new JScrollPane(routes);

    public RoutesTable() {
        routes.getColumnModel().getColumn(5).setCellRenderer(tableCellRenderer);
        routes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected)
                    c.setBackground(column % 2 == 1 ? new Color(61, 97, 133) : new Color(73, 117, 160));
                else
                    c.setBackground(column % 2 == 1 ? (MainClient.isDark ? new Color(60, 63, 65) : new Color(230, 230, 230)) : (MainClient.isDark ? new Color(69, 73, 74) : Color.WHITE));
                return this;
            }
        });

        sorter = new TableRowSorter<>(routes.getModel());
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
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    UpdateWindow o = new UpdateWindow();
                    int id = 0;
                    int user_name = 0;
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        if (table.getColumnName(i).equals("ID"))
                            id = i;
                        else if (table.getColumnName(i).equals("Username"))
                            user_name = i;
                    }
                    Route r = MainClient.collection.getRouteById(Integer.parseInt(String.valueOf(table.getValueAt(row, id))));
                    String login = String.valueOf(table.getValueAt(row, user_name));
                    o.display(r, MainClient.user.login.equals(login));
                }
            }
        });

        panel.setLayout(new BorderLayout());
        panel.add(routesScrollPane);
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
