package com.java.loanmanagement.testmodel;

import com.java.loanmanagement.model.Customer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CustomerTest {

    @Test
    public void testCustomerConstructorAndGetters() {
        Customer customer = new Customer(1, "Anish Jarag", "anish@example.com", "9876543210", "Pune", 720.0);

        assertEquals(1, customer.getCustomerId());
        assertEquals("Anish Jarag", customer.getName());
        assertEquals("anish@example.com", customer.getEmailAddress());
        assertEquals("9876543210", customer.getPhoneNumber());
        assertEquals("Pune", customer.getAddress());
        assertEquals(720.0, customer.getCreditScore(), 0.0001);
    }

    @Test
    public void testCustomerSetters() {
        Customer customer = new Customer();

        customer.setCustomerId(101);
        customer.setName("Anish Jarag");
        customer.setEmailAddress("anish@example.com");
        customer.setPhoneNumber("9998887776");
        customer.setAddress("Kolhapur");
        customer.setCreditScore(710.5);

        assertEquals(101, customer.getCustomerId());
        assertEquals("Anish Jarag", customer.getName());
        assertEquals("anish@example.com", customer.getEmailAddress());
        assertEquals("9998887776", customer.getPhoneNumber());
        assertEquals("Kolhapur", customer.getAddress());
        assertEquals(710.5, customer.getCreditScore(), 0.0001);
    }

    @Test
    public void testToStringContainsFields() {
        Customer customer = new Customer(2, "Anish Jarag", "anish@example.com", "9876543210", "Pune", 720.0);

        String output = customer.toString();
        assertTrue(output.contains("Anish Jarag"));
        assertTrue(output.contains("creditScore=720.0"));
    }
}
