package com.restapi.parsecsv;

import com.restapi.parsecsv.model.Record;
import com.restapi.parsecsv.repository.RecordRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
class ParsecsvApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RecordRepository repository;


    @Test
    void upload_test() throws Exception {

/*        TODO: path file change your right path and run test , file.csv is in the Project directiory
        String data = readFileAsString("/home/mehmet/Desktop/files/Test task/UploadCsvFile/file.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "file.csv", "text/csv", data.getBytes());
        this.mockMvc.perform(multipart("/upload-csv-file")
                .file(mockMultipartFile)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());*/

    }

    public static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    @Test
    @Before("getRecord,getRecord_2,deletedRecord,deletedRecord_2")
    void saveItemsToDatabase() throws Exception {
        List<Record> records = new ArrayList<>();
        Record record = new Record(10000000L, "mazlumd", "mazlumdaskin", "2015-04-14T11:07:36.639Z");
        records.add(record);


        Record record_2 = new Record();
        record_2.setDescription("mehmet");
        record_2.setName("mazlumdaskin");
        record_2.setUpdated_timestamp("2015-04-14T11:07:36.639Z");
        record_2.setPrimary_key(10000001L);
        records.add(record_2);
        repository.saveAll(records);

        Optional<Record> record1 = repository.findById(10000000);
        assertEquals(10000000, record1.get().getPrimary_key());

        Optional<Record> record2 = repository.findById(10000001);
        assertEquals(10000001, record2.get().getPrimary_key());
    }

    @Test
    void getRecord() throws Exception {
        Record record = new Record(10000000L, "mazlumd", "mazlumdaskin", "2015-04-14T11:07:36.639Z");
        repository.save(record);
        mockMvc.perform(MockMvcRequestBuilders.get("/get-csv-file/{primarykey}", 10000000L))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void getRecord_2() throws Exception {
        Record record_2 = new Record();
        record_2.setDescription("mehmet");
        record_2.setName("mazlumdaskin");
        record_2.setUpdated_timestamp("2015-04-14T11:07:36.639Z");
        record_2.setPrimary_key(10000001L);
        repository.save(record_2);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-csv-file/{primarykey}", 10000001))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void deletedRecord() throws Exception {
        Record record = new Record(10000000L, "mazlum", "mazlumdaskin", "2015-04-14T11:07:36.639Z");
        repository.save(record);
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete-csv-file/{primarykey}", 10000000))
                .andExpect(status().isOk());

    }

    @Test
    void deletedRecord_2() throws Exception {
        Record record_2 = new Record();
        record_2.setDescription("mehmet");
        record_2.setName("mazlumdaskin");
        record_2.setUpdated_timestamp("2015-04-14T11:07:36.639Z");
        record_2.setPrimary_key(10000001L);
        repository.save(record_2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete-csv-file/{primarykey}", 10000001))
                .andExpect(status().isOk());

    }
}
