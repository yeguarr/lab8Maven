package program;

import command.*;
import commons.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс - обработчик команд с консоли
 */

public class CommanderServer {
    /**
     * Обработка команд, вводимых с консоли
     */
    public static Command switcher(Command com, Collection c, PostgreSQL sqlRun) {
        switch (com.getCurrent()) {
            case SHOW://нет
                return show(c, com);
            case ADD://да
                return add(c, com, sqlRun);
            case UPDATE://да
                return update(c, com, sqlRun);
            case REMOVE_BY_ID://да
                return removeById(c, com, sqlRun);
            case CLEAR://да
                return clear(c, com, sqlRun);
            case ADD_IF_MIN://да
                return addIfMin(c, com, sqlRun);
            case REMOVE_GREATER://да
                return removeGreater(c, com, sqlRun);
            case REMOVE_LOWER://да
                return removeLower(c, com, sqlRun);
            case AVERAGE_OF_DISTANCE://нет
                return averageOfDistance(c, com);
            case MIN_BY_CREATION_DATE://нет
                return minByCreationDate(c, com);
            case PRINT_FIELD_ASCENDING_DISTANCE://нет
                return printFieldAscendingDistance(c, com);
            case LOGIN://нет
                return login(c, com);
            case REGISTER://да
                return register(c, com, sqlRun);
            default:
                Writer.writeln("Такой команды нет");
        }
        return new ErrorCommand(com.getUser(), "switcher.noCommand");
    }

    private static Command register(Collection c, Command com, PostgreSQL sqlRun) {
        if (!c.isLoginUsed(com.getUser().login)) {
            c.map.put(com.getUser(), new CopyOnWriteArrayList<>());
            sqlRun.add(com);
        }
        else {
            return new ErrorCommand(com.getUser(),"register.invalid.user");
        }
        return new Show(com.getUser(),c);
    }

    private static Command login(Collection c, Command com) {
        if (!c.isUserInMap(com.getUser())) {
            return new ErrorCommand(com.getUser(),"login.invalid.user");
        }
        return new Show(com.getUser(),c);
    }

    public static Command show(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            return new Show(com.getUser(),c);
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }
    /**
     * Выводит значения поля distance в порядке возрастания
     */
    public static Command printFieldAscendingDistance(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            final String[] s = {""};
            if (list.size() > 0)
                list.stream().filter(r -> r.getDistance() != null).map(Route::getDistance).sorted().forEach(dis -> s[0] +=(dis+"\n"));
            else
                return new Info(com.getUser(),"empty");
            return new Info(com.getUser(),s[0]);
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * выводит объект из коллекции, значение поля creationDate которого является минимальным
     */
    public static Command minByCreationDate(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (list.size() > 0)
                return new Info(com.getUser(), String.valueOf(list.stream().min(Comparator.comparing(Route::getCreationDate)).get()));
            else
                return new Info(com.getUser(),"empty");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Выводит среднее значение поля distance
     */
    public static Command averageOfDistance(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (list.size() > 0)
                return new Info(com.getUser(), String.valueOf(list.stream().filter(r -> r.getDistance() != null).mapToDouble(Route::getDistance).average().orElse(Double.NaN)));
            else
                return new Info(com.getUser(),"empty");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Удаляет все элементы коллекции, которые меньше чем заданный
     */
    public static Command removeLower(Collection c, Command com, PostgreSQL sqlRun) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.removeIf(route -> {
                boolean bool = route.compareTo(newRoute) < 0;
                if (bool)
                    sqlRun.add(new RemoveById(com.getUser(), newRoute.getId()));
                return bool;
            });
            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Удаляет все элементы коллекции, которые больше чем заданный
     */
    public static Command removeGreater(Collection c, Command com, PostgreSQL sqlRun) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.removeIf(route -> {
                boolean bool = route.compareTo(newRoute) > 0;
                if (bool)
                    sqlRun.add(new RemoveById(com.getUser(), newRoute.getId()));
                return bool;
            });
            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public static Command addIfMin(Collection c, Command com, PostgreSQL sqlRun) {
        int id = c.getNextId();
        Route newRoute = routeWithId((Route) com.returnObj(), id);
        List<Route> list = properUser( com.getUser(), c);
        if (list != null) {
            if (newRoute.compareTo(list.stream().sorted().findFirst().orElse(newRoute)) < 0) {
                list.add(newRoute);
                sqlRun.add(new CommandWithObj(com.getUser(), Commands.ADD, newRoute));
                return new Info(com.getUser(),"success");
            } else return new Info(com.getUser(),"failure");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Удаляет все элементы из коллекции
     */
    public static Command clear(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.clear();
            sqlRun.add(com);
            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Удаляет все элементы по его id
     */
    public static Command removeById(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            Integer id = (Integer) com.returnObj();
            Route route = null;
            for (Route r : list) {
                if (r.getId().equals(id))
                    route = r;
            }
            if (route == null) {
                return new Info(com.getUser(),"failure");
            }
            list.remove(route);
            sqlRun.add(com);
            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Перезаписывает элемент списка с указанным id
     */
    public static Command update(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            int id = ((Route) com.returnObj()).getId();
            Route route = null;
            for (Route r : list) {
                if (r.getId().equals(id)) {
                    route = r;
                }
            }
            if (route == null) {
                return new Info(com.getUser(),"failure");
            }
            list.set(list.indexOf(route), (Route) com.returnObj());
            sqlRun.add(com);
            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
    }

    /**
     * Добавляет элемент в список
     */
    public static Command add(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            int id = c.getNextId();
            list.add(routeWithId((Route) com.returnObj(), id));
            sqlRun.add(com);

            return new Info(com.getUser(),"success");
        }
        return new ErrorCommand(com.getUser(),"invalid.user");
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

