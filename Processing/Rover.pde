public class Rover implements Drawable {
  private Position position;
  private Radar radar;
  private RoverUI ui;

  private int size = 20;
  private float speed = 5;
  private boolean movingForward = false;
  private boolean movingBackward = false;

  public Rover(Position position, Radar radar) {
    this.position = position;
    this.radar = radar;
    this.ui = new RoverUI(); // Mover a dependencia si queremos testear esto
  }

  public void turnLeft() {
    position = position.turnLeft(speed);
  }

  public void turnRight() {
    position = position.turnRight(speed);
  }

  public void moveForward() {
    movingForward = true;
    position = position.moveForward(speed);
  }

  public void moveBackward() {
    movingBackward = true;
    position = position.moveBackward(speed / 4);
  }

  public void stop() {
    movingBackward = false;
    movingForward = false;
  }

  public void draw() {
    ui.draw(position, radar, size, movingBackward, movingForward);
  }
}

// UI :)

public class RoverUI {

  public void draw(Position position, Radar radar, int size, boolean movingBackward, boolean movingForward) {
    pushMatrix();

    translate(position.getX(), position.getY());
    rotate(position.getAngle());

    var hasElementsNearby = radar.detectElementsNear(position);
    if (hasElementsNearby) {
      radar.draw();
    }

    if (movingBackward || movingForward) {
      drawTaillight(size, movingBackward);
    }

    noStroke();
    drawLeftTire(size);
    drawRoverBody(size, hasElementsNearby);
    drawRightTire(size);
    stroke(1);

    popMatrix();
  }

  private void drawTaillight(int size, boolean movingBackward) {
    var lightColor = movingBackward ? color(255, 255, 255) : color(0, 255, 0);
    fill(lightColor);
    circle(-size+12, 0, 10);
  }

  private void drawRoverBody(int size, boolean hasElementsNearby) {
    fill(167, 171, 193);
    circle(0, 0, size);
    triangle(0, -size / 2, size, 0, 0, size / 2);

    var sensorColor = hasElementsNearby ? color(255, 0, 0) : color(0, 0, 0);
    fill(sensorColor);
    circle(0, 0, 5);
  }

  private void drawLeftTire(int size) {
    drawTire(-size, -size, size);
  }

  private void drawRightTire(int size) {
    drawTire(-size, size / 2, size);
  }

  private void drawTire(float x, float y, int width) {
    fill(71, 70, 75);
    rect(x, y, width * 2, width / 2);
    stroke(0, 0, 0);
    for (var i = 0; i < 5; i++) {
      line(x + (i * 10), y, x + (i * 10), y + width / 2);
    }
    noStroke();
  }
}
