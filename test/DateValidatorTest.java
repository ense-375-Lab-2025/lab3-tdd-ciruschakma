import org.junit.jupiter.api.Test; //JUnit5
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorTest {
    
    @Test
    public void Validate_Today_True(){
        DateValidator dateValidator = new DateValidator();
        boolean legalDate = dateValidator.validate("06202025");
        assertTrue(legalDate);

    }
}