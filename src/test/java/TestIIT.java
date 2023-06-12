import org.example.IitSignatureService;
import org.testng.annotations.Test;


public class TestIIT {

    @Test
    public void test1() {
        assert true;
    }

    // цей тест падає з io.qameta.allure.AllureResultsWriteException: Could not write Allure test result container
    // не може записати файл результата в target/reports/allure-results
    @Test
    public void test2() {
        IitSignatureService iss = new IitSignatureService();
        assert 1 == 1;
    }
}
