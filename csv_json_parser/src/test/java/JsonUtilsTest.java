import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.Employee;
import ru.netology.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class JsonUtilsTest {

    private static List<Employee> employees;
    private static final String PATH_TO_EXPECTED_FILE = "src/test/resources/expectedData.json";

    @BeforeAll
    public static void init() {
        employees = List.of(new Employee(1, "Ivanov", "Ivan", "Rus", 25));
    }

    @Test
    public void successConvertEmployeesListToJson() {
        //when
        String actualEmployeesJson = JsonUtils.listToJson(employees);

        //then
        assertEquals(getExpectedEmployeesJson(), actualEmployeesJson);
    }

    @Test
    public void convertEmptyEmployeesListToJson() {
        //when
        String actualEmployeesJson = JsonUtils.listToJson(List.of());

        //then
        assertEquals("[]", actualEmployeesJson);
    }

    @Test
    public void successReadStringFromJson() {
        //when
        String actualJson = JsonUtils.readString(PATH_TO_EXPECTED_FILE);

        //then
        assertThat(actualJson, equalTo(getExpectedEmployeesJson().replaceAll("\n", "")));
    }

    @Test
    public void successWriteStringToJson() {
        //given
        String pathToActualFile = "src/test/resources/data.json";

        //when
        JsonUtils.writeString(pathToActualFile, JsonUtils.listToJson(employees));
        File actuelFile = new File(pathToActualFile);
        File expectedFile = new File(PATH_TO_EXPECTED_FILE);

        //then
        try {
            assertTrue(FileUtils.contentEquals(actuelFile, expectedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            actuelFile.delete();
        }
    }

    private String getExpectedEmployeesJson() {
        return "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"Ivanov\",\n" +
                "    \"lastName\": \"Ivan\",\n" +
                "    \"country\": \"Rus\",\n" +
                "    \"age\": 25\n" +
                "  }\n" +
                "]";
    }
}
