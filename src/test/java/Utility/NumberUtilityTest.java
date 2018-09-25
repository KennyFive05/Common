package Utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberUtilityTest {
    @Test
    public void format() {
        String str;
        // 數字類
        String input = "00123456789.0852000";
        str = NumberUtility.format("9", input, "9999.Z");
        assertEquals("00123456789.0852", str);
        str = NumberUtility.format("9", input, "ZZZ9");
        assertEquals("123456789", str);
        str = NumberUtility.format("9(5,2)", input, "ZZZ9.9");
        assertEquals("123456789.08", str);
        // 日期
        str = NumberUtility.format("D(yyyyMMdd HHmmss)", "20180413 174356", "yyyy/MM/dd HH:mm:ss");
        assertEquals("2018/04/13 17:43:56", str);
        str = NumberUtility.format("D", "20180413", "yyyy-MM-dd");
        assertEquals("2018-04-13", str);
        str = NumberUtility.format("D", "174356", "HH:mm:ss");
        assertEquals("17:43:56", str);
    }

    @Test
    public void null2Double() {
        Double num = NumberUtility.null2Double(null);
        assertEquals(0.0, num, 0.0);
    }
}
