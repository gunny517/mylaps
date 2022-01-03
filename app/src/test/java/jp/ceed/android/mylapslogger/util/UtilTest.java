package jp.ceed.android.mylapslogger.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {

    private Util util;


    @Test
    public void toTimeFromDateTimeWithMilliSec() {
        assertEquals(1638284400000L, DateUtil.Companion.toTimeFromDateTimeWithMilliSec("2021-12-01T00:00:00.000+09:00"));
    }


    @Test
    public void toHmsFromDateTimeWithMilliSec() {
        assertEquals("08:05:09", DateUtil.Companion.toHmsFromDateTimeWithMilliSec("2021-12-01T08:05:09.923+09:00"));
    }


    @Test
    public void toYmdFormatFromDateTime() {
        assertEquals("2021/12/01", DateUtil.Companion.toYmdFormatFromDateTime("2021-12-01T08:30:50+09:00"));
    }
}