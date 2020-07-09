package swing_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;


public class AlarmWindow {
    JFrame frame;
    JLabel label = new JLabel();

    public void display(String title, String message){
        frame = new JFrame(title);
        label.setText(message);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        Font font = new Font("Verdana", Font.PLAIN,20);
        label.setFont(font);

        label.setBounds(60, 120, 130, 25);

        panel.add(label);
        frame.add(panel);

        frame.setVisible(true);
    }

    class  Okbutton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        AlarmWindow obj = new AlarmWindow();
        obj.display("dsfdsfsdf", "zdfdfdv");
    }
}
