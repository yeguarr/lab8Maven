package swing_package;

import commons.Utils;
import program.Client;
import program.MainClient;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.NumberFormat;

public class IpPortWindow {
    JFrame frame;

    public void display(){
        frame = new JFrame("connection");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(MainClient.img.getImage());

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel serverInfo = new JLabel("Client configuration");
        serverInfo.setBounds(110, 10, 110, 25);
        panel.add(serverInfo);

        JLabel username = new JLabel("Server's ip:");
        username.setBounds(20,40,80,25);
        panel.add(username);

        JTextField ipText = new JTextField(20);
        ipText.setBounds(90,40,165,25);
        ipText.setText(MainClient.ip);
        panel.add(ipText);

        JLabel password = new JLabel("Port:");
        password.setBounds(20,70,80,25);
        panel.add(password);

        JTextField portText = new JTextField(20);
        portText.setBounds(90,70,165,25);
        portText.setText(MainClient.port);
        panel.add(portText);

        JButton setButton = new JButton("Set");
        setButton.setBounds(140, 100, 60, 25);
        panel.add(setButton);
        setButton.addActionListener(actionEvent -> {
            if (!portText.getText().equals("")) {
                try {
                    MainClient.ip = ipText.getText();
                    MainClient.port = portText.getText();
                    MainClient.globalKillFlag.set(false);
                    Client.run(MainClient.ip, Integer.parseInt(MainClient.port));
                    frame.dispose();
                    LoginWindow o = new LoginWindow();
                    o.display();
                } catch (IOException e) {
                    AlarmWindow alarmWindow = new AlarmWindow();
                    alarmWindow.display("ERROR", e.getMessage());
                    e.printStackTrace();
                    MainClient.globalKillFlag.set(true);
                }
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(setButton);
        setButton.requestFocus();
    }
}
