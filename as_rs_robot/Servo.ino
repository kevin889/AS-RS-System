/*
 * Hier staan de functies die de servo besturen 
 */

void servoOut() {
  myservo.write(40);
}

void servoIn() {

  for (int pos = 40; pos < 150; pos += 1)
  {
    myservo.write(pos);
    delay(15);
  }
}
