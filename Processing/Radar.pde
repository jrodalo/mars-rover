public class Radar implements Drawable {
  private List<Drawable> elements;
  private RadarUI ui;

  private int sensorDistance = 70;

  public Radar(List<Drawable> elements) {
    this.elements = elements;
    this.ui = new RadarUI(); // Mover a dependencia si queremos testear esto
  }

  public boolean detectElementsNear(Position roverPosition) {
    return elements.stream()
      .filter(element -> element instanceof Detectable)
      .anyMatch(element -> isNear(((Detectable) element), roverPosition));
  }

  private boolean isNear(Detectable element, Position roverPosition) {
    return element.distanceWith(roverPosition) < sensorDistance;
  }

  public void draw() {
    ui.draw(sensorDistance);
  }
}

// UI :)

public class RadarUI {

  private int currentSensorWidth = 10;

  public void draw(int sensorDistance) {
    var maxSensorWidth = sensorDistance + 30;
    currentSensorWidth = currentSensorWidth > maxSensorWidth ? 10 : currentSensorWidth + 1;

    noStroke();
    fill(255, 245, 204, maxSensorWidth - currentSensorWidth);
    circle(0, 0, currentSensorWidth);
  }
}
