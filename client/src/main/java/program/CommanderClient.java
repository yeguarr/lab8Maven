package program;

import command.*;
import commons.*;
import swing_package.AlarmWindow;

import java.util.List;

/**
 * Класс - обработчик команд с консоли
 */

public class CommanderClient {
    public static void switcher(Command com, Collection c) {
        switch (com.getCurrent()) {
            case ADD:
                 add(c, com);
                 return;
            case UPDATE:
                 update(c, com);
                 return;
            case REMOVE_BY_ID:
                 removeById(c, com);
                 return;
            case CLEAR:
                 clear(c, com);
                 return;
            case ADD_IF_MIN:
                 addIfMin(c, com);
                 return;
            case REMOVE_GREATER:
                 removeGreater(c, com);
                 return;
            case REMOVE_LOWER:
                 removeLower(c, com);
                 return;
            case INFO: {
                AlarmWindow obj = new AlarmWindow();
                obj.display("INFO", ((Info) com).returnObj());
                break;
            }
            case ERROR: {
                AlarmWindow obj = new AlarmWindow();
                obj.display("ERROR", ((ErrorCommand) com).returnObj());
                break;
            }
            case SHOW: {
                MainClient.collection = ((Show) com).returnObj();
                break;
            }
            default:
                Writer.writeln("Такой команды нет");
        }
    }

    /**
     * Удаляет все элементы коллекции, которые меньше чем заданный
     */
    public static void removeLower(Collection c, Command com) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.removeIf(route -> route.compareTo(newRoute) < 0);
        }
    }

    /**
     * Удаляет все элементы коллекции, которые больше чем заданный
     */
    public static void removeGreater(Collection c, Command com) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.removeIf(route -> route.compareTo(newRoute) > 0);
        }
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public static void addIfMin(Collection c, Command com) {
        int id = c.getNextId();
        Route newRoute = routeWithId((Route) com.returnObj(), id);
        List<Route> list = properUser( com.getUser(), c);
        if (list != null) {
            if (newRoute.compareTo(list.stream().sorted().findFirst().orElse(newRoute)) <= 0) {
                list.add(newRoute);
            }
        }
    }

    /**
     * Удаляет все элементы из коллекции
     */
    public static void clear(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.clear();
        }
    }

    /**
     * Удаляет все элементы по его id
     */
    public static void removeById(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            Integer id = (Integer) com.returnObj();
            Route route = null;
            for (Route r : list) {
                if (r.getId().equals(id))
                    route = r;
            }
            if (route != null) {
                list.remove(route);
            }
        }
    }

    /**
     * Перезаписывает элемент списка с указанным id
     */
    public static void update(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            int id = ((Route) com.returnObj()).getId();
            Route route = null;
            for (Route r : list) {
                if (r.getId().equals(id)) {
                    route = r;
                }
            }
            if (route != null) {
                list.set(list.indexOf(route), (Route) com.returnObj());
            }
        }
    }

    /**
     * Добавляет элемент в список
     */
    public static void add(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            int id = c.getNextId();
            list.add(routeWithId((Route) com.returnObj(), id));
            Writer.writeln("add");
        }
    }

    public static Route routeWithId(Route r, int id) {
        r.setId(id);
        return r;
    }

    public static List<Route> properUser(User user, Collection collection) {
        if (collection.isUserInMap(user))
            return collection.map.get(user);
        return null;
    }
}
