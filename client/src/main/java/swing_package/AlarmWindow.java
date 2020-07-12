package swing_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;


public class AlarmWindow {
    JFrame frame;
    JMultilineLabel label;

    public void display(String title, String message){
        label = new JMultilineLabel(message);
        frame = new JFrame(title);
        //label.setText(message);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        Font font = new Font("Verdana", Font.PLAIN,20);
        label.setFont(font);

        //label.setBounds(60, 120, 130, 25);

        panel.add(label);
        frame.add(panel);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    class  Okbutton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frame.dispose();
        }
    }
}

class JMultilineLabel extends JTextArea{
    private static final long serialVersionUID = 1L;
    public JMultilineLabel(String text){
        super(text);
        setEditable(false);
        setCursor(null);
        setOpaque(false);
        setFocusable(false);
        //setFont(UIManager.getFont("Label.font"));
        setWrapStyleWord(true);
        setLineWrap(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setAlignmentY(JLabel.CENTER_ALIGNMENT);
    }
}
