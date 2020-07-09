package swing_package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow {
    JFrame frame;

    public void display(){
        frame = new JFrame("Register");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel username = new JLabel("Username");
        username.setBounds(10,20,80,25);
        panel.add(username);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100,20,165,25);
        panel.add(usernameText);

        JLabel password = new JLabel("Password");
        password.setBounds(10,50,80,25);
        panel.add(password);

        JLabel password2 = new JLabel("Password 2");
        password2.setBounds(10,80,80,25);
        panel.add(password2);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        JPasswordField passwordText2 = new JPasswordField(20);
        passwordText2.setBounds(100,80,165,25);
        panel.add(passwordText2);

        JButton goBackButton = new JButton("go back");
        goBackButton.setBounds(10, 130, 80, 25);
        panel.add(goBackButton);
        goBackButton.addActionListener(new GoBackButton());

        JButton registerButton = new JButton("create account");
        registerButton.setBounds(100, 130, 120, 25);
        panel.add(registerButton);

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
