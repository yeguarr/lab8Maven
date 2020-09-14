package program;

import command.*;
import commons.Collection;
import commons.Route;
import commons.User;
import commons.Writer;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static commons.Utils.*;

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
        } else {
            return new ErrorCommand(com.getUser(), "register.invalid.user");
        }
        /*Collection newC = new Collection(c.ids);
        for (User user : c.map.keySet()) {
            newC.map.put(user.secret(), c.map.get(user));
        }
        return new Show(com.getUser(),newC);*/
        return new Info(com.getUser(), "success");
    }

    private static Command login(Collection c, Command com) {
        if (!c.isUserInMap(com.getUser())) {
            return new ErrorCommand(com.getUser(), "login.invalid.user");
        }
        Collection newC = new Collection(c.ids);
        for (User user : c.map.keySet()) {
            newC.map.put(user.secret(), c.map.get(user));
        }
        return new Show(com.getUser(), newC);
    }

    public static Command show(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            Collection newC = new Collection(c.ids);
            for (User user : c.map.keySet()) {
                newC.map.put(user.secret(), c.map.get(user));
            }
            return new Show(com.getUser(), newC);
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Выводит значения поля distance в порядке возрастания
     */
    public static Command printFieldAscendingDistance(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            StringBuilder sb = new StringBuilder("server.distances/");
            if (list.size() > 0 && list.stream().anyMatch(r -> r.getDistance() != null))
                list.stream().filter(r -> r.getDistance() != null).map(Route::getDistance).sorted().forEach(dis -> sb.append(dis).append("; "));
            else
                return new Info(com.getUser(), "empty");
            return new Info(com.getUser(), sb.toString());
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * выводит объект из коллекции, значение поля creationDate которого является минимальным
     */
    public static Command minByCreationDate(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (list.size() > 0) {
                Route route = list.stream().min(Comparator.comparing(Route::getCreationDate)).get();
                return new Info(com.getUser(), "server.min/" + route.getId() + "/" + route.getName());
            } else
                return new Info(com.getUser(), "empty");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Выводит среднее значение поля distance
     */
    public static Command averageOfDistance(Collection c, Command com) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (list.size() > 0 && list.stream().anyMatch(r -> r.getDistance() != null))
                return new Info(com.getUser(), "server.average/" + list.stream().filter(r -> r.getDistance() != null).mapToDouble(Route::getDistance).average().orElse(Double.NaN));
            else
                return new Info(com.getUser(), "empty");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Удаляет все элементы коллекции, которые меньше чем заданный
     */
    public static Command removeLower(Collection c, Command com, PostgreSQL sqlRun) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (testRoute((Route) com.returnObj()))
                return new Warning(com.getUser(), "warning.route");
            list.removeIf(route -> {
                boolean bool = route.compareTo(newRoute) < 0;
                if (bool)
                    sqlRun.add(new RemoveById(com.getUser(), newRoute.getId()));
                return bool;
            });
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new CommandWithObj(com.getUser().secret(), Commands.REMOVE_LOWER, newRoute)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Удаляет все элементы коллекции, которые больше чем заданный
     */
    public static Command removeGreater(Collection c, Command com, PostgreSQL sqlRun) {
        Route newRoute = (Route) com.returnObj();
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (testRoute((Route) com.returnObj()))
                return new Warning(com.getUser(), "warning.route");
            list.removeIf(route -> {
                boolean bool = route.compareTo(newRoute) > 0;
                if (bool)
                    sqlRun.add(new RemoveById(com.getUser(), newRoute.getId()));
                return bool;
            });
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new CommandWithObj(com.getUser().secret(), Commands.REMOVE_GREATER, newRoute)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public static Command addIfMin(Collection c, Command com, PostgreSQL sqlRun) {
        int id = c.getNextId();
        Route newRoute = routeWithId((Route) com.returnObj(), id);
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (testRoute((Route) com.returnObj()))
                return new Warning(com.getUser(), "warning.route");
            if (newRoute.compareTo(list.stream().sorted().findFirst().orElse(newRoute)) <= 0) {
                list.add(newRoute);
                sqlRun.add(new CommandWithObj(com.getUser(), Commands.ADD, newRoute));
                for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                    try {
                        ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new CommandWithObj(com.getUser().secret(), Commands.ADD_IF_MIN, newRoute)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return new Info(com.getUser(), "success");
            } else return new Warning(com.getUser(), "failure");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Удаляет все элементы из коллекции
     */
    public static Command clear(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            list.clear();
            sqlRun.add(com);
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new Command(com.getUser().secret(), Commands.CLEAR)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
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
                return new Warning(com.getUser(), "failure");
            }
            list.remove(route);
            sqlRun.add(com);
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new RemoveById(com.getUser().secret(), id)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Перезаписывает элемент списка с указанным id
     */
    public static Command update(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (testRoute((Route) com.returnObj()))
                return new Warning(com.getUser(), "warning.route");
            int id = ((Route) com.returnObj()).getId();
            Route route = null;
            for (Route r : list) {
                if (r.getId().equals(id)) {
                    route = r;
                }
            }
            if (route == null) {
                return new Warning(com.getUser(), "failure");
            }
            ((Route) com.returnObj()).setCreationDate(route.getCreationDate());
            list.set(list.indexOf(route), (Route) com.returnObj());
            sqlRun.add(com);
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new CommandWithObj(com.getUser().secret(), Commands.UPDATE, (Route) com.returnObj())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    /**
     * Добавляет элемент в список
     */
    public static Command add(Collection c, Command com, PostgreSQL sqlRun) {
        List<Route> list = properUser(com.getUser(), c);
        if (list != null) {
            if (testRoute((Route) com.returnObj()))
                return new Warning(com.getUser(), "warning.route");
            int id = c.getNextId();
            list.add(routeWithId((Route) com.returnObj(), id));
            sqlRun.add(com);
            for (int i = 0; i < ServerWithProperThreads.messList.size(); i++) {
                try {
                    ServerWithProperThreads.messList.get(i).add(ServerWithProperThreads.addCommand(new CommandWithObj(com.getUser().secret(), Commands.ADD, (Route) com.returnObj())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Info(com.getUser(), "success");
        }
        return new ErrorCommand(com.getUser(), "invalid.user");
    }

    public static Route routeWithId(Route r, int id) {
        r.setId(id);
        return r;
    }

    public static boolean testRoute(Route route) {
        try {

            routeDistanceCheck.checker(route.getDistance());
            routeNameCheck.checker(route.getName());
            if (route.getFrom() != null) {
                locationXYZCheck.checker(route.getFrom().getX());
                locationXYZCheck.checker(route.getFrom().getY());
                locationXYZCheck.checker(route.getFrom().getZ());
                locationNameCheck.checker(route.getFrom().getName());
            }
            locationXYZCheck.checker(route.getTo().getX());
            locationXYZCheck.checker(route.getTo().getY());
            locationXYZCheck.checker(route.getTo().getZ());
            locationNameCheck.checker(route.getTo().getName());
            coordinatesXCheck.checker(route.getCoordinates().getX());
            coordinatesYCheck.checker(route.getCoordinates().getY());
            return false;
        } catch (Exception ignored) {
        }
        return true;
    }

    public static List<Route> properUser(User user, Collection collection) {
        if (collection.isUserInMap(user))
            return collection.map.get(user);
        return null;
    }
}

