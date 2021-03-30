import Utils.ElementOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private final By loginBy = By.className("login");
    private final By emailCreateBy = By.id("email_create");
    private final By submitCreateBy = By.id("SubmitCreate");
    private final By idGender1By = By.id("id_gender1");
    private final By footerBy = By.xpath("//section[contains(@class,\"bottom-footer\")]");

    public WelcomePage(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String url) {
        this.driver = driver;
        this.wait = wait;
        this.js = js;
        driver.get(url);
        ElementOperations.waitForAndGetElement(loginBy, driver, wait);
    }

    public RegistrationPage openRegistrationPage(String email_address) {
        driver.findElement(loginBy).click();
        ElementOperations.waitForAndGetElement(emailCreateBy, driver, wait).sendKeys(email_address);
        driver.findElement(submitCreateBy).click();
        ElementOperations.waitForAndGetElement(idGender1By, driver, wait).click();
        return new RegistrationPage(driver, wait, js);
    }

    public WebElement getFooter() {
        return driver.findElement(footerBy);
    }
}
