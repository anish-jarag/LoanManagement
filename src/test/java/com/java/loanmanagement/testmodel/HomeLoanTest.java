package com.java.loanmanagement.testmodel;

import com.java.loanmanagement.model.HomeLoan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HomeLoanTest {

    @Test
    public void testHomeLoanConstructorAndGetters() {
        HomeLoan homeLoan = new HomeLoan("Plot 43, Kothrud, Pune", 8500000.0);

        assertEquals("Plot 43, Kothrud, Pune", homeLoan.getPropertyAddress());
        assertEquals(8500000.0, homeLoan.getPropertyValue(), 0.0001);
    }

    @Test
    public void testHomeLoanSetters() {
        HomeLoan homeLoan = new HomeLoan();

        homeLoan.setPropertyAddress("Flat 102, Blue Tower, Pune");
        homeLoan.setPropertyValue(7800000.0);

        assertEquals("Flat 102, Blue Tower, Pune", homeLoan.getPropertyAddress());
        assertEquals(7800000.0, homeLoan.getPropertyValue(), 0.0001);
    }

    @Test
    public void testToStringContainsFields() {
        HomeLoan homeLoan = new HomeLoan("Sector 21, Navi Mumbai", 9200000.0);

        String output = homeLoan.toString();
        assertTrue(output.contains("Sector 21, Navi Mumbai"));
        assertTrue(output.contains("propertyValue=9200000.0"));
    }
}
