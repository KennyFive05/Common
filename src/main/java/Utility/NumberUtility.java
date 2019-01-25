package Utility;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NumberUtility {
    /**
     * null -> 0
     *
     * @param num
     * @return
     */
    public static Integer null2Integer(Integer num) {
        if (num == null) {
            num = 0;
        }
        return num;
    }


    /**
     * null -> 0.0
     *
     * @param num
     * @return
     */
    public static Double null2Double(Double num) {
        if (num == null) {
            num = 0.0;
        }
        return num;
    }

    /**
     * format
     *
     * @param fieldType
     * @param inputText
     * @param format
     * @return
     */
    public static String format(String fieldType, String inputText, String format) {
        String result = inputText;

        try {
            switch (fieldType.substring(0, 1)) {
                case "9":
                    result = formatMath(fieldType, inputText, format);
                    break;
                case "D":
                    result = formatDate(fieldType, inputText, format);
                    break;
            }
        } catch (Exception e) {
            System.err.println("NumberUtility.format error: {fieldType:" + fieldType + ", inputText:" + inputText
                    + ", format:" + format + "}");
        }

        return result;
    }

    /**
     * 數字類
     *
     * @param fieldType
     * @param inputText
     * @param format
     * @return
     */
    private static String formatMath(String fieldType, String inputText, String format) {
        String result = inputText;
        int tInt = 0;
        int tFloat = 0;
        String tFormat2;
        String tFormat3 = "";
        String temp = "0";
        if (fieldType.indexOf("(") > 0 && fieldType.indexOf(")") > 0) {
            temp = fieldType.substring(fieldType.indexOf("(") + 1, fieldType.indexOf(")"));
        }
        if (format.contains(".")) {
            // 浮點數
            if (!"0".equals(temp)) {
                tInt = Integer.parseInt(temp.split(",")[0]);
                tFloat = Integer.parseInt(temp.split(",")[1]);
            } else if (inputText.contains(".")) {
                tInt = inputText.split("\\.")[0].length();
                tFloat = inputText.split("\\.")[1].length();
            }
            tFormat2 = format.split("\\.")[0];
            tFormat3 = format.split("\\.")[1];
        } else {
            // 整數
            if (!"0".equals(temp)) {
                tInt = Integer.parseInt(temp);
            } else {
                tInt = inputText.length();
            }
            tFormat2 = format;
        }

        // 處理整數部份
        String format2 = "0";
        switch (tFormat2) {
            case "ZZZ9":
                // format2 = "0";
                break;
            case "Z,ZZ9":
                format2 = "#,##0";
                break;
            case "9,999":
                format2 = StringUtils.leftPad("0,000", tInt + 1, "0");
                break;
            case "9999":
                format2 = StringUtils.leftPad("0", tInt, "0");
                break;
            default:
                return result;
        }

        // 處理小數位部份
        if (StringUtils.isNotEmpty(tFormat3)) {
            switch (tFormat3) {
                case "9":
                    format2 += StringUtils.rightPad(".0", tFloat + 1, "0");
                    break;
                case "Z":
                    format2 += StringUtils.rightPad(".#", tFloat + 1, "#");
                    break;
                default:
                    return result;
            }
        }
        DecimalFormat df = new DecimalFormat(format2);
        df.setRoundingMode(RoundingMode.DOWN); // 不要四捨五入
        result = df.format(new BigDecimal(inputText));
        return result;
    }

    /**
     * 日期類
     *
     * @param fieldType
     * @param inputText
     * @param format
     * @return
     * @throws ParseException
     */
    private static String formatDate(String fieldType, String inputText, String format) {
        String result = inputText;
        String inputType = "";
        if (fieldType.indexOf("(") > 0 && fieldType.indexOf(")") > 0) {
            inputType = fieldType.substring(fieldType.indexOf("(") + 1, fieldType.indexOf(")"));
        }
        if (StringUtils.isEmpty(inputType)) {
            switch (inputText.length()) {
                case 8:
                    inputType = "yyyyMMdd";
                    break;
                case 6:
                    inputType = "HHmmss";
                    break;
            }
        }
        try {
            SimpleDateFormat inSdf = new SimpleDateFormat(inputType);
            Date date = inSdf.parse(inputText);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat outSdf = new SimpleDateFormat(format);
            result = outSdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String hello(){
        return "Hello";
    }
}
