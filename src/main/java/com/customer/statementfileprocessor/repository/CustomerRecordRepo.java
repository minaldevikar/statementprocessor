package com.customer.statementfileprocessor.repository;

import com.customer.statementfileprocessor.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRecordRepo extends JpaRepository<RecordEntity, String> {

}
