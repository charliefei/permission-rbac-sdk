package com.feirui.common.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class QunjeObjectUtils extends ObjectUtil {
    public static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 判断对象所有属性都为null
     */
    public static boolean allFieldsNull(Object object) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (getValueByFieldName(field.getName(), object) != null) {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("判断对象所有属性都为空 失败 >>>>>>>> ", e);
        }
        return true;
    }

    /**
     * 给对象属性赋默认值，不支持组合对象
     * 参数对象必须包含getter/setter方法，且命名规范，否则抛出异常
     *
     * @param object 参数对象
     */
    public static void initValueToObject(Object object, String... ignoreProperties) {
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (ignoreList != null && ignoreList.contains(field.getName())) {
                    continue;
                }
                Object value = getValueByFieldName(field.getName(), object);
                if (value != null) {
                    continue;
                }
                Method method = getMethodByFieldName(field.getName(), methods, "set");
                if (method == null) {
                    continue;
                }
                switch (field.getGenericType().toString()) {
                    case "class java.lang.String":
                        method.invoke(object, "");
                        break;
                    case "class java.lang.Integer":
                        method.invoke(object, 0);
                        break;
                    case "class java.lang.Long":
                        method.invoke(object, 0L);
                        break;
                    case "class java.lang.Float":
                        method.invoke(object, 0.0F);
                        break;
                    case "class java.util.Date":
                        method.invoke(object, new Date());
                        break;
                    case "class java.math.BigDecimal":
                        method.invoke(object, new BigDecimal(0));
                        break;
                    default:
                        if (List.class.isAssignableFrom(field.getType())) {
                            method.invoke(object, new ArrayList<>());
                        }
                }
            }
        } catch (Exception e) {
            log.error("initValueToObject 失败 >>>>>>>> ", e);
        }
    }

    /**
     * 根据属性名称获取值，拼装get方法，不破坏封装性
     */
    public static Object getValueByFieldName(String fieldName, Object object) throws Exception {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
        Method method = object.getClass().getMethod(getterName);
        return method.invoke(object);
    }

    /**
     * 根据属性名称获取set方法，不破坏封装性
     */
    public static Method getMethodByFieldName(String fieldName, Method[] methods, String prefix) {
        for (Method method : methods) {
            String methodName = method.getName();
            if (!methodName.startsWith(prefix)) {
                continue;
            }
            if (methodName.substring(prefix.length()).toLowerCase(Locale.ROOT).equals(fieldName.toLowerCase(Locale.ROOT))) {
                return method;
            }
        }
        return null;
    }

    public static String replacePlaceholder(String originText, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                Object value = getValueByFieldName(fieldName, obj);
                originText = originText.replace('{' + fieldName + '}', value == null ? " " : value.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("REPLACE_PLACEHOLDER_ERROR");
        }
        return originText;
    }

    /**
     * 对象同名属性值复制
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source != null && target != null) {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        }
    }

    /**
     * 手动校验对象注解
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... group) {
        return validator.validate(object, group);
    }

    /**
     * 实体类转Map
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>(16);
        Class<?> clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("DATA_TRANSFER_ERROR");
        }
        return map;
    }

}
