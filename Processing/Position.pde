public class Position {
  private float x;
  private float y;
  private float direction;

  public Position(float x, float y, float direction) {
    this.x = x;
    this.y = y;
    this.direction = direction;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getAngle() {
    return radians(direction);
  }

  public Position turnLeft(float speed) {
    return new Position(x, y, direction -= speed);
  }

  public Position turnRight(float speed) {
    return new Position(x, y, direction += speed);
  }

  public Position moveForward(float speed) {
    var newX = x + cos(getAngle()) * speed;
    var newY = y + sin(getAngle()) * speed;
    return new Position(newX, newY, direction);
  }

  public Position moveBackward(float speed) {
    var newX = x - cos(getAngle()) * speed;
    var newY = y - sin(getAngle()) * speed;
    return new Position(newX, newY, direction);
  }

  public float distanceWith(Position anotherPosition) {
    return dist(getX(), getY(), anotherPosition.getX(), anotherPosition.getY());
  }
}
