package swing_package;

import commons.Route;
import commons.User;
import program.MainClient;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;

public class RoutesTableModel extends AbstractTableModel {

    private LinkedList<String[]> dataLinkedList = new LinkedList<>();

    public RoutesTableModel() {
    }

    public void update() {
        dataLinkedList.clear();
        for (User u : MainClient.collection.map.keySet())
            for (Route r : MainClient.collection.map.get(u)) {
                String[] s = new String[getColumnCount()];
                s[0] = u.login;
                s[1] = r.getId().toString();
                s[2] = r.getName();
                s[3] = String.valueOf(r.getCoordinates().getX());
                s[4] = String.valueOf(r.getCoordinates().getY());
                s[5] = r.getCreationDate().toString();
                if (r.getFrom() != null) {
                    s[6] = String.valueOf(r.getFrom().getX());
                    s[7] = String.valueOf(r.getFrom().getY());
                    s[8] = String.valueOf(r.getFrom().getZ());
                    s[9] = String.valueOf(r.getFrom().getName());
                } else {
                    s[6] = "null";
                    s[7] = "null";
                    s[8] = "null";
                    s[9] = "null";
                }
                s[10] = String.valueOf(r.getTo().getX());
                s[11] = String.valueOf(r.getTo().getY());
                s[12] = String.valueOf(r.getTo().getZ());
                s[13] = String.valueOf(r.getTo().getName());
                s[14] = String.valueOf(r.getDistance());
                dataLinkedList.add(s);
            }
        MainClient.rtm.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dataLinkedList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return MainClient.stats.getString("user_name");
            case 1:
                return MainClient.stats.getString("id");
            case 2:
                return MainClient.stats.getString("name");
            case 3:
                return MainClient.stats.getString("coordinate_x");
            case 4:
                return MainClient.stats.getString("coordinate_y");
            case 5:
                return MainClient.stats.getString("creationDate");
            case 6:
                return MainClient.stats.getString("from_x");
            case 7:
                return MainClient.stats.getString("from_y");
            case 8:
                return MainClient.stats.getString("from_z");
            case 9:
                return MainClient.stats.getString("from_name");
            case 10:
                return MainClient.stats.getString("to_x");
            case 11:
                return MainClient.stats.getString("to_y");
            case 12:
                return MainClient.stats.getString("to_z");
            case 13:
                return MainClient.stats.getString("to_name");
            case 14:
                return MainClient.stats.getString("distance");
        }
        return "";
    }

    @Override
    public int getColumnCount() {
        return 15;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataLinkedList.get(rowIndex);
        return rows[columnIndex];
    }
}
