import Utils.ElementOperations;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Automation Practice")
public class AutomationPractice {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    String url = "http://automationpractice.com/index.php";

    @BeforeEach
    void init() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void afterEach(){
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Automate User Registration Process")
    void automateUserRegistrationProcess() {
        String email_address = "UrbanKrisztina1111111114@jourrapide.com";
        String firstName = "Krisztina";
        String lastName = "Urban";

        WelcomePage welcomePage = new WelcomePage(driver, wait, js, url);
        RegistrationPage registrationPage = welcomePage.openRegistrationPage(email_address);
        registrationPage.fillRegistrationForm(firstName, lastName, email_address, "123qweert", "22",
                "10", "1960", "", "Test Street", "Oklahoma City", "Oklahoma",
                "73101", "United States", "+14012312312");
        HomePage homePage = registrationPage.submitRegistration();
        Assertions.assertEquals(homePage.getLoggedInUsername(), firstName + " " + lastName);
    }

    @Test
    @Order(2)
    @DisplayName("Verify Error Messages for mandatory fields")
    void verifyErrorMessages() {
        String email_address = "UrbanKrisztina12121114@jourrapide.com";

        WelcomePage welcomePage = new WelcomePage(driver, wait, js, url);
        RegistrationPage registrationPage = welcomePage.openRegistrationPage(email_address);

        /* delete default values */
        /* case1: delete selected country and check selected country only */
        registrationPage.deleteDefaultValues();

        /* get mandatory fields and error messages */
        registrationPage.submitFailedRegistration();
        registrationPage.checkMandatoryFields();

        /* case2: select country (USA) and check empty state and empty postal code */
        registrationPage.selectCountry("United States");
        /* get mandatory fields and error messages */
        registrationPage.submitFailedRegistration();
        registrationPage.checkMandatoryFields();
    }

    @Test
    @Order(3)
    @DisplayName("Scroll down with JavaScript")
    void scrollDownWithJS() {
        WelcomePage welcomePage = new WelcomePage(driver, wait, js, url);
        Assertions.assertTrue(ElementOperations.searchElement(js, welcomePage.getFooter(), 100),
                "Cannot found element after the scrolling is finished.");
    }
}
