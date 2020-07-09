package program;

import commons.Collection;
import commons.User;
import swing_package.NewPortWindow;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainClient {
    public static Collection collection;
    public static final AtomicBoolean globalKillFlag = new AtomicBoolean(false);
    public static User user = new User("login", "password");
    public static void main(String[] args) {
        NewPortWindow o = new NewPortWindow();
        collection = new Collection(0); ////////todo
        o.display();
    }
}
