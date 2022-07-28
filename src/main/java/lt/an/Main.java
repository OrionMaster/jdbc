package lt.an;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream("db.properties");
            props.load(fileInputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from customers");

            ArrayList<Customer> customers = new ArrayList<>();

            while (rs.next()) {
                int customerNumber = rs.getInt("customerNumber");
                String customerName = rs.getString("customerName");
                String customerCity = rs.getString("city");
                String customerPhone = rs.getString("phone");

                Customer customer = new Customer( customerNumber, customerName, customerPhone, customerCity);
                customers.add(customer);

                }

            con.close();
            customers.forEach(System.out::println);

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }


