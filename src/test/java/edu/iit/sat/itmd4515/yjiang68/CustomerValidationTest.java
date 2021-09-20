package edu.iit.sat.itmd4515.yjiang68;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

public class CustomerValidationTest {

    private static Validator validator;

    // fires once at the class level before all test cases
    @BeforeAll
    public static void beforeAll(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // fires once before each test method
    @BeforeEach
    public void beforeEach() {}

    // rainy day test case- we are expecting it to fail
    // so our assertions should assert that it did fail, and the test passes if validation fails
    @Test
    public void invalidCustomerEmail_validationShouldFail(){
        Customer c = new Customer(1,1,"Test","Customer","invalidiit.edu",99,true, LocalDate.now());

        Set<ConstraintViolation<Customer>> violations = validator.validate(c);
        assertEquals(1, violations.size() );

        for (ConstraintViolation<Customer> violation : violations) {
            System.out.println(violation.toString());
        }
    }


    // fires once after each test method
    @AfterEach
    public void afterEach() {}

    // fires once at the class level after all test cases
    @AfterAll
    public static void afterAll(){}

}
