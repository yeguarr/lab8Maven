package command;

import commons.User;

public class ErrorCommand extends Command {
    String error;

    public ErrorCommand(User user,String error) {
        super(user, Commands.ERROR);
        this.error = error;
    }
    @Override
    public String returnObj() {
        return error;
    }
}
