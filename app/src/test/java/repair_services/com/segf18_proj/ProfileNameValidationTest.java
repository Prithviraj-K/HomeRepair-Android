package repair_services.com.segf18_proj;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
//isValidName to be implemented
public class ProfileNameValidationTest {
    @Test
    public void profileName_CorrectProfileNameSample_ReturnTrue() {
        assertTrue(isValidName("testname"));
    }

    @Test
    public void profileName_CorrectProfileNameSample_ReturnFalse() {
        assertFalse(isValidName(""));
    }

    @Test
    public void profileName_CorrectProfileNameSample_ReturnFalse() {
        assertFalse(isValidName(".,/.,"));
    }
}
