package com.customer.statementfileprocessor.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Data
@Table(name = "Customer_Record")
public class RecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Reference",length = 6)
    private Long reference;

    @Column(name = "AccountNumber",length = 18)
    private String accountNumber;

    @Column(name = "StartBalance")
    private BigDecimal startBalance;

    @Column(name = "Mutation")
    private BigDecimal mutation;

    @Column(name = "Description")
    private String description;

    @Column(name = "EndBalance")
    private BigDecimal endBalance;

    public RecordEntity() {
    }

    public RecordEntity(Long reference, String accountNumber, BigDecimal startBalance, BigDecimal mutation, String description, BigDecimal endBalance) {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }
}
