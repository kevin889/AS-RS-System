/*
 * Hier in staan alle functies die de shuttle besturen
 */

void rideLeft() {
  digitalWrite(M1, HIGH);
}

void rideRight() {
  digitalWrite(M1, LOW);
}

void goRide() {
  analogWrite(E1, 255);
}

void goRide(int speed) {
  if (speed < 0) {
    speed = 0;
  } else if (speed > 255) {
    speed = 255;
  }

  analogWrite(E1, speed);

}

void stopRide() {
  analogWrite(E1, 0);
}
