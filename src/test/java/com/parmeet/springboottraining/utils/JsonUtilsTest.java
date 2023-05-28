package com.parmeet.springboottraining.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilsTest {

    @Test
    void createJson() {
        var json = JsonUtils.createJson(Map.of("foo", "bar"));

        assertEquals("bar", json.get("foo").getAsString());
        assertEquals("""
                    {"foo":"bar"}
                """.trim(), json.toString());
    }
}