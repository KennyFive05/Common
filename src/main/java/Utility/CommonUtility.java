package Utility;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class CommonUtility {
    public static Object null2Empty(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (String.class.equals(field.getType())) {
                    if (field.get(obj) == null) {
                        field.set(obj, StringUtils.EMPTY);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
}
