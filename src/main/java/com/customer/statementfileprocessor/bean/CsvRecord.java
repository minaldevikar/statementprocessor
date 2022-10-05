package com.customer.statementfileprocessor.bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsvRecord {

    @CsvBindByName
    private String reference;

    @CsvBindByName(column = "Account Number")
    private String accountNumber;

    @CsvBindByName
    private String description;

    @CsvBindByName(column = "Start Balance")
    private String startBalance;

    @CsvBindByName
    private String mutation;

    @CsvBindByName(column = "End Balance")
    private String endBalance;
}
