import org.junit.jupiter.api.Test; //JUnit5
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DateValidatorTest {
    
    @Test
    public void Validate_Today_True(){
        DateValidator dateValidator = new DateValidator();
        boolean legalDate = dateValidator.validate("06202025");
        assertTrue(legalDate);
    }

    @Test
    public void Validate_Tomorrow_True(){
        DateValidator dateValidator = new DateValidator();
        boolean legalDate = dateValidator.validate("06212025");
        assertTrue(legalDate);
    }

        @Test
    public void Validate_Jan32_False(){
        DateValidator dateValidator = new DateValidator();
        boolean illegalDate = dateValidator.validate("01322025");
        assertFalse(illegalDate);
    }
    
        @Test
    public void Validate_Feb31_False(){
        DateValidator dateValidator = new DateValidator();
        boolean illegalDate = dateValidator.validate("02312025");
        assertFalse(illegalDate);
    }

        @Test
    public void Validate_LeapDay_True(){
        DateValidator dateValidator = new DateValidator();
        boolean legalDate = dateValidator.validate("02292020");
        assertTrue(legalDate);
    }
}