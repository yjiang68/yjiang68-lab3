package edu.iit.sat.itmd4515.yjiang68;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerJDBCTest {

    private Connection connection;

    // perhaps you want to think about some utility methods
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "itmd4515";
        String password = "itmd4515";

        return DriverManager.getConnection(url, username, password);
    }

    private void createACustomer(Customer customer) throws SQLException {
        String INSERT_SQL = "insert into customer " +
                "(customer_id, store_id, first_name, last_name, email, address_id, active, create_date) " +
                "values (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
        ps.setInt(1, customer.getId());
        ps.setInt(2, customer.getStoreId());
        ps.setString(3, customer.getFirstName());
        ps.setString(4, customer.getLastName());
        ps.setString(5, customer.getEmail());
        ps.setInt(6, customer.getAddressId());
        ps.setBoolean(7, customer.getActive());
        ps.setObject(8, customer.getCreateDate());

        ps.executeUpdate();
    }

    private void deleteACustomer(Customer customer) throws SQLException {
        String DELETE_SQL = "delete from customer where customer_id = ?";
        PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
        ps.setInt(1, customer.getId());
        ps.executeUpdate();
    }

    private Customer findACustomer(Integer id) throws SQLException {
        Customer customer = null;
        String SELECT_SQL = "select * from customer where customer_id = ?";
        PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            customer = new Customer();
            customer.setId(rs.getInt("customer_id"));
            customer.setStoreId(rs.getInt("store_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setAddressId(rs.getInt("address_id"));
            customer.setActive(rs.getBoolean("active"));
            customer.setCreateDate(rs.getObject("create_date", LocalDate.class));
        }

        return customer;
    }

    private void updateACustomer(Customer customer) throws SQLException {
        String UPDATE_SQL = "update customer set " +
                "store_id = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "address_id = ?, " +
                "active = ? " +
                "where customer_id = ?";

        PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);

        ps.setInt(1, customer.getStoreId());
        ps.setString(2, customer.getFirstName());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getEmail());
        ps.setInt(5, customer.getAddressId());
        ps.setBoolean(6, customer.getActive());
        ps.setInt(7, customer.getId());

        ps.executeUpdate();

    }

    // fires once at the class level before all test cases
    @BeforeAll
    public static void beforeAll(){}

    // fires once before each test method
    @BeforeEach
    public void beforeEach() throws SQLException {
        connection = getConnection();

        // create a test customer to work with in each test case
        Customer customer = new Customer(999,1,"Test","Customer","spyrison@iit.edu",99,true, LocalDate.now());
        createACustomer(customer);
    }

    @Test
    public void createCustomerTest() throws SQLException {
        // first create a customer
        Customer customer = new Customer(9999,1,"Test","Customer","spyrison@iit.edu",99,true, LocalDate.now());
        createACustomer(customer);

        //find the customer from the database to confirm it was created
        Customer foundCustomer = findACustomer(9999);
        // assert it was created

        assertNotNull(foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());

        System.out.println(foundCustomer.toString());

        // finally, delete the test data
        deleteACustomer(customer);
    }

    @Test
    public void readCustomerTest() throws SQLException {
        // we already have a test customer (999) from beforeEach
        // just read it back from the database (hint: findACustomer)
        // and then, assert you were successful

    }

    @Test
    public void updateCustomerTest() throws SQLException {
        // we already have a test customer (999) from beforeEach
        // update it
        // then read it back from the database (hint: findACustomer)
        // assert that your updates were successful

    }

    @Test
    public void deleteCustomerTest(){
        // why would I not want to use beforeEach customer (999) for this?

        // create a new customer
        // delete the customer
        // try and find the customer you deleted
        // assert that you could not find it (it was successfully deleted)
        // Hint: findACustomer might return a null customer if nothing was found.  Can you assertNull?

    }


    // fires once after each test method
    @AfterEach
    public void afterEach() throws SQLException {
        connection = getConnection();
        Customer customer = new Customer();
        customer.setId(999);
        deleteACustomer(customer);
    }

    // fires once at the class level after all test cases
    @AfterAll
    public static void afterAll(){}

}
