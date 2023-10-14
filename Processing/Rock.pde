public class Rock implements Drawable, Detectable {
  private Position position;
  private float size;
  private RockUI ui;

  public Rock(Position position) {
    this.position = position;
    this.size = random(5, 50);
    this.ui = new RockUI(); // Mover a dependencia si queremos testear esto
  }

  public float distanceWith(Position anotherPosition) {
    return position.distanceWith(anotherPosition);
  }

  public void draw() {
    ui.draw(position, size);
  }
}

// UI :)

public class RockUI {
  public void draw(Position position, float size) {
    fill(126, 56, 56);
    circle(position.getX(), position.getY(), size);
  }
}
