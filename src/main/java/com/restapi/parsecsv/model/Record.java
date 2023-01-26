package com.restapi.parsecsv.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@Entity
//@RedisHash("Record")
public class Record {

    @CsvBindByName
    private long primary_key;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private String updated_timestamp;

    public Record(long primary_key, String name, String description, String updated_timestamp) {
        this.primary_key = primary_key;
        this.name = name;
        this.description = description;
        this.updated_timestamp = updated_timestamp;
    }

    public void setPrimary_key(Long primary_key) {
        this.primary_key = primary_key;
    }

    @Id
    public Long getPrimary_key() {
        return primary_key;
    }


// getters and setters removed for the sake of brevity
}
