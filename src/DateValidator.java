import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateValidator {

    private boolean isDateIllegal(int month, int day, int year) {
        return 
            // obviously illegal
            day >= 32 || month >= 13 ||
            // not obviously illegal
            (month == 2  && day == 31) ||
            (month == 4  && day == 31) ||
            (month == 6  && day == 31) ||
            (month == 9  && day == 31) ||
            (month == 11 && day == 31) ||
            (month == 2  && day == 30) ||
            // illegal except on leap years
            (month == 2  && day == 29 && (year % 4 != 0));
    }

    public boolean validate(String date) {
        // first, all your existing simple‐format checks
        if (validateSimpleFormats(date)) {
            return true;
        }
        // then fallback to weekday+word-month parsing
        return validateWeekdayFormat(date);
    }

    public boolean validateSimpleFormats(String date) {
        String mm, dd, yyyy;

        if (date.length() == 8) {
            mm   = date.substring(0,2);
            dd   = date.substring(2,4);
            yyyy = date.substring(4,8);

        } else if (date.length() == 10) {
            // mismatched separator check
            String sep1 = date.substring(2,3);
            String sep2 = date.substring(5,6);
            if (!sep1.equals(sep2)) return false;

            mm   = date.substring(0,2);
            dd   = date.substring(3,5);
            yyyy = date.substring(6,10);

        } else {
            return false;
        }

        try {
            int month = Integer.parseInt(mm);
            int day   = Integer.parseInt(dd);
            int year  = Integer.parseInt(yyyy);

            // if illegal, return false;
            // otherwise true
            return !isDateIllegal(month, day, year);

        } catch (NumberFormatException e) {
            // non-numeric substrings are invalid date
            return false;
        }
    }
    
    // For AS Test Case 3: manual weekday parsing + Sakamoto’s algorithm

    private boolean validateWeekdayFormat(String input) {
        // 1) split "Friday, June 20, 2025" → ["Friday", " June 20, 2025"]
        int comma = input.indexOf(',');
        if (comma < 0) return false;

        String weekdayToken = input.substring(0, comma).trim();      // "Friday" or "Fri"
        String rest         = input.substring(comma + 1).trim();    // "June 20, 2025"

        // 2) break "June 20, 2025" into [monthName, day, year]
        String[] parts = rest.split("[ ,]+");
        if (parts.length != 3) return false;
        String monthName = parts[0];
        int day, year;
        try {
            day  = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        // 3) map month name → month number (1–12)
        int month = monthFromName(monthName);
        if (month < 1) return false;

        // 4) compute weekday index (0=Sunday,…,6=Saturday)
        int w = dayOfWeek(year, month, day);

        // 5) full & short names for comparison
        String[] fullNames  = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        String[] shortNames = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};

        // 6) match token (case-insensitive)
        return weekdayToken.equalsIgnoreCase(fullNames[w])
            || weekdayToken.equalsIgnoreCase(shortNames[w]);
    }

    // map full or 3-letter month to 1–12
    private int monthFromName(String name) {
        switch (name.toLowerCase()) {
            case "january": case "jan":    return 1;
            case "february":case "feb":    return 2;
            case "march":   case "mar":    return 3;
            case "april":   case "apr":    return 4;
            case "may":                   return 5;
            case "june":    case "jun":    return 6;
            case "july":    case "jul":    return 7;
            case "august":  case "aug":    return 8;
            case "september":case "sep":   return 9;
            case "october": case "oct":    return 10;
            case "november":case "nov":    return 11;
            case "december":case "dec":    return 12;
            default: return -1;
        }
    }

    // Sakamoto’s algorithm: returns 0=Sunday,…,6=Saturday
    private int dayOfWeek(int y, int m, int d) {
        int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if (m < 3) y -= 1;
        return (y + y/4 - y/100 + y/400 + t[m-1] + d) % 7;
    }
}
