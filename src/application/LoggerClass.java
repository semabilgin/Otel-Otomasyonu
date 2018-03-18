/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerClass {
     public void loggerFunction(Exception ex) {
        try {
            FileWriter yaz = new FileWriter("LogDosyasi.txt", true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("[dd.MM.yy - HH:mm EEE]");

            for (int i = 0; i < ex.getStackTrace().length; i++) {
                String date = dateFormat.format(new Date());
                String className = ex.getStackTrace()[i].getClassName();
                int lineNumber = ex.getStackTrace()[i].getLineNumber();
                String exMessage = ex.getMessage();
                yaz.append(date + "  LINE:" + lineNumber + "  CLASSNAME:" + className + "  MESSAGE:" + exMessage + System.lineSeparator());
                yaz.close();
            }
        } catch (IOException e) {
//            System.out.println(e.getMessage());
        }
    }

}
