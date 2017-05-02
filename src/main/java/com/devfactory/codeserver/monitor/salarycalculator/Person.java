package com.devfactory.codeserver.monitor.salarycalculator;

class Person {
    private final String name;
    private final int ratePerHour;
    private int hoursWorked;

    public Person(String name, int ratePerHour) {
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    public String getName() {
        return name;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}