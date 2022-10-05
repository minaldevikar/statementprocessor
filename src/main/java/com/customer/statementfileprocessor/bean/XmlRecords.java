package com.customer.statementfileprocessor.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "records")
public class XmlRecords {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "record")
    private List<XmlRecord> xmlRecordList;

}
