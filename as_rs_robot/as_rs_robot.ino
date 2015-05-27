#include <Servo.h>
#include <Wire.h>

//Testmode
bool testMode = true;

//Servo
Servo myservo;

//Motor shield configuration
int E1 = 5;
int M1 = 4;
int E2 = 6;
int M2 = 7;

//Keep track of current position
int currX = 0;
int currY = 0;

//Servo defenitions
int horizontalSensor = 0;
int verticalSensor = 1;

int sensorBounds[] = {940, 400};

//initialize vars
String str;     //For reading a whole string trouh serial

void setup() {
  //Start serial
  Serial.begin(9600);

  //Start bus connection
  Wire.begin(4);   //address of this arduino
  Wire.onReceive(receiveEvent);

  //Attach and reset the servo
  myservo.attach(9);
  servoIn();

  //Setup motors
  pinMode(M1, OUTPUT);
  pinMode(M2, OUTPUT);

  Serial.println("AR/RS Robot started");
}

void loop() {
  
  Serial.println(analogRead(horizontalSensor));

  if (Serial.available()) {

    char c = 'h';
    
    if(testMode == false){
      //Read products from Java application
      str = Serial.readStringUntil(';');
      goTo(getValue(str, ',', 0).toInt(), getValue(str, ',', 1).toInt());
      pickProduct();
      dumpAtBPP(getValue(str, ',', 2).toInt());
    } else {
      c = Serial.read();
    }
     
    if (c == 'u') {
      up();
    } else if (c == 'd') {
      down();
    } else if (c == 'l') {
      rideLeft();
    } else if (c == 'r') {
      rideRight();
    } else if (c == 'k') {
      goRide(200);
    } else if (c == 'j') {
      stopRide();
    } else if (c == 'g') {
      goLift();
    } else if (c == 's') {
      stopLift();
    } else if (c == 'i') {
      servoIn();
    } else if (c == 'o') {
      servoOut();
    } else if (c == 't') {
      goTo(0, 4);
      goTo(0, 4);
      goTo(1, 4);
      goTo(1, 4);
      goTo(2, 4);
      goTo(3, 4);
      goTo(4, 4);
      goTo(2, 4);
      goTo(1, 4);
      goTo(0, 4);
      goTo(0, 4);
      goTo(1, 4);
      goTo(2, 4);
      goTo(3, 4);

      //Serial.println("done1");
      //goTo(2,4);
      //Serial.println("done2");
      //goTo(4,4);

    } else if (c == 'p') {
      pickProduct();
    }
  }

}
