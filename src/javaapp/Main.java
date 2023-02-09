package javaapp;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.*;
import java.util.regex.Pattern;
import javaapp.MongoDB;
import javaapp.ReadWrite;
import org.bson.Document;
import java.util.ArrayList;

public class Main {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/";

    public static String conStringDB(String DBname) {// สร้าง path database
        return CONN_STRING + DBname;
    }

    public static void main(String[] args) {

        Connection conn_fineart3 = null;// เปลี่ยนชื่อ table ให้ตรงกันทั้งหมด
        Connection conn_converterdb = null;//เชื่อมต่อข้อมูลใน MySQL

        try {
            ReadWrite rw = new ReadWrite();// อ่านไฟล์ config
            conn_converterdb = DriverManager.getConnection(conStringDB("converterdb"), USERNAME, PASSWORD);
            Statement stmt_config = (Statement) conn_converterdb.createStatement();
            ResultSet result_config = stmt_config.executeQuery("SELECT config FROM config WHERE id = 7");//id คือ row ที่จะเลือก
            System.out.println("passconfig");
            System.out.println(result_config);
            System.out.println("config!!!");
            String config = "";
            while (result_config.next()) {//อ่านข้อมูลจาก DatabaseSQL 
                config = result_config.getString(1);
                System.out.println(result_config.getString(1));
            }

            //String config = rw.Read();
            System.out.println(Pattern.compile(Pattern.quote("from"), Pattern.CASE_INSENSITIVE).matcher(config).find());

            String[] arrOfConfig = config.split(" ");// ตัดคำ
            ArrayList<String> useConfig = new ArrayList<String>();

            // if (Pattern.compile(Pattern.quote("from"), Pattern.CASE_INSENSITIVE).matcher(config).find()) {
            //    System.out.println("find");
            //    arrOfConfig = config.split("FROM");
            //  }
            int end = arrOfConfig.length;
            for (int i = 0; i < end; i++) {// แยกเอาคอลัมที่ต้องการ
//                System.out.println(arrOfConfig[i]);
                if (arrOfConfig[i].indexOf(",") >= 0) {

                    useConfig.add(arrOfConfig[i].substring(0, arrOfConfig[i].length() - 1));
                    end = i + 2;
                }
                if (i + 1 == end) {
                    useConfig.add(arrOfConfig[i].substring(0, arrOfConfig[i].length()));
                }
            }
            for (String a : useConfig) {
                System.out.println(a);
            }

//          rw.Write();
            conn_fineart3 = DriverManager.getConnection(conStringDB("antique"), USERNAME, PASSWORD);// เปลี่ยนข้อมูลใน " " ได้
            System.out.println("Connected SQL!");
            //String query = "SELECT * FROM `archive` LIMIT 10";//เอาคำสั่ง SQL มาใส่
            String query = config;

            Statement stmt = (Statement) conn_fineart3.createStatement();
            ResultSet result = stmt.executeQuery(query);
            System.out.println(result);

            MongoDB connection = new MongoDB();

            while (result.next()) {// ทำเป็นอัตโนมัติ

                Document document = new Document();
//                document.append("AccountName", rs_fineart3.getString("AccountName"));
//                document.append("ResourceDigitalFile", rs_fineart3.getString("ResourceDigitalFile"));
//                document.append("Description", rs_fineart3.getString("Description"));
//                document.append("LongKeyWords", rs_fineart3.getString("LongKeyWords"));

                for (int i = 0; i < useConfig.size(); i++) {
                    document.append(useConfig.get(i), result.getString(useConfig.get(i)));
                }
                System.out.println(document);
                connection.insertData(document);// เพิ่มข้อมูลใน mongo 

                System.out.println("Document inserted successfully");

//                connection.insertData(document);
//                System.out.println(rs_fineart3.getString("AccountName"));// ใส่ชื่อหัวตารางที่จะเพิ่มเข้ามา
            }

        } catch (SQLException e) {// ลองทำ ถ้าตรงไหน error จะเข้า catch
            System.err.println(e);// print error แก้ไขเฉพาะ error นั้น
        }
    }
}
