void receiveEvent(int howMany) {
  Serial.println("Event received");
}

void tellBpp(int side){
  Wire.beginTransmission(4);    //Begin connectie
  Wire.write(side);             //Stuur data over kanaal 4 naar de andere arduino
  Wire.endTransmission();       //Eindig de connectie
}

