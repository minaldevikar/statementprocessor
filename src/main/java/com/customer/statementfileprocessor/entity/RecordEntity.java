package com.customer.statementfileprocessor.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Customer_Record")
public class RecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Reference", length = 6)
    private Long reference;

    @Column(name = "AccountNumber", length = 18)
    private String accountNumber;

    @Column(name = "StartBalance")
    private BigDecimal startBalance;

    @Column(name = "Mutation")
    private BigDecimal mutation;

    @Column(name = "Description")
    private String description;

    @Column(name = "EndBalance")
    private BigDecimal endBalance;
}
