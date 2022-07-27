package lt.an;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        try {
            //   Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/classicmodels", "root", "M_as4ter1");

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
 //                   System.out.println(String.format("%s | %s | %s | %s", customerNumber, customerName, customerCity, customerPhone));

                }

            con.close();
            customers.forEach(System.out::println);

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }


