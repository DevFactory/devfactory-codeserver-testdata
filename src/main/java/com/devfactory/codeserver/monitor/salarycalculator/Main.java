package com.devfactory.codeserver.monitor.salarycalculator;

public class Main {
    public static void main(String[] args) {
        Person kate = new Person("Kate", 100);
        kate.setHoursWorked(40);
        System.out.println(String.format(
                "%s earned %d$ per %d hours worked",
                kate.getName(),
                new SalaryCalculator().calculate(kate),
                kate.getHoursWorked()));
    }//dummy comment
}