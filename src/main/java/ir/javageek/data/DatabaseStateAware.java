package ir.javageek.data;

import java.util.List;

public interface DatabaseStateAware {
    public void update(List<User> users);
}
