/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteclicker_controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tim
 */
public class ConnectionManager {
    
    //private static String url = "jdbc:mysql://13.86.216.212:3306/remote_clicker";
    //private static String url = "jdbc:mysql://13.64.182.19:3306/remote-gear?sslmode=verify-full&sslrootcert=/cert/BaltimoreCyberTrustRoot.crt.pem";    
    private static String url = "jdbc:mysql://13.64.182.19:3306/remote-gear";
    //private static String url = "jdbc:mysql://137.135.24.147:3306/remote-gear";
    private static String driverName = "com.mysql.jdbc.Driver";   
    private static String username = "bseengineer";   
    private static String password = "SeaTeaYEngineer7!";
    private static Connection con;
    private static String urlstring;
   
    //    Class.forName("com.mysql.jdbc.Driver");  
    //    Connection con=DriverManager.getConnection(  
    //    "jdbc:mysql://13.86.216.212:3306/remote_clicker","bseengineer@remoteclicker","SeaTeaYEngineer7!");   
    //    Statement stmt=con.createStatement();
    
    public static Connection getConnection() {
        
        System.out.println("getConnection() summoned");

        /** generate truststore and keystore in code
        String importCert = " -import "+
            " -alias mysqlServerCACert "+
            " -file " + ssl_ca +
            " -keystore truststore "+
            " -trustcacerts " +
            " -storepass password -noprompt ";
        String genKey = " -genkey -keyalg rsa " +
            " -alias mysqlClientCertificate -keystore keystore " +
            " -storepass password123 -keypass password " +
            " -dname CN=MS ";
        sun.security.tools.keytool.Main.main(importCert.trim().split("\\s+"));
        sun.security.tools.keytool.Main.main(genKey.trim().split("\\s+"));

        // use the generated keystore and truststore
        System.setProperty("javax.net.ssl.keyStore","path_to_keystore_file");
        System.setProperty("javax.net.ssl.keyStorePassword","password");
        System.setProperty("javax.net.ssl.trustStore","path_to_truststore_file");
        System.setProperty("javax.net.ssl.trustStorePassword","password");
      
      **/
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url,username,password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
        return con;
    }
}
    

