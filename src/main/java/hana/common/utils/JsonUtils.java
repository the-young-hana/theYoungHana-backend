package hana.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;

@TypeInfo(name = "JsonUtils", description = "JSON 유틸리티")
public class JsonUtils {
    private static final ObjectMapper objectMapper;

    @MethodInfo(name = "convertListToJson", description = "리스트를 JSON으로 변환합니다.")
    public String convertListToJson(List<String> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }

    @MethodInfo(name = "convertJsonToList", description = "JSON을 리스트로 변환합니다.")
    public List convertJsonToList(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, List.class);
    }

    static {
        objectMapper = new ObjectMapper();
    }
}
