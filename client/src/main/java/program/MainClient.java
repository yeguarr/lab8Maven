package program;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import commons.Collection;
import commons.User;
import swing_package.NewPortWindow;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainClient {
    public static Collection collection;
    public static final AtomicBoolean globalKillFlag = new AtomicBoolean(false);
    public static User user = new User("login", "password");
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf() );
        } catch ( UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        NewPortWindow o = new NewPortWindow();
        collection = new Collection(0);
        o.display();
    }
}
