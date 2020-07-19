package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.data.User;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UserMenuItem extends MenuItem {

    private User user;
    private boolean selected;

    public UserMenuItem(User user) {
        super(user.getName());
        this.user = user;
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
}
