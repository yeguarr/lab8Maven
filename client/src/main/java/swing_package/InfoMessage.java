package swing_package;

import javax.swing.*;
import java.awt.*;

public class InfoMessage {
    public Color first;
    public Color second;
    public Icon icon;
    public String name;
    public String description;

    private InfoMessage() {}

    public static InfoMessage info(String name, String description) {
        InfoMessage message = new InfoMessage();
        message.first = new Color(61,97,133);
        message.second = new Color(73,117,160);
        message.icon = UIManager.getIcon( "OptionPane.informationIcon");
        message.name = name;
        message.description = description;
        return message;
    }
    public static InfoMessage warning(String name, String description) {
        InfoMessage message = new InfoMessage();
        message.first = new Color(177,121,0);
        message.second = new Color(199,141,17);
        message.icon = UIManager.getIcon("OptionPane.warningIcon");
        message.name = name;
        message.description = description;
        return message;
    }

    public static InfoMessage error(String name, String description) {
        InfoMessage message = new InfoMessage();
        message.first = new Color(173,64,71);
        message.second = new Color(191,82,89);
        message.icon = UIManager.getIcon("OptionPane.errorIcon");
        message.name = name;
        message.description = description;
        return message;
    }
}
