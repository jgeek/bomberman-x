package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.components.Bomberman;
import ir.ac.kntu.data.User;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class UserMenuItem extends MenuItem {

    private User user;
    private boolean selected;
    private List<User> selectedUsers;

    public UserMenuItem(User user) {
        super(user.getName());
        this.user = user;

        setOnMousePressed(event -> {
            if (selectedUsers.contains(user)) {
                selectedUsers.remove(user);
                setSelected(false);
                System.out.println(String.format("%s removed ", user));
            } else {
                int maxPlayerCount = Bomberman.SYSTEM_NAMES.values().length - 1;
                if (selectedUsers.size() == maxPlayerCount) {
                    System.out.println(String.format("at most %s player can be added", maxPlayerCount));
                    return;
                }
                selectedUsers.add(user);
                setSelected(true);
                System.out.println(String.format("%s added ", user));
            }
        });
    }

    public User getUser() {
        return user;
    }

    protected double getItemWidth() {
        return 150;
    }

    @Override
    protected Rectangle getItemRectangle() {
        return new Rectangle(150, Constants.MENU_ITEM_HEIGHT);
    }

    public void setSelected(boolean selected) {
        setIgnoreEffect(selected);
        this.selected = selected;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
}
