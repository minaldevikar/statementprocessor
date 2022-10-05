package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.util.FileType;

public class FileProcessorFactory {

    private FileProcessorFactory() {
        throw new IllegalStateException("Factory class, not meant to be instantiated");
    }

    public static FileProcessor getFileProcessor(FileType fileType) {
        switch (fileType) {
            case CSV:
                return new CsvProcessor();
            /*case XML:
                return new XmlProcessor();*/
            default:
                return null;
        }
    }
}
