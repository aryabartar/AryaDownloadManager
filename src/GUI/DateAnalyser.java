/**
 * This class is used to get minute and hour of a given date .
 */

package GUI;

import java.util.Date;

public class DateAnalyser {

    /**
     * @param date as input date
     * @return as minutes of given date
     */
    public static int getMinute(Date date) {
        int intMinute;
        String minute = "";

        minute += date.toString().charAt(14);
        minute += date.toString().charAt(15);

        intMinute = Integer.parseInt(minute);

        return intMinute;
    }

    /**
     * @param date as input date
     * @return as hour of given date
     */
    public static int getHour(Date date) {
        int intHour;
        String hour = "";

        hour += date.toString().charAt(11);
        hour += date.toString().charAt(12);

        intHour = Integer.parseInt(hour);

        return intHour;
    }
}
