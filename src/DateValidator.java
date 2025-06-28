public class DateValidator {
    public boolean validate(String date){
        if (date.length() == 8){
            return true;
        } else {
            return false;
        }
    }
}