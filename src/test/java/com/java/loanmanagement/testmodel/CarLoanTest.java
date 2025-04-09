package com.java.loanmanagement.testmodel;

import com.java.loanmanagement.model.CarLoan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CarLoanTest {

    @Test
    public void testCarLoanConstructorAndGetters() {
        CarLoan carLoan = new CarLoan("Tata Nexon EV", 1600000.0);

        assertEquals("Tata Nexon EV", carLoan.getCarModel());
        assertEquals(1600000.0, carLoan.getCarValue(), 0.0001);
    }

    @Test
    public void testCarLoanSetters() {
        CarLoan carLoan = new CarLoan();

        carLoan.setCarModel("Mahindra XUV700");
        carLoan.setCarValue(2100000.0);

        assertEquals("Mahindra XUV700", carLoan.getCarModel());
        assertEquals(2100000.0, carLoan.getCarValue(), 0.0001);
    }

    @Test
    public void testToStringContainsFields() {
        CarLoan carLoan = new CarLoan("Hyundai Verna", 1800000.0);

        String output = carLoan.toString();
        assertTrue(output.contains("Hyundai Verna"));
        assertTrue(output.contains("carValue=1800000.0"));
    }
}
