package com.restapi.parsecsv.controller;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.restapi.parsecsv.exception.CsvDeleteFileException;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;


@RestController
@Slf4j
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    RecordRepository recordRepository;

    @PostMapping("/upload-csv-file")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) throws CsvUploadFileException {
        if (file.isEmpty()) {
            logger.info("Your file is empty");
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
                logger.info("exception occurs");
                throw new CsvUploadFileException("Error occurred while uploading the file",ex);
            }
        }
        return new ResponseEntity<>(
                "Upload csv file is completed ",
                HttpStatus.OK);
    }


    @GetMapping("/get-csv-file/{primarykey}")
    public ResponseEntity<?> getCSVFileByPrimarKey(@PathVariable("primarykey") Integer primarykey) throws CsvGetFileException{
        Optional<Record> record;
        try {
             record = recordRepository.findById(primarykey);
             if (record.isEmpty())
                 throw new CsvGetFileException("Error occurred while getting the file");
        } catch (Exception ex) {
            logger.info("exception occurs");
            return new ResponseEntity<>("Error occurred while getting the file", HttpStatus.NOT_FOUND);
            //throw new CsvGetFileException("Error occurred while getting the file",ex);
        }
        return ResponseEntity.accepted().body(record.orElseThrow());
    }


    @DeleteMapping("/delete-csv-file")
    public ResponseEntity<String> deleteCSVFileByPrimarKey(@RequestParam("primarykey") String primarykey)  throws CsvDeleteFileException{
        try {
            recordRepository.deleteById(Integer.valueOf(primarykey));
        } catch (Exception ex) {
            logger.info("exception occurs when csv file deleting");
            return new ResponseEntity<>("Error occurred while deleting the file", HttpStatus.BAD_REQUEST);
           // throw new CsvDeleteFileException("Error occurred while deleting file",ex);
        }
        return new ResponseEntity<>(
                "Deleted csv file is completed ",
                HttpStatus.OK);
    }
}
