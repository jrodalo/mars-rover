import java.util.List;

public class Planet implements Drawable {
  private int maxWidth;
  private int maxHeight;
  private List<Drawable> elements;
  private PlanetUI ui;

  public Planet(int maxWidth, int maxHeight) {
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    this.elements = new ArrayList<>();
    this.ui = new PlanetUI(); // Mover a dependencia si queremos testear esto
  }

  public List<Drawable> getElements() {
    return elements;
  }

  public void add(Drawable element) {
    elements.add(element);
  }

  private Position getRandomPosition() {
    var x = random(0, maxWidth);
    var y = random(0, maxHeight);
    var direction = random(0, 360);
    return new Position(x, y, direction);
  }
  
  public void draw() {
    ui.draw(maxWidth, maxHeight, elements);
  }
}

// UI :)

public class PlanetUI {

  public void draw(int maxWidth, int maxHeight, List<Drawable> elements) {
    fill(188, 82, 63);
    rect(0, 0, maxWidth, maxHeight);

    for (Drawable element : elements) {
      element.draw();
    }
  }
}
