package com.customer.statementfileprocessor.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XmlRecord {

    @JacksonXmlProperty(isAttribute = true)
    private String reference;

    @JacksonXmlProperty
    private String accountNumber;

    @JacksonXmlProperty
    private String description;

    @JacksonXmlProperty
    private String startBalance;

    @JacksonXmlProperty
    private String mutation;

    @JacksonXmlProperty
    private String endBalance;
}
