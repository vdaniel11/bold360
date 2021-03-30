import guru99.WelcomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("iFrame Tab Handling")
public class iFrameTabHandling {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    String url = "http://demo.guru99.com/test/guru99home/";

    @BeforeEach
    void init() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void afterEach() {
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("iFrame and tab handling")
    void iFrameTabHandlingTest() {
        String pageTitle = "Selenium Live Project: FREE Real Time Project for Practice";

        /* 1. Open this url http://automationpractice.com/index.php */
        WelcomePage welcomePage = new WelcomePage(driver, wait, js, url);
        /* 2. Locate the image inside the iframe near bottom of the page just above “Email Submission” and click on it */
        welcomePage.switchToIFrameAndClickOnImg();
        /* 3. Verify new page is loaded in new tab with title “Selenium Live Project: FREE Real Time
         * Project for Practice”
         */
        welcomePage.switchToTab(pageTitle);
        Assertions.assertTrue(welcomePage.getPageTitle().equalsIgnoreCase(pageTitle));
        /* 4. Close this tab and switch back to main window */
        welcomePage.switchBackToMainHandle();
        /* 5. From top menu click on Selenium link that can be seen while hovering on Testing menu item */
        welcomePage.selectSeleniumMenu(welcomePage.selectTestingMainMenu());
        /* 6. Verify the wide red “Join Now” button is displayed near bottom of the page */
        Assertions.assertTrue(welcomePage.getRedJoinBtn().isDisplayed(), "red “Join Now” button is not displayed");
    }
}
