package command;

import commons.User;

public class Warning extends Command {
    String warning;

    public Warning(User user, String info) {
        super(user, Commands.WARNING);
        this.warning = info;
    }
    @Override
    public String returnObj() {
        return warning;
    }
}