package com.devfactory.codeserver.monitor.salarycalculator;

class SalaryCalculator {
    public int calculate(Person person) {
        return person.getRatePerHour() * person.getHoursWorked();
    }
}