public class DateValidator {

    private boolean checkIllegalDays(int month, int day, int year){
        return 
        // obviously illegal
        day >= 32 || month >= 13 ||
        // not obviously illegal
        ( month == 2  && day == 31 ) ||
        ( month == 4  && day == 31 ) ||
        ( month == 6  && day == 31 ) ||
        ( month == 9  && day == 31 ) ||
        ( month == 11 && day == 31 ) ||
        ( month == 2  && day == 30 ) ||
        //illegal...                   except on leap years
        ( month == 2  && day == 29 && (year % 4 != 0));
    }

    public boolean validate(String date){

        String mm, dd, yyyy;
        if (date.length() == 8){

            mm = date.substring(0,2);
            dd = date.substring(2,4);
            yyyy = date.substring(4,8);

        } else if (date.length() == 10) {

            mm = date.substring(0,2);
            dd = date.substring(3,5);
            yyyy = date.substring(6,10);

        } else {
            return false;
        }

        int month = Integer.parseInt(mm);
        int day   = Integer.parseInt(dd);
        int year  = Integer.parseInt(yyyy);

        return checkIllegalDays(month, day, year);
    }
}