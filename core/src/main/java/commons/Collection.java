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

    public Collection(int SEQUENCE)
    {
        ids = SEQUENCE;
    }

    /**
     * Список, в котором хранятся элементы типа program.Route
     */
    public Map<User, List<Route>> map = new ConcurrentHashMap<>();

    public int ids;
    /**
     * Дата создания списка
     */
    private Date date = new Date();

    public boolean isUserInMap(User user) {
        if (user.login.equals("login")) return false;
        return map.containsKey(user);
    }

    public boolean isLoginUsed(String login) {
        if (login.equals("login")) return true;
        for (User user : map.keySet()) {
            if (user.login.equals(login))
                return true;
        }
        return false;
    }

    /**
     * Метод, осуществляющий поиск элемента по id
     */
    public Route searchById(Integer id) {
        for (User user: map.keySet()) {
            for (Route r : map.get(user)) {
                if (r.getId().equals(id))
                    return r;
            }
        }
        return null;
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
