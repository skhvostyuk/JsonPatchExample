import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.flipkart.zjsonpatch.JsonPatch;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Scanner sc = new Scanner(new FileInputStream("source.json"));
        String jsonString = sc.nextLine();
        while(sc.hasNextLine())
            jsonString += sc.nextLine();

        JsonNode source = mapper.readTree(jsonString);

        sc = new Scanner(new FileInputStream("target.json"));
        jsonString = sc.nextLine();
        while(sc.hasNextLine())
            jsonString += sc.nextLine();
        JsonNode target = mapper.readTree(jsonString);

        PrintStream out = new PrintStream("patch.json");
        // Получение патча
        JsonNode patch = JsonDiff.asJson(source, target);
        out.print(patch);

        // Применение патча
        JsonNode sourseWithPatch = JsonPatch.apply(patch, source);
         out = new PrintStream("sourseWithPatch.json");
        out.print(sourseWithPatch);
    }
}
