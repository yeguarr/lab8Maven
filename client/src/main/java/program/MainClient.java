package program;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import commons.Collection;
import commons.Reader;
import commons.User;
import exceptions.EndOfFileException;
import exceptions.IncorrectFileNameException;
import swing_package.IpPortWindow;
import swing_package.RoutesTableModel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainClient {
    public static Collection collection;
    public static final AtomicBoolean globalKillFlag = new AtomicBoolean(false);
    public static User user = new User("login", "password");
    public static RoutesTableModel rtm = new RoutesTableModel();
    public static ImageIcon img = new ImageIcon("client/src/main/resources/icon48.png");
    public static boolean isDark = false;
    public static String port;
    public static String ip;

    public static void main(String[] args) {
        try (Reader reader = new Reader("client.txt")) {
            user = User.userFromHashPassword(reader.read(),reader.read());
            ip = reader.read();
            port = reader.read();
            isDark = Boolean.parseBoolean(reader.read());
        } catch (FileNotFoundException | IncorrectFileNameException | EndOfFileException ignored) { }
        try {
            UIManager.setLookAndFeel( MainClient.isDark ? new FlatDarculaLaf() : new FlatIntelliJLaf());
        } catch ( UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        IpPortWindow o = new IpPortWindow();
        collection = new Collection(0);
        o.display();
    }
}
