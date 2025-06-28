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

    private boolean validateWeekdayFormat(String input) {
        String[] parts = input.split(",", 2);
        if (parts.length < 2) return false;

        String weekdayToken = parts[0].trim();
        String rest         = parts[1].trim();

        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("MMM d, yyyy",  Locale.ENGLISH)
        };

        LocalDate parsed = null;
        for (DateTimeFormatter fmt : formatters) {
            try {
                parsed = LocalDate.parse(rest, fmt);
                break;
            } catch (DateTimeParseException ignored) { }
        }
        if (parsed == null) return false;

        String fullName  = parsed.getDayOfWeek()
                                 .getDisplayName(TextStyle.FULL,  Locale.ENGLISH);
        String shortName = parsed.getDayOfWeek()
                                 .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        return weekdayToken.equalsIgnoreCase(fullName)
            || weekdayToken.equalsIgnoreCase(shortName);
    }
}
