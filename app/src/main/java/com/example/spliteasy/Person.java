package com.example.spliteasy;

public class Person {
    String name;
    double contribution, balance;

    Person(String name, double contribution, double balance) {
        this.name = name;
        this.contribution = contribution;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getContribution() {
        return contribution;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}