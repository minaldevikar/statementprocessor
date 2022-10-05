package com.customer.statementfileprocessor.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum FileType {

    XML("application/xml"),
    CSV("text/csv"),
    INVALID("invalid");

    private static final Map<String, FileType> fileTypeMap;

    static {
        fileTypeMap = Collections.unmodifiableMap(Arrays.stream(FileType.values())
                .collect(Collectors.toMap(item -> item.mimeType, item -> item)));
    }

    private String mimeType;

    FileType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static FileType get(String mimeType) {
        return fileTypeMap.get(mimeType) != null ? fileTypeMap.get(mimeType) : INVALID;
    }

    public String getMimeType() {
        return this.mimeType;
    }
}
