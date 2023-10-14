private Planet planet;
private Rover rover;

private int planetWidth = 600;
private int planetHeight = 600;
private int numberOfRocks = 10;

void settings() {
  size(planetWidth, planetHeight);
}

void setup() {
  planet = new Planet(planetWidth, planetHeight);
  
  for (var i = 0; i < numberOfRocks; i++) {
    planet.add(new Rock(planet.getRandomPosition()));
  }
 
  var radar = new Radar(planet.getElements());
  rover = new Rover(planet.getRandomPosition(), radar);
  planet.add(rover);
}

void draw() {
  planet.draw();
}

void keyPressed() {
  if (keyCode == LEFT)  { rover.turnLeft(); }
  if (keyCode == RIGHT) { rover.turnRight(); }
  if (keyCode == UP)    { rover.moveForward(); }
  if (keyCode == DOWN)  { rover.moveBackward(); }
  if (keyCode == ENTER) { setup(); }
}

void keyReleased() {
  rover.stop();
}

interface Drawable {
  void draw();
}

interface Detectable {
  float distanceWith(Position position);
}
