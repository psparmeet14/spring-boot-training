package com.parmeet.springboottraining.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class JsonUtils {

    public static JsonObject createJson(Map<String, String> jsonMap) {
        return jsonMap.entrySet()
                .stream()
                .reduce(
                        new JsonObject(),
                        (a, b) -> {
                            a.add(b.getKey(), new JsonPrimitive(b.getValue()));
                            return a;
                        },
                        (a, b) -> a
                );
    }
}
