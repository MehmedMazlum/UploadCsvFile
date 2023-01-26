package com.restapi.parsecsv.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.restapi.parsecsv.model.Record;


@Repository
public interface RecordRepository extends CrudRepository<Record,Integer> {

}
