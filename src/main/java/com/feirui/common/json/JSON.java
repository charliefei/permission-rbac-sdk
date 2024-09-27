package com.feirui.common.json;

import cn.hutool.json.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public abstract class JSON {
    public static final String DEFAULT_DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 蛇形命名序列化/反序列化
     */
    public static final ObjectMapper OBJECT_MAPPER_SNAKE = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JSON.class);

    static {
        // 允许字段不用一一匹配
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 开启大小写不敏感
        OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        // 允许key不带双引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许key单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许字符串中存在回车换行控制符
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 忽略null
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 统一格式化日期
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
        // 解决时差8小时问题
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // 蛇形命名策略
        OBJECT_MAPPER_SNAKE.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // 允许字段不用一一匹配
        OBJECT_MAPPER_SNAKE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 开启大小写不敏感
        OBJECT_MAPPER_SNAKE.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        // 允许key不带双引号
        OBJECT_MAPPER_SNAKE.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许key单引号
        OBJECT_MAPPER_SNAKE.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许字符串中存在回车换行控制符
        OBJECT_MAPPER_SNAKE.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static Object parse(String text) {
        return parseObject(text, JSONObject.class);
    }

    public static JSONObject parseObject(String text) {
        return parseObject(text, JSONObject.class);
    }

    public static JSONObject toJSONObject(Object object) {
        if (object == null || object == "") {
            return null;
        }
        if (object instanceof String) {
            return JSONObject.parseObject(object.toString());
        }
        String json = JSONObject.toJSONString(object);
        return JSONObject.parseObject(json);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                return OBJECT_MAPPER.readValue(text, clazz);
            } catch (Exception e) {
                LOGGER.error(" >>>>>>>>>>>>>>> parseObject error, source str: " + text, e);
            }
        }
        return null;
    }

    /**
     * 使用蛇形命名策略反序列化
     */
    public static <T> T parseObjectSnake(String text, Class<T> clazz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                return OBJECT_MAPPER_SNAKE.readValue(text, clazz);
            } catch (Exception e) {
                LOGGER.error(" >>>>>>>>>>>>>>> parseObjectSnake error, source str: " + text, e);
            }
        }
        return null;
    }

    public static <T> T parseObjectAutoString(String text, Class<T> clazz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                Map<String, Object> objMap = OBJECT_MAPPER.readValue(text, Map.class);
                for (Map.Entry<String, Object> entry : objMap.entrySet()) {
                    if (entry.getValue() instanceof Map) {
                        objMap.put(entry.getKey(), OBJECT_MAPPER.writeValueAsString(entry.getValue()));
                    }
                }
                return OBJECT_MAPPER.convertValue(objMap, clazz);
            } catch (Exception e) {
                LOGGER.error(" >>>>>>>>>>>>>>> parseObjectAutoString error, source str: " + text, e);
            }
        }
        return null;
    }

    public static JSONArray parseArray(String text) {
        return new JSONArray(parseArray(text, Object.class));
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz));
            } catch (Exception ex) {
                LOGGER.error(" >>>>>>>>>>>>>>> parseArray error, source value: " + text, ex);
            }
        }
        return new ArrayList<>();
    }

    public static <T> List<T> parseArrayAutoString(String text, Class<T> clazz) {
        if (StringUtils.isNotBlank(text)) {
            try {
                List<Map<String, Object>> srcMapList = OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, Map.class));
                for (Map<String, Object> srcMap : srcMapList) {
                    for (Map.Entry<String, Object> entry : srcMap.entrySet()) {
                        if (entry.getValue() instanceof Map) {
                            srcMap.put(entry.getKey(), OBJECT_MAPPER.writeValueAsString(entry.getValue()));
                        }
                    }
                }
                return OBJECT_MAPPER.convertValue(srcMapList, OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz));
            } catch (Exception ex) {
                LOGGER.error(" >>>>>>>>>>>>>>> parseArrayAutoString error, source value: " + text, ex);
            }
        }
        return new ArrayList<T>();
    }

    public static String toJSONString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception ex) {
            LOGGER.error(" >>>>>>>>>>>>>>> toJSONString error ", ex);
        }
        return null;
    }

    public static <T> T convertObject(Object value, Class<T> clazz) {
        try {
            if (value instanceof String) {
                return parseObject((String) value, clazz);
            }
            return OBJECT_MAPPER.convertValue(value, clazz);
        } catch (Exception ex) {
            LOGGER.error(" >>>>>>>>>>>>>>> convertObject error, source value: " + value.toString(), ex);
        }
        return null;
    }

    public static <T> T convertObject(Object value, Class<T> parametrized, Class... parameterClasses) {
        try {
            if (value instanceof String) {
                return OBJECT_MAPPER.readValue((String) value, OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses));
            }
            return OBJECT_MAPPER.convertValue(value, OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses));
        } catch (Exception ex) {
            LOGGER.error(" >>>>>>>>>>>>>>> convertObject error, source value: " + value.toString(), ex);
        }
        return null;
    }

    public static <T> T toJavaObject(JSON json, Class<T> clazz) {
        return convertObject(json, clazz);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
