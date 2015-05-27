/**
 * Geef een X en een Y op waar de robot naartoe moet rijden
 */
void goTo(int x, int y) {

  //Bereken het verschil tussen de huidige positie en de eind positie
  int tempX = x - currX;
  int tempY = y - currY;

  //Kijk of de robot naar links of naar rechts moet gaan
  if (tempX < 0) {
    rideLeft();
  } else if (tempX > 0) {
    rideRight();
  }

  //Kijk of de robot omhoog of omlaag moet gaan
  if (tempY < 0) {
    down();
  } else if (tempY > 0) {
    up();
  }

  //initialiseer variabelen
  bool detectedX = false;
  bool detectedY = false;
  
  int count = 0;
  
  //Rij met een snelheid van 175
  goRide(175);

  //Zolang de huidige X niet gelijk is aan de eind positie X blijft deze while lopen
  while (currX != x) {
    //Als er een object wordt gedetecteerd en de robot rijdt naar links en de huidige x is niet gelijk aan de eind positie x en er is nog geen object gedetecteerd door de sensor, wordt er 1 bij de huidige x opgeteld.
    if (detectLevel(horizontalSensor) && tempX < 0 && currX != x && detectedX == false) {
      count++;
      detectedX = true;
      if (count != 1) {
        currX--;
      }
      Serial.println(currX);
      
    } else if (detectLevel(horizontalSensor) && tempX > 0 && currX != x && detectedX == false) {
      count++;
      detectedX = true;
      if (count != 1) {
        currX++;
      }
      Serial.println(currX);
    }

    if (!detectLevel(horizontalSensor)) {
      detectedX = false;
    }

    //Wanneer de huidige X gelijk is aan de eind positie van X, stopt de robot met rijden.
    if (currX == x) {
      stopRide();

      delay(1000);
    }
  }
  
  goLift();
  //Zolang de huidige Y niet gelijk is aan de eind positie Y blijft deze while lopen
  while (currY != y) {

    if (detectLevel(verticalSensor) && tempY < 0 && currY != y && detectedY == false) {
      detectedY = true;
      currY--;
    } else if (detectLevel(verticalSensor) && tempY > 0 && currY != y && detectedY == false) {
      detectedY = true;
      currY++;
    }

    if (!detectLevel(verticalSensor)) {
      detectedY = false;
    }

    //Wanneer de huidige Y gelijk is aan de eind positie van Y, stopt de lift.
    if (currY == y) {
      stopLift();
    }
  }

  Serial.println(currX);

}

//Pakt een pakkettje uit het magazijn
void pickProduct(){
  servoOut();
  delay(200);
  up();
  goLift();
  delay(1000);
  stopLift();
  servoIn();
}

//Gaat naar de BPP robot toe, vertelt de robot dat er een pakkettje aankomt, en of deze links of rechrt in een bin moet. vervolgend dumpt de AR/RS robot het pakkettje op de lopendeband. 
void dumpAtBPP(int side){
  goTo(6,2);

  tellBpp(side);
  
  servoOut();
  down();
  goLift();
  delay(1000);
  stopLift();
  servoIn();
}

