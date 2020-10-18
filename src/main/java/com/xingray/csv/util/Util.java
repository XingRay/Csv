package com.xingray.csv.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class Util {
    public static Object get(Object o, String fieldName, Map<String, Method> getterCache) {
        Method getter = null;
        if (getterCache != null && !getterCache.isEmpty()) {
            getter = getterCache.get(fieldName);
        }
        if (getter == null) {
            getter = getGetter(o.getClass(), fieldName);
            if (getter != null && getterCache != null) {
                getterCache.put(fieldName, getter);
            }
        }
        if (getter == null) {
            return null;
        }
        try {
            return getter.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据属性，获取get方法
     *
     * @param fieldName 属性名
     */
    private static Method getGetter(Class<?> cls, String fieldName) {
        Field field = null;
        try {
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("field: " + fieldName + " not found in class: " + cls.getCanonicalName());
        }
        if (field == null) {
            return null;
        }

        Class<?> type = field.getType();
        String methodName;
        if (type == Boolean.class || type == boolean.class) {
            if (fieldName.startsWith("is")) {
                methodName = fieldName;
            } else {
                methodName = "is" + captain(fieldName);
            }
        } else {
            methodName = "get" + captain(fieldName);
        }

        try {
            return cls.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            System.out.println("method: " + methodName + " not found in class: " + cls.getCanonicalName());
        }
        return null;
    }

    private static String captain(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String secondsToFormattedString(long seconds, String format) {
        return millsToFormattedString(seconds * 1000, format);
    }

    public static String millsToFormattedString(long mills, String format) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(mills);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(calendar.getTime());
    }

    public static String toFormattedString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
