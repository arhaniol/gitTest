import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;


public class MySqlClass {
    private Connection mysqlConnection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public MySqlClass() {
        CreateConnection("db.properties");
    }

    /**
     *
     * @param file filename with properties of database server connection
     */
    private void CreateConnection(String file) {
        if (file.length() == 0) {
            throw new IllegalArgumentException("No file name!");
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            //load properties form file
            Properties properties = new Properties();
            properties.load(fileInputStream);

            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            mysqlConnection = DriverManager.getConnection(url, user, pass);

            statement = mysqlConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM people");
            while(resultSet.next()){
                System.out.println("imie: "+resultSet.getString(1)+"\t"+"nazwisko: "+resultSet.getString(2));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
            }
        }
    private void close() {
        try {
            if (mysqlConnection != null) {
                mysqlConnection.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        catch (Exception ignored){

        }
    }
}
