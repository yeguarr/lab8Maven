package command;

import commons.Route;
import commons.User;

public class CommandWithObj extends Command {
    Route route;

    public CommandWithObj(User user, Commands com, Route route) {
        super(user, com);
        this.route = route;
    }

    @Override
    public String toString() {
        return "CommandWithObj{" +
                "route=" + route +
                '}';
    }

    @Override
    public Route returnObj() {
        return route;
    }
}
