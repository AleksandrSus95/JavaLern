package org.examples.annotationClass.example;

public class AccountManagerImpl implements AccountManager{
    @Override
    @BankingAnnotation(securityLevel = SecurityLevelType.HIGH)
    public double depositInCash(int accountId, int amount) {
        System.out.println("deposit in total: " + amount);
        return 0;
    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelType.HIGH)
    public boolean withdraw(int accountId, int amount) {
        System.out.println("amount withdrawn: " + amount);
        return true;
    }

    @Override
    public boolean convert(double amount) {
        System.out.println("amount converted: " + amount);
        return true;
    }

    @Override
    @BankingAnnotation //default value MEDIUM
    public boolean transfer(int accountId, double amount) {
        System.out.println("amount transferred: " + amount);
        return false;
    }
}
