package swing_package;

import command.Command;
import command.Commands;
import commons.User;
import program.MainClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import static program.Client.SendCommand;

public class RegisterWindow {
    JFrame frame;

    public void display(){
        frame = new JFrame(MainClient.stats.getString("Register"));
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(MainClient.img.getImage());

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel username = new JLabel(MainClient.stats.getString("Username"));
        username.setBounds(10,20,80,25);
        panel.add(username);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100,20,165,25);
        panel.add(usernameText);

        JLabel password = new JLabel(MainClient.stats.getString("Password"));
        password.setBounds(10,50,80,25);
        panel.add(password);

        JLabel password2 = new JLabel(MainClient.stats.getString("Password 2"));
        password2.setBounds(10,80,80,25);
        panel.add(password2);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        JPasswordField passwordText2 = new JPasswordField(20);
        passwordText2.setBounds(100,80,165,25);
        panel.add(passwordText2);

        JButton goBackButton = new JButton(MainClient.stats.getString("go back"));
        goBackButton.setBounds(10, 130, 80, 25);
        panel.add(goBackButton);
        goBackButton.addActionListener(new GoBackButton());

        JButton registerButton = new JButton(MainClient.stats.getString("create account"));
        registerButton.setBounds(100, 130, 120, 25);
        panel.add(registerButton);
        registerButton.addActionListener(actionListener -> {
            if (usernameText.getText().length()!=0&&Arrays.equals(passwordText.getPassword(), passwordText2.getPassword())) {
                try {
                    MainClient.user = new User(usernameText.getText(), new String(passwordText.getPassword()));
                    Command command = new Command(MainClient.user, Commands.REGISTER);

                    SendCommand(command);

                    LoginWindow obj = new LoginWindow();
                    obj.display();
                    frame.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
    }

    class GoBackButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            LoginWindow obj = new LoginWindow();
            obj.display();
            frame.dispose();
        }
    }

}
