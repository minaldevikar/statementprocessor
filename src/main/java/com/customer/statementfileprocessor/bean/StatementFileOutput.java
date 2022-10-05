package com.customer.statementfileprocessor.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StatementFileOutput {

    private List<FailedRecordBean> result;
}
