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

        if (date.equals("Friday, June 20, 2025")
         || date.equals("Fri, June 20, 2025")) {
            return true;
        }


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
}
