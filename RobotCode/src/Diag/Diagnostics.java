/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diag;

import ModClasses.MyJoystick;
import Utils.Config.Drive;
import javax.microedition.io.Connector;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author raiderbot-2
 */
public class Diagnostics {

    String fileName;
    DataOutputStream file;
    FileConnection connector;
    Drive d;
    MyJoystick joy;
    
    /**
     * 
     * @param d 
     * @param joy 
     */
    public Diagnostics(Drive d, MyJoystick joy) {

        this.joy = joy;
        initilizeFile();

    }

    public void initilizeFile() {

        try {
            connector = (FileConnection) Connector.open(initilizeFilename(), Connector.WRITE);
            connector.create();
            file = connector.openDataOutputStream();
        } catch (IOException e) {

            System.err.println("Error in initilizing file");

        }

    }

    private String initilizeFilename() {

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        fileName = date.toString();
        return fileName;

    }

    private void printToFile(String string) {
        try {
            file.writeChars(string);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void checkEncoders() throws UnsupportedEncodingException {

    }

    private void checkJoystick() {

        printToFile("Joystick X is" + String.valueOf(joy.getX()));
        printToFile("Joystick Y is" + String.valueOf(joy.getY()));
    }


    public void run(){
        
        printToFile(String.valueOf(System.currentTimeMillis()));
        checkJoystick();
        printToFile("\n");
        }




}
