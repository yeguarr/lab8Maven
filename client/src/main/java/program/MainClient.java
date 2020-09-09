package program;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import commons.Collection;
import commons.Reader;
import commons.User;
import exceptions.EndOfFileException;
import exceptions.IncorrectFileNameException;
import swing_package.InfoMessage;
import swing_package.IpPortWindow;
import swing_package.ProgramWindow;
import swing_package.RoutesTableModel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainClient {
    public static final AtomicBoolean globalKillFlag = new AtomicBoolean(false);
    public static Collection collection;
    public static User user = new User("login", "password");
    public static RoutesTableModel rtm = new RoutesTableModel();
    public static ImageIcon img = new ImageIcon("client/src/main/resources/icon48.png");
    public static boolean isDark = false;
    public static String port;
    public static String ip;
    public static java.util.Queue<InfoMessage> messages = new ConcurrentLinkedQueue<>();
    public static int i = 2;

    public static Locale[] locales = new Locale[]{
            new Locale("ru", "RU"),
            new Locale("hu", "HU"),
            new Locale("en", "IN"),
            new Locale("nl", "NL")
    };

    /*public static ResourceBundle[] Labels = new ResourceBundle[]{
            new Label_ru_RU(),
            new Label_hu_HU(),
            new Label_en_IN(),
            new Label_nl_NL(),
    };*/

    public static ResourceBundle stats;

    public static void main(String[] args) {
        stats = ResourceBundle.getBundle("locales.Label", locales[i]);
        try (Reader reader = new Reader("client.txt")) {
            user = User.userFromHashPassword(reader.read(), reader.read());
            ip = reader.read();
            port = reader.read();
            isDark = Boolean.parseBoolean(reader.read());
            i = Integer.parseInt(reader.read());
            stats = ResourceBundle.getBundle("locales.Label", locales[i]);
        } catch (FileNotFoundException | IncorrectFileNameException | NumberFormatException | EndOfFileException ignored) {
        }
        try {
            UIManager.setLookAndFeel(MainClient.isDark ? new FlatDarculaLaf() : new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        collection = new Collection(0);
        if (user.login.equals("demo")) {
            ProgramWindow demo = new ProgramWindow();
            demo.display();
        } else {
            IpPortWindow o = new IpPortWindow();
            o.display();
        }
    }
}
