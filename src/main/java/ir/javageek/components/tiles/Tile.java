package ir.javageek.components.tiles;

import ir.javageek.components.Bomberman;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Tile extends StackPane {

    protected ImageView baseView;
    protected double width, height;
    protected int row, col;
    protected boolean canPassThrow;
    private boolean inFire;
    private Tile guestTile;
    private Tile hostTile;

    public Tile(int row, int col, double width, double height, double x, double y) {
        this.row = row;
        this.col = col;
        setWidth(width);
        setHeight(height);
        this.width = width;
        this.height = height;
        setTranslateX(x);
        setTranslateY(y);
    }

    public Tile() {
    }

    public void init() {
        baseView = createBaseImageView();
        baseView.setFitWidth(width);
        baseView.setFitHeight(height);
        getChildren().add(baseView);
    }

    public boolean isExplodable() {
        return false;
    }

    protected abstract ImageView createBaseImageView();

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract boolean canPassThrow(Bomberman.Direction direction);

    public void setCanPassThrow(boolean canPassThrow) {
        this.canPassThrow = canPassThrow;
    }

    public boolean isCanPassThrow() {
        return canPassThrow;
    }

    public boolean isInFire() {
        return inFire;
    }

    public void setInFire(boolean inFire) {
        this.inFire = inFire;
    }

    public Tile getGuestTile() {
        return guestTile;
    }

    public void setGuestTile(Tile guestTile) {
        this.guestTile = guestTile;
        if (guestTile != null) {
            guestTile.setHostTile(this);
        }
    }

    public Tile getHostTile() {
        return hostTile;
    }

    public void setHostTile(Tile hostTile) {
        this.hostTile = hostTile;
    }
}
