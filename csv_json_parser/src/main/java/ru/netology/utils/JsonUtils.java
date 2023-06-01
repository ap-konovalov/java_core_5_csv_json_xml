package ru.netology.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.netology.Employee;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static String listToJson(List<Employee> employees) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(employees, listType);
    }

    public static void writeString(String path, String jsonData) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String path) {
        StringBuilder sb = new StringBuilder();
        try (Reader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    public static List<Employee> jsonToList(String json) {
        JSONParser parser = new JSONParser();
        List<Employee> employees = new ArrayList<>();
        try {
            Object obj = parser.parse(json);
            JSONArray jsonArray = (JSONArray) obj;
            jsonArray.forEach(element -> {
                try {
                    Object employee = parser.parse(element.toString());
                    JSONObject employeeJsonObj = (JSONObject) employee;
                    int id = Integer.parseInt(employeeJsonObj.get("id").toString());
                    String firstName = employeeJsonObj.get("firstName").toString();
                    String lastName = employeeJsonObj.get("lastName").toString();
                    String country = employeeJsonObj.get("country").toString();
                    int age = Integer.parseInt(employeeJsonObj.get("age").toString());
                    employees.add(new Employee(id, firstName, lastName, country, age));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
