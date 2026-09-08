package com.Bank.BankBackend;

import org.springframework.boot.SpringApplication;

public class TestBankBackendApplication {
    public static void main(String[] args){
        SpringApplication.from(BankBackendApplication::main).run(args);
    }
}
