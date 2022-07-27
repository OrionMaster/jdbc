package lt.an;

public class Customer {
    private int customerNumber;
    private String customerName;
    private String phone;
    private String city;

    public Customer(int customerNumber, String customerName) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
    }

    public Customer(int customerNumber, String customerName, String phone, String city) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.phone = phone;
        this.city = city;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "{" +
                "customerNumber=" + customerNumber +
                ", customerName='" + customerName + '\'' +
                ", phone='" + phone +  '\'' +
                ", city='" + city +'\'' +
                '}';
    }
}
