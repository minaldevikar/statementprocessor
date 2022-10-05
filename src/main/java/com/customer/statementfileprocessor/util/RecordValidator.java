package com.customer.statementfileprocessor.util;

import com.customer.statementfileprocessor.bean.FailedRecordBean;
import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecordValidator {

    private RecordValidator() {
        throw new IllegalStateException("Utility class, not meant to be instantiated");
    }

    public static List<FailedRecordBean> validate(StatementFileInput input) {
        return input.getInput()
                .parallelStream()
                .filter(record -> isReferenceNotUnique(input, record) || isEndBalanceNotCorrect(record))
                .map(RecordValidator::createValidationResult)
                .collect(Collectors.toList());
    }

    private static boolean isReferenceNotUnique(StatementFileInput input, Record record) {
        return Collections.frequency(input.getInput(), record) > 1;
    }

    private static boolean isEndBalanceNotCorrect(Record record) {
        return !record.getStartBalance().add(record.getMutation()).equals(record.getEndBalance());
    }

    private static FailedRecordBean createValidationResult(Record record) {
        FailedRecordBean failedRecordBean = new FailedRecordBean();
        failedRecordBean.setReference(record.getReference());
        failedRecordBean.setDescription(record.getDescription());
        return failedRecordBean;
    }
}
