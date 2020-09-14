package swing_package;

import command.CommandWithObj;
import command.Commands;
import commons.Coordinates;
import commons.Location;
import commons.Route;
import program.Client;
import program.MainClient;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.io.IOException;

public class ObjectWindow {
    JFrame frame;
    JButton buttonLocationFrom = new JButton();
    JButton buttonDistance = new JButton();

    public void display(Commands com, String title, String buttonStr) {
        frame = new JFrame(title);
        frame.setSize(350, 530);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(MainClient.img.getImage());

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel name = new JLabel(MainClient.stats.getString("name"));
        name.setBounds(10, 20, 90, 25);
        panel.add(name);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(130, 20, 165, 25);
        panel.add(nameText);

        JLabel coordinatesX = new JLabel(MainClient.stats.getString("coordinates.") + "x");
        coordinatesX.setBounds(10, 50, 90, 25);
        panel.add(coordinatesX);

        JLabel coordinatesY = new JLabel(MainClient.stats.getString("coordinates.") + "y");
        coordinatesY.setBounds(10, 80, 90, 25);
        panel.add(coordinatesY);

        JLabel LocationToX = new JLabel(MainClient.stats.getString("LocationTo.") + "x");
        LocationToX.setBounds(10, 110, 90, 25);
        panel.add(LocationToX);

        JLabel LocationToY = new JLabel(MainClient.stats.getString("LocationTo.") + "y");
        LocationToY.setBounds(10, 140, 90, 25);
        panel.add(LocationToY);

        JLabel LocationToZ = new JLabel(MainClient.stats.getString("LocationTo.") + "z");
        LocationToZ.setBounds(10, 170, 90, 25);
        panel.add(LocationToZ);

        JTextField coordinatesXText = new JTextField(20);
        coordinatesXText.setBounds(130, 50, 165, 25);
        coordinatesXText.setDocument(new Tester());
        panel.add(coordinatesXText);

        JTextField coordinatesYText = new JTextField(20);
        coordinatesYText.setBounds(130, 80, 165, 25);
        coordinatesYText.setDocument(new Tester());
        panel.add(coordinatesYText);

        JTextField LocationToXText = new JTextField(20);
        LocationToXText.setBounds(130, 110, 165, 25);
        LocationToXText.setDocument(new Tester());
        panel.add(LocationToXText);

        JTextField LocationToYText = new JTextField(20);
        LocationToYText.setBounds(130, 140, 165, 25);
        LocationToYText.setDocument(new Tester());
        panel.add(LocationToYText);

        JTextField LocationToZText = new JTextField(20);
        LocationToZText.setBounds(130, 170, 165, 25);
        LocationToZText.setDocument(new Tester());
        panel.add(LocationToZText);

        JLabel LocationToName = new JLabel(MainClient.stats.getString("LocationTo.name"));
        LocationToName.setBounds(10, 200, 100, 25);
        panel.add(LocationToName);

        JTextField LocationToNameText = new JTextField(20);
        LocationToNameText.setBounds(130, 200, 165, 25);
        panel.add(LocationToNameText);

        buttonLocationFrom.setText(MainClient.stats.getString("From is null"));
        buttonLocationFrom.setBounds(80, 232, 160, 20);
        panel.add(buttonLocationFrom);

        JLabel LocationFromX = new JLabel(MainClient.stats.getString("LocationFrom.") + "x");
        LocationFromX.setBounds(10, 260, 90, 25);
        panel.add(LocationFromX);

        JLabel LocationFromY = new JLabel(MainClient.stats.getString("LocationFrom.") + "y");
        LocationFromY.setBounds(10, 290, 90, 25);
        panel.add(LocationFromY);

        JLabel LocationFromZ = new JLabel(MainClient.stats.getString("LocationFrom.") + "z");
        LocationFromZ.setBounds(10, 320, 90, 25);
        panel.add(LocationFromZ);

        JTextField LocationFromXText = new JTextField(20);
        LocationFromXText.setBounds(130, 260, 165, 25);
        LocationFromXText.setDocument(new Tester());
        panel.add(LocationFromXText);

        JTextField LocationFromYText = new JTextField(20);
        LocationFromYText.setBounds(130, 290, 165, 25);
        LocationFromYText.setDocument(new Tester());
        panel.add(LocationFromYText);

        JTextField LocationFromZText = new JTextField(20);
        LocationFromZText.setBounds(130, 320, 165, 25);
        LocationFromZText.setDocument(new Tester());
        panel.add(LocationFromZText);

        JLabel LocationFromName = new JLabel(MainClient.stats.getString("LocationFrom.name"));
        LocationFromName.setBounds(10, 350, 120, 25);
        panel.add(LocationFromName);

        JTextField LocationFromNameText = new JTextField(20);
        LocationFromNameText.setBounds(130, 350, 165, 25);
        panel.add(LocationFromNameText);

        buttonDistance.setText(MainClient.stats.getString("distance is null"));
        buttonDistance.setBounds(80, 382, 160, 20);
        panel.add(buttonDistance);

        JLabel distance = new JLabel(MainClient.stats.getString("Distance"));
        distance.setBounds(10, 410, 120, 25);
        panel.add(distance);

        JTextField distanceText = new JTextField(20);
        distanceText.setBounds(130, 410, 165, 25);
        distanceText.setDocument(new Tester());
        panel.add(distanceText);

        JButton cancelButton = new JButton(MainClient.stats.getString("cancel"));
        cancelButton.setBounds(10, 440, 80, 25);
        panel.add(cancelButton);

        JButton createButton = new JButton(buttonStr);
        createButton.setBounds(130, 440, 120, 25);
        panel.add(createButton);

        buttonDistance.addActionListener(actionEvent -> {
            if (buttonDistance.getText().equals(MainClient.stats.getString("distance is null"))) {
                buttonDistance.setText(MainClient.stats.getString("distance is not null"));
                distanceText.setEditable(false);
            } else {
                buttonDistance.setText(MainClient.stats.getString("distance is null"));
                distanceText.setEditable(true);
            }
        });

        buttonLocationFrom.addActionListener(actionEvent -> {
            if (buttonLocationFrom.getText().equals(MainClient.stats.getString("From is null"))) {
                buttonLocationFrom.setText(MainClient.stats.getString("From is not null"));
                LocationFromNameText.setEditable(false);
                LocationFromXText.setEditable(false);
                LocationFromYText.setEditable(false);
                LocationFromZText.setEditable(false);
            } else {
                buttonLocationFrom.setText(MainClient.stats.getString("From is null"));
                LocationFromNameText.setEditable(true);
                LocationFromXText.setEditable(true);
                LocationFromYText.setEditable(true);
                LocationFromZText.setEditable(true);
            }
        });

        cancelButton.addActionListener(actionEvent -> frame.dispose());

        createButton.addActionListener(actionEvent -> {
            try {
                Route r = new Route();
                r.setName(nameText.getText());
                r.setCoordinates(new Coordinates(Integer.parseInt(coordinatesXText.getText()), Long.parseLong(coordinatesYText.getText())));
                if (buttonLocationFrom.getText().equals(MainClient.stats.getString("From is null"))) {
                    r.setFrom(new Location(Long.parseLong(LocationFromXText.getText()), Long.parseLong(LocationFromYText.getText()), Long.parseLong(LocationFromZText.getText()), LocationFromNameText.getText()));
                } else {
                    r.setFrom(null);
                }
                r.setTo(new Location(Long.parseLong(LocationToXText.getText()), Long.parseLong(LocationToYText.getText()), Long.parseLong(LocationToZText.getText()), LocationToNameText.getText()));
                if (buttonDistance.getText().equals(MainClient.stats.getString("distance is null"))) {
                    r.setDistance(Long.parseLong(distanceText.getText()));
                } else {
                    r.setDistance(null);
                }
                Client.SendCommand(new CommandWithObj(MainClient.user, com, r));
                frame.dispose();
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, MainClient.stats.getString("Field error"), MainClient.stats.getString("ERROR"), JOptionPane.WARNING_MESSAGE);
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

class Tester extends PlainDocument {
    String chars = "-0123456789";

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;

        if (chars.contains(str)) {
            super.insertString(offset, str, attr);
        }

    }
}
