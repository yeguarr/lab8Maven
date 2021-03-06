package commons;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс для хранения и обработки LinkedList
 */
public class Collection implements Serializable {

    /**
     * Список, в котором хранятся элементы типа program.Route
     */
    public Map<User, List<Route>> map = new ConcurrentHashMap<>();
    public int ids;
    /**
     * Дата создания списка
     */
    private Date date = new Date();
    public Collection(int SEQUENCE) {
        ids = SEQUENCE;
    }

    public User userFromRoute(Route route) {
        User user = null;
        myLabel:
        for (User u : map.keySet()) {
            for (Route r : map.get(u))
                if (r.equals(route)) {
                    user = u;
                    break myLabel;
                }
        }
        return user;
    }

    public boolean isUserInMap(User user) {
        if (user.login.equals("login")) return false;
        return map.containsKey(user);
    }

    public Route getRouteById(int id) {
        Route route = null;
        myLabel:
        for (User u : map.keySet()) {
            for (Route r : map.get(u))
                if (r.getId().equals(id)) {
                    route = r;
                    break myLabel;
                }
        }
        return route;
    }

    public boolean isLoginUsed(String login) {
        if (login.equals("login")) return true;
        for (User user : map.keySet()) {
            if (user.login.equals(login))
                return true;
        }
        return false;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Метод, возвращающий уникальный id
     */
    public int getNextId() {
        return ids++;
    }

    /*public void getAll(PostgreSQL sqlRun, Collection c) {
        sqlRun.setRoutes(c.map);
    }*/
}
