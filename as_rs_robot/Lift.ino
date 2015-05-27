/*
 * Hier staan de functies waarmee de lift bestuurd kan worden
 */

void up() {
  digitalWrite(M2, LOW);
}

void down() {
  digitalWrite(M2, HIGH);
}

void goLift() {
  analogWrite(E2, 255);
}

void stopLift() {
  analogWrite(E2, 0);
}
