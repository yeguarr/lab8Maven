package command;

import commons.Collection;
import commons.User;

public class Show extends Command {
    Collection collection;
    public Show(User user, Collection collection) {
        super(user, Commands.SHOW);
        this.collection = collection;
    }
    @Override
    public Collection returnObj() {
        return collection;
    }
}
