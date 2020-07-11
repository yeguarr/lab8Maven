package swing_package;

import command.Command;
import command.Commands;
import commons.User;
import commons.Writer;
import program.Client;
import program.MainClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import static program.Client.SendCommand;

public class LoginWindow {
    JButton registerButton;
    JFrame frame;

    public void display(){
        frame = new JFrame("Start Page");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel welcome = new JLabel("Welcome!");
        welcome.setBounds(140, 10, 80, 25);
        panel.add(welcome);

        JLabel username = new JLabel("Username");
        username.setBounds(10,40,80,25);
        panel.add(username);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100,40,165,25);
        panel.add(usernameText);

        JLabel password = new JLabel("Password");
        password.setBounds(10,70,80,25);
        panel.add(password);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,70,165,25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 100, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(actionEvent -> {
            try {
                MainClient.user = new User(usernameText.getText(), new String(passwordText.getPassword()));
                Command command = new Command(MainClient.user, Commands.LOGIN);

                SendCommand(command);

                ProgramWindow obj = new ProgramWindow();
                obj.display();
                frame.dispose();
            } catch (IOException e) {
                e.printStackTrace();
                MainClient.globalKillFlag.set(true);
            }
        });

        registerButton = new JButton("create account");
        registerButton.setBounds(100, 100, 120, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new RegisterButton());

        frame.setVisible(true);
    }

    public class RegisterButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            RegisterWindow obj = new RegisterWindow();
            obj.display();
            frame.dispose();
        }
    }

}

