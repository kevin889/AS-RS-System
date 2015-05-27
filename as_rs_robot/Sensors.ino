//Deze functie controlleert of de geselecteerde sensor wat waarneemt of niet.
bool detectLevel(int sensor) {
  int value = analogRead(sensor);
  
  if (sensor == horizontalSensor && value >= sensorBounds[sensor]) {
    return true;
  } else if (sensor == verticalSensor && value < sensorBounds[sensor]) {
    return true;
  } else {
    return false;
  }

  return false;

}
