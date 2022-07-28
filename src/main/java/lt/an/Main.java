package lt.an;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    static Connection con = null;

    public static void main(String[] args) {

       try {
            con = getConnection();

            ArrayList<Customer> customers = getCustomers(con);

            customers.forEach(System.out::println);
            System.out.println("----------------------------------------");

            insertProductLine(con, "TestLine04", "TestDescription5", "TestHtmlDescription5");

           updateProductLine(con, "TestLine02","TestDescription04", "TestHtmlDescription03");

            System.out.println("----------------------------------------");
            ArrayList<ProductLine> productLines = getProductLine(con);
            productLines.forEach(System.out::println);

            //1st STEP
            con.commit();

            } catch (Exception e){
                System.out.println(e);
            try {
                //3rd STEP
                getConnection().rollback();
            }catch (SQLException e1){
                throw new RuntimeException();
            }

        }
    }

    private static void insertProductLine(
            Connection con,
            String productLine,
            String textDescription,
            String htmlDescription
    ){

        String sql = "INSERT INTO classicmodels.productlines(productLine,textDescription, htmlDescription) VALUES(?,?,?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, productLine);
            pstmt.setString(2, textDescription);
            pstmt.setString(3, htmlDescription);
            pstmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
   }

    private static void updateProductLine(
            Connection con,
            String productLine,
            String textDescription,
            String htmlDescription
    ){

        String sql = "UPDATE classicmodels.productlines SET textDescription = ?, htmlDescription = ? WHERE productLine = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, textDescription);
            pstmt.setString(2, htmlDescription);
            pstmt.setString(3, productLine);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private static ArrayList<ProductLine> getProductLine(Connection con) throws SQLException {

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select * from classicmodels.productlines");

        ArrayList<ProductLine> productLines = new ArrayList<>();

        while (rs.next()) {
            String productLineName = rs.getString("productLine");
            String textDescription = rs.getString("textDescription");
            String htmlDescription = rs.getString("htmlDescription");

            ProductLine productLine = new ProductLine(productLineName, textDescription, htmlDescription);
            productLines.add(productLine);

        }
        return productLines;
    }

    private static ArrayList<Customer> getCustomers(Connection con) throws SQLException {

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
        return customers;
    }

    private static Connection getConnection() {
        Properties props = getProperties();

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, password);
            //2nd STEP
            con.setAutoCommit(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return con;
    }

    private static Properties getProperties() {
        Properties props = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("db.properties");){
            props.load(fileInputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return props;
    }
}


