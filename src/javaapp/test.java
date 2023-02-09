package javaapp;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import javaapp.MongoDB;
import javaapp.ReadWrite;
import java.sql.*;
import java.util.regex.Pattern;

public class test extends javax.swing.JFrame {

    public test() {
        initComponents();
    }

    public static class A implements Runnable {

        private static final String USERNAME = "root";
        private static final String PASSWORD = "";
        private static final String CONN_STRING = "jdbc:mysql://localhost:3306/";

        public static String conStringDB(String DBname) {// สร้าง path database
            return CONN_STRING + DBname;
        }
        Connection conn_fineart3 = null;// เปลี่ยนชื่อ table ให้ตรงกันทั้งหมด

        @Override
        public void run() {
            //while(true)

            try {
                ReadWrite rw = new ReadWrite();// อ่านไฟล์ config
                String config = rw.Read();
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
                conn_fineart3 = DriverManager.getConnection(conStringDB("fineart3"), USERNAME, PASSWORD);// เปลี่ยนข้อมูลใน " " ได้
                System.out.println("Connected SQL!");
                //String query = "SELECT * FROM `archive` LIMIT 10";//เอาคำสั่ง SQL มาใส่
                String query = config;

                Statement stmt = (Statement) conn_fineart3.createStatement();
                ResultSet result = stmt.executeQuery(query);
                System.out.println(result);

                MongoDB connection = new MongoDB();
                MongoClient mongox = new MongoClient("localhost", 27017);//ต่อ Database NoSQL

                MongoDatabase dbx = mongox.getDatabase("mongo");
                MongoCollection<org.bson.Document> collectionx = dbx.getCollection("mongotest");

                while (result.next()) {// ทำให้เป็นอัตโนมัติ

                    Document document = new Document();
//                document.append("AccountName", rs_fineart3.getString("AccountName"));
//                document.append("ResourceDigitalFile", rs_fineart3.getString("ResourceDigitalFile"));
//                document.append("Description", rs_fineart3.getString("Description"));
//                document.append("LongKeyWords", rs_fineart3.getString("LongKeyWords"));

                    for (int i = 0; i < useConfig.size(); i++) {
                        document.append(useConfig.get(i), result.getString(useConfig.get(i)));
                    }
                    System.out.println(document);

                    System.out.println(useConfig.get(0));
                    /// System.out.println(result.getString(useConfig.get(0)));

                    Bson queryx = eq(useConfig.get(0), result.getString(useConfig.get(0)));//เงื่อนไขการลบ MongoDB
                    collectionx.deleteMany(queryx);//ลบข้อมูลใน MOngoDB ที่ซ้ำกัน

                    connection.insertData(document);// เพิ่มข้อมูลใน mongo 

                    System.out.println("Document inserted successfully");

//                connection.insertData(document);
//                System.out.println(rs_fineart3.getString("AccountName"));// ใส่ชื่อหัวตารางที่จะเพิ่มเข้ามา
                }
            } catch (SQLException e) {// ลองทำ ถ้าตรงไหน error จะเข้า catch
                System.err.println(e);// print error แก้ไขเฉพาะ error นั้น
            }

            System.out.println("Thread Run 2");
            //  Thread.sleep( 10000 );
        }
    ;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Config");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(268, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Thread T = new Thread(new A());
        T.start();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
