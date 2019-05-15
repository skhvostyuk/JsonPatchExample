import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.JsonDiff;

import java.io.IOException;

public class Utils {
    private static final String REPLACE = "\"replace\"";
    private static final String REMOVE = "\"remove\"";
    private static final String OPERATION = "op";


    public static JsonNode getJsonPatch(JsonNode source, JsonNode target) {
        ArrayNode patch = (ArrayNode) JsonDiff.asJson(source, target);
        ArrayNode reversePatch = (ArrayNode) JsonDiff.asJson(target, source);
        for(int i = 0; i < patch.size(); i++) {
            ObjectNode node = (ObjectNode) patch.get(i);
            if (node.get(OPERATION).toString().equals(REPLACE) || node.get(OPERATION).toString().equals(REMOVE)) {
                for(int j = 0; j < reversePatch.size(); j++) {
                    ObjectNode reverseNode = (ObjectNode) reversePatch.get(j);
                    if (node.get("path").toString().equals(reverseNode.get("path").toString())) {
                        node.set("oldValue", reverseNode.get("value"));
                        break;
                    }
                }
            }
        }
        return  patch;
    }

    public static JsonNode getJsonPatch(String source, String target) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode sourceNode= mapper.readTree(source);
        JsonNode targetNode  = mapper.readTree(target);
        return getJsonPatch(sourceNode, targetNode);
    }
}
