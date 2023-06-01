package ru.netology;


import ru.netology.utils.JsonUtils;

import java.util.List;

//https://github.com/netology-code/jd-homeworks/blob/master/special_files/task3/README.md
public class JsonParser {
    private static final String PATH_TO_FILES = "json_parser/src/main/resources/";

    public static void main(String[] args) {
        String json = JsonUtils.readString(PATH_TO_FILES + "data.json");
        List<Employee> list = JsonUtils.jsonToList(json);
        list.forEach(employee -> System.out.println(employee));
    }
}