package com.customer.statementfileprocessor.util;

import com.customer.statementfileprocessor.bean.FailedRecordBean;
import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.entity.RecordEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecordValidator {

    public static List<FailedRecordBean> validate(StatementFileInput input) {
        return input.getRecordInputList()
                .parallelStream()
                .filter(record -> isReferenceNotUnique(input, record) || isEndBalanceNotCorrect(record))
                .map(RecordValidator::createValidationResult)
                .collect(Collectors.toList());
    }

    public static boolean isReferenceNotUnique(StatementFileInput input, Record record) {
        return Collections.frequency(input.getRecordInputList(), record) > 1;
    }

    public static boolean isEndBalanceNotCorrect(Record record) {
        return !record.getStartBalance().add(record.getMutation()).equals(record.getEndBalance());
    }

    private static FailedRecordBean createValidationResult(Record record) {
        FailedRecordBean failedRecordBean = new FailedRecordBean();
        failedRecordBean.setReference(record.getReference());
        failedRecordBean.setDescription(record.getDescription());
        return failedRecordBean;
    }

    public static RecordEntity presistValidResult(Record record) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setReference(record.getReference());
        recordEntity.setEndBalance(record.getEndBalance());
        recordEntity.setStartBalance(record.getStartBalance());
        recordEntity.setDescription(record.getDescription());
        recordEntity.setMutation(record.getMutation());
        recordEntity.setAccountNumber(record.getAccountNumber());
        return recordEntity;
    }
}
