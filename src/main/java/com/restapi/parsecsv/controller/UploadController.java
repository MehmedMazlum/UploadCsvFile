package com.restapi.parsecsv.controller;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.restapi.parsecsv.exception.CsvGetFileException;
import com.restapi.parsecsv.exception.CsvUploadFileException;
import com.restapi.parsecsv.model.Record;
import com.restapi.parsecsv.repository.RecordRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;


@RestController
@Slf4j
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final String UPLOADING_ERROR = "Error occurred while uploading the file";
    private static final String UPLOADING_EXCEPTION = "Exception occurs when upload-csv-file uplading...";
    private static final String UPLOADING_EMPTY_FILE = "Your file is empty";
    private static final String UPLOADING_COMPLETED = "Upload csv file is completed";
    private static final String GET_FILE_EXCEPTION = "Error occurred while getting the file";
    private static final String DELETED_FILE_EXCEPTION = "Exception occurs when csv file deleting";
    private static final String DELETED_COMPLETED = "Deleted csv file is completed ";


    @Resource
    RecordRepository recordRepository;

    @PostMapping("/upload-csv-file")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) throws CsvUploadFileException {
        if (file.isEmpty()) {
            logger.info(UPLOADING_EMPTY_FILE);
        } else {
            // parse CSV file to create a list of `Record` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                // create csv bean reader
                CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Record.class)
                        .withSkipLines(0)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of records
                var items = csvToBean.parse();
                // TODO: save users in DB
                recordRepository.saveAll(unmodifiableList(items));
            } catch (Exception ex) {
                logger.info(UPLOADING_EXCEPTION);
                throw new CsvUploadFileException(UPLOADING_ERROR, ex);
            }
        }
        return new ResponseEntity<>(
                UPLOADING_COMPLETED,
                HttpStatus.OK);
    }

    @Cacheable(key="#primarykey", value="record")
    @GetMapping("/get-csv-file/{primarykey}")
    public ResponseEntity<String> getCSVFileByPrimarKey(@PathVariable("primarykey") Integer primarykey) {
        Optional<Record> record;
        try {
            record = recordRepository.findById(primarykey);
            if (record.isEmpty())
                throw new CsvGetFileException(GET_FILE_EXCEPTION);
        } catch (Exception ex) {
            logger.info(ex.getLocalizedMessage());
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.accepted().body(record.toString());
    }

    @Cacheable(key="#primarykey", value="record")
    @DeleteMapping("/delete-csv-file/{primarykey}")
    public ResponseEntity<String> deleteCSVFileByPrimarKey(@PathVariable("primarykey") String primarykey) {
        try {
            recordRepository.deleteById(Integer.valueOf(primarykey));
        } catch (Exception ex) {
            logger.info(DELETED_FILE_EXCEPTION);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                DELETED_COMPLETED,
                HttpStatus.OK);
    }
}
