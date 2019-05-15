import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Test {

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

        System.out.println(Utils.getJsonPatch(source, target));
    }
}
