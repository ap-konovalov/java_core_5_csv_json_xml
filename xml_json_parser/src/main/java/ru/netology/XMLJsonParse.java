package ru.netology;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.netology.utils.JsonUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// https://github.com/netology-code/jd-homeworks/blob/master/special_files/task2/README.md
public class XMLJsonParse {
    private static final String PATH_TO_FILES = "xml_json_parser/src/main/resources/";

    public static void main(String[] args) {
        List<Employee> employees = parseXML(PATH_TO_FILES + "data.xml");
        String json = JsonUtils.listToJson(employees);
        JsonUtils.writeString(PATH_TO_FILES + "data.json", json);
    }

    private static List<Employee> parseXML(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Employee> result = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node item = nodeList.item(i);
                if (isEmployeeNode(item)) {
                    NodeList employeeChildNodes = item.getChildNodes();
                    result.add(getEmployee(employeeChildNodes));
                }
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Employee getEmployee(NodeList employeeChildNodes) {
        long id = Long.parseLong(employeeChildNodes.item(1).getTextContent());
        String firstName = employeeChildNodes.item(3).getTextContent();
        String lastName = employeeChildNodes.item(5).getTextContent();
        String country = employeeChildNodes.item(7).getTextContent();
        int age = Integer.parseInt(employeeChildNodes.item(9).getTextContent());
        return new Employee(id, firstName, lastName, country, age);
    }

    private static boolean isEmployeeNode(Node node) {
        return Node.ELEMENT_NODE == node.getNodeType() && node.getNodeName().equals("employee");
    }
}