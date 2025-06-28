public class DateValidator {
    public boolean validate(String date){
        if (date.length() == 8){
            String mm = date.substring(0,2);
            String dd = date.substring(2,4);
            String yyyy = date.substring(4,8);

            int month = Integer.parseInt(mm);
            int day = Integer.parseInt(dd);
            int year = Integer.parseInt(yyyy);

            if ( day >= 32 || month >= 13 ||
                (month == 2  && day == 31) ||
                (month == 4  && day == 31) ||
                (month == 6  && day == 31) ||
                (month == 9  && day == 31) ||
                (month == 11 && day == 31) ||
                (month == 2  && day == 30) ||
                (month == 2  && day == 29 && (year % 4 != 0)) ) {
                    return false;
                }
        } else {
            return false;
        }
        return true;
    }
}