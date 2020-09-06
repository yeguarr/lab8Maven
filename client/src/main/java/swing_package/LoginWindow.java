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
        frame = new JFrame(MainClient.stats.getString("Start Page"));
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(MainClient.img.getImage());

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel welcome = new JLabel(MainClient.stats.getString("Welcome!"));
        welcome.setBounds(140, 10, 80, 25);
        panel.add(welcome);

        JLabel username = new JLabel(MainClient.stats.getString("Username"));
        username.setBounds(10,40,80,25);
        panel.add(username);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100,40,165,25);
        if (!MainClient.user.login.equals(MainClient.stats.getString("login")))
            usernameText.setText(MainClient.user.login);
        panel.add(usernameText);

        JLabel password = new JLabel(MainClient.stats.getString("password"));
        password.setBounds(10,70,80,25);
        panel.add(password);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,70,165,25);
        if (!MainClient.user.login.equals(MainClient.stats.getString("login")))
            passwordText.setText(MainClient.stats.getString("password"));
        panel.add(passwordText);

        JButton loginButton = new JButton(MainClient.stats.getString("login"));
        loginButton.setBounds(10, 100, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(actionEvent -> {
            try {
                if (!MainClient.user.login.equals(usernameText.getText())||!(new String(passwordText.getPassword()).equals(MainClient.stats.getString("password"))))
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

        registerButton = new JButton(MainClient.stats.getString("create account"));
        registerButton.setBounds(100, 100, 120, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new RegisterButton());
        frame.setResizable(false);
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

