import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        // Η απαραίτητη προσθήκη για να βρεθεί ο Driver
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        
        // Το URL χωρίς μονά εισαγωγικά και με παραμέτρους για σιγουριά
        String url = "jdbc:mysql://localhost:3306/Smart-Inventory?serverTimezone=UTC&useSSL=false";
        String user = "root";
        String password = ""; 

        return DriverManager.getConnection(url, user, password);
    }
}