package com.sungjun.springrecipesbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@EnableBatchProcessing
@SpringBootApplication
public class SpringrecipesBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringrecipesBatchApplication.class, args);

        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        System.out.println(value);
    }

}

