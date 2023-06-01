package ru.netology;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.netology.utils.JsonUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

// https://github.com/netology-code/jd-homeworks/blob/master/special_files/task1/README.md
public class SCVJsonParse {

    private static final String PATH_TO_FILES = "csv_json_parser/src/main/resources/";

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = PATH_TO_FILES + "data.csv";
        List<Employee> employees = parseCSV(columnMapping, fileName);
        String json = JsonUtils.listToJson(employees);
        JsonUtils.writeString(PATH_TO_FILES + "data.json", json);
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            return csv.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}