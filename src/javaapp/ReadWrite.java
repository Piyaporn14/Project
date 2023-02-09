package javaapp;

import com.mongodb.connection.Server;
import java.io.IOException;
import java.nio.file.*;
import java.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadWrite {

    static String Read() {
        String allLine = "";
        try {
            Path file = Paths.get("C:/temp/config2.txt");
            BufferedReader reader = Files.newBufferedReader(file,StandardCharsets.UTF_8);
            String line = null;

            while ((line = reader.readLine()) != null) {
               // System.out.println(line);
                allLine = allLine + line;
            }
            System.out.println(allLine);
            return allLine;

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return allLine;
        }
    }

    static void Write() {
        try {
            Path file = Paths.get("C:/temp/dee.txt");//
            BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8);

            for (int i = 0; i < 10; i++) {
                writer.write("This is content lines " + (i + 1));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
//    //ดึงข้อมูลมาแสดง
//     static void ShowData() {
//         String sql = "Select * from database sql";
//         try {
//             
//         }
//         catch(Exception e) {
//            System.err.println("IOException: " + e.getMessage());{
//             
//         }
//     }
   }

