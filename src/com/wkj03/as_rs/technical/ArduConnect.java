package com.wkj03.as_rs.technical;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by kevin889 on 19-05-15.
 */
    public class ArduConnect implements SerialPortEventListener
    {
        SerialPort serialPort = null;
        private static final String PORT_NAMES[] =
                {
                        "/dev/cu.usbmodem1411" // Mac OS X
                };
        private static final int TIME_OUT = 1000; // Port open timeout
        private static final int DATA_RATE = 9600; // Arduino serial port
        private String appName;
        private BufferedReader input;
        private OutputStream output;
        private String chosenSerialPort;

        public ArduConnect(String serialPort)
        {
            appName = getClass().getName();
            this.chosenSerialPort = serialPort;
        }

        public boolean initialize()
        {
            try
            {
                CommPortIdentifier portId = null;
                Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

                while (portId == null && portEnum.hasMoreElements())
                {
                    CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

                    //for (String portName : PORT_NAMES)
                    //{

                        if (currPortId.getName().equals(chosenSerialPort)
                                || currPortId.getName().startsWith(chosenSerialPort))
                        {

                            serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
                            portId = currPortId;
                            break;
                        }
                    //}
                }

                if (portId == null || serialPort == null)
                {
                    System.out.println("Oops... Could not connect to Arduino");
                    return false;
                }

                // set port parameters
                serialPort.setSerialPortParams(DATA_RATE,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                // add event listeners
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);

                // Give the Arduino some time
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ie)
                {
                }

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }

        public void sendData(String data)
        {
            try
            {
                System.out.println("Data versturen: '" + data + "'");

                output = serialPort.getOutputStream();
                output.write(data.getBytes());
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
                System.exit(0);
            }
        }

        public synchronized void close()
        {
            if (serialPort != null)
            {
                serialPort.removeEventListener();
                serialPort.close();
            }
        }

        public synchronized void serialEvent(SerialPortEvent oEvent)
        {
            System.out.println("Event ontvangen: " + oEvent.toString());
            try
            {
                switch (oEvent.getEventType())
                {
                    case SerialPortEvent.DATA_AVAILABLE:
                        if (input == null)
                        {
                            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                        }
                        String inputLine = input.readLine();

//                    if (inputLine == "false")
//                    {
//                        System.out.println("klaar!");
//                        klaar = true;
//                    }
                        System.out.println(inputLine);

                        break;

                    default:
                        break;
                }
            }

            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }



    }

