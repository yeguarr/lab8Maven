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

public class NewPortWindow {
    JFrame frame;

    public void display(){
        frame = new JFrame("connection");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel port = new JLabel("Port");
        port.setFont(new Font("Verdana", Font.PLAIN,30));
        port.setBounds(140,30,80,25);
        panel.add(port);

        JTextField portText = new JTextField(20);
        portText.setBounds(90,70,165,25);
        PlainDocument doc = new PlainDocument(){
            String chars = "0123456789";
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null)
                    return;
                if ((getLength() + str.length()) <= 5) {
                    if(chars.contains(str)){
                        super.insertString(offset, str, attr);
                    }
                }
            }
        };
        portText.setDocument(doc);
        panel.add(portText);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(10, 130, 80, 25);
        panel.add(exitButton);
        exitButton.addActionListener(actionEvent -> {
            MainClient.globalKillFlag.set(true);
            frame.dispose();
        });

        JButton setButton = new JButton("Set");
        setButton.setBounds(140, 100, 60, 25);
        panel.add(setButton);
        setButton.addActionListener(actionEvent -> {
            if (!portText.getText().equals("")) {
                try {
                    frame.dispose();
                    Client.run(Integer.parseInt(portText.getText()));
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

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        NewPortWindow o = new NewPortWindow();
        o.display();
    }
}
