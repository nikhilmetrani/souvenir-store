package sg.edu.nus.iss.se23pt2.pos.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat FORMAT_YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    public static String getCurrentDateAsString(){
        Date date = new Date(System.currentTimeMillis());
        return FORMAT_YYYYMMDD.format(date);
    }
}
