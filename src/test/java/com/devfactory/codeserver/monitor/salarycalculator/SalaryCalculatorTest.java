package com.devfactory.codeserver.monitor.salarycalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SalaryCalculatorTest {

    @Test
    public void calculateNormalSalary() {
        final int ratePerHour = 25;
        Person mike = new Person("Mike", ratePerHour);

        final int hoursWorked = 50;
        mike.setHoursWorked(hoursWorked);
        assertEquals(ratePerHour * hoursWorked, new SalaryCalculator().calculate(mike));
    }

    @Test
    public void calculateNoSalary() {
        final int ratePerHour = 50;
        Person dan = new Person("Dan", ratePerHour);

        dan.setHoursWorked(0);
        assertEquals(0, new SalaryCalculator().calculate(dan));
    }
}