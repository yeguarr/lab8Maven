package command;

import commons.User;

public class Info extends Command {
    String info;

    public Info(User user, String info) {
        super(user, Commands.INFO);
        this.info = info;
    }
    @Override
    public String returnObj() {
        return info;
    }
}