package com.customer.statementfileprocessor.util;

import com.customer.statementfileprocessor.bean.FailedRecordBean;
import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.entity.RecordEntity;

import java.util.Collections;
import java.util.List;

public class RecordValidator {
    private RecordValidator() {
        throw new IllegalStateException("Utility class, not meant to be instantiated");
    }

    public static List<FailedRecordBean> validate(StatementFileInput input) {
        return input.getRecordInputList()
                .parallelStream()
                .filter(recordInput -> isReferenceNotUnique(input, recordInput) || isEndBalanceNotCorrect(recordInput))
                .map(RecordValidator::createValidationResult)
                .toList();
    }

    public static boolean isReferenceNotUnique(StatementFileInput input, Record recordInput) {
        return Collections.frequency(input.getRecordInputList(), recordInput) > 1;
    }

    public static boolean isEndBalanceNotCorrect(Record recordInput) {
        return !recordInput.getStartBalance().add(recordInput.getMutation()).equals(recordInput.getEndBalance());
    }

    private static FailedRecordBean createValidationResult(Record recordInput) {
        FailedRecordBean failedRecordBean = new FailedRecordBean();
        failedRecordBean.setReference(recordInput.getReference());
        failedRecordBean.setDescription(recordInput.getDescription());
        return failedRecordBean;
    }

    public static RecordEntity presistValidResult(Record recordInput) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setReference(recordInput.getReference());
        recordEntity.setEndBalance(recordInput.getEndBalance());
        recordEntity.setStartBalance(recordInput.getStartBalance());
        recordEntity.setDescription(recordInput.getDescription());
        recordEntity.setMutation(recordInput.getMutation());
        recordEntity.setAccountNumber(recordInput.getAccountNumber());
        return recordEntity;
    }
}
