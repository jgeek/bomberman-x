package ir.ac.kntu.data;

import java.util.List;

public interface DatabaseStateAware {
    public void update(List<User> users);
}
