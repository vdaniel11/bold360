import Utils.ElementOperations;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

public class RegistrationPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private final By customerFirstnameBy = By.id("customer_firstname");
    private final By firstnameBy = By.id("firstname");
    private final By customerLastnameBy = By.id("customer_lastname");
    private final By lastnameBy = By.id("lastname");
    private final By emailBy = By.id("email");
    private final By passwdBy = By.id("passwd");
    private final By daysBy = By.id("days");
    private final By monthsBy = By.id("months");
    private final By yearsBy = By.id("years");
    private final By submitBy = By.id("submitAccount");
    private final By address1By = By.id("address1");
    private final By cityBy = By.id("city");
    private final By aliasBy = By.id("alias");
    private final By stateBy = By.id("id_state");
    private final By postcodeBy = By.id("postcode");
    private final By countryBy = By.id("id_country");
    private final By phoneMobileBy = By.id("phone_mobile");
    private final By headerUserInfoBy = By.xpath("//div[@class=\"header_user_info\"]//span");

    private final By xpathMandatoryElementsBy = By.xpath("//div[@class=\"account_creation\"]//sup[//*[contains(text(),'*')]]/..");
    private final By xpathErrorElementsBy = By.xpath("//div[@class=\"alert alert-danger\"]//ol/li");


    public RegistrationPage(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
        this.driver = driver;
        this.wait = wait;
        this.js = js;
    }

    private List<WebElement> getMandatoryElements() {
        return driver.findElements(xpathMandatoryElementsBy);
    }

    private List<WebElement> getErrorElements() {
        return driver.findElements(xpathErrorElementsBy);
    }

    public void fillRegistrationForm(String firstName, String lastName, String email_address,
                                     String passwd, String days, String month, String year, String alias,
                                     String address1, String city, String state, String postcode,
                                     String country, String phoneMobile) {
        driver.findElement(customerFirstnameBy).sendKeys(firstName);
        driver.findElement(customerLastnameBy).sendKeys(lastName);
        driver.findElement(emailBy).click();
        Assertions.assertEquals(driver.findElement(emailBy).getAttribute("value"), email_address);

        driver.findElement(passwdBy).sendKeys(passwd);
        new Select(driver.findElement(daysBy)).selectByValue(days);
        new Select(driver.findElement(monthsBy)).selectByValue(month);
        new Select(driver.findElement(yearsBy)).selectByValue(year);

        /* check first and last name default values */
        Assertions.assertEquals(driver.findElement(firstnameBy).getAttribute("value"),
                driver.findElement(customerFirstnameBy).getAttribute("value"));
        Assertions.assertEquals(driver.findElement(lastnameBy).getAttribute("value"),
                driver.findElement(customerLastnameBy).getAttribute("value"));

        if (alias.isEmpty()) {
            /* check alias default value */
            alias = "My address";
            Assertions.assertEquals(driver.findElement(aliasBy).getAttribute("Value"), alias);
        }

        driver.findElement(address1By).sendKeys(address1);
        driver.findElement(cityBy).sendKeys(city);
        new Select(driver.findElement(stateBy)).selectByVisibleText(state);
        driver.findElement(postcodeBy).sendKeys(postcode);
        new Select(driver.findElement(countryBy)).selectByVisibleText(country);
        driver.findElement(phoneMobileBy).sendKeys(phoneMobile);
    }

    public HomePage submitRegistration() {
        driver.findElement(submitBy).click();
        ElementOperations.waitForAndGetElement(headerUserInfoBy, driver, wait);
        return new HomePage(driver, wait, js);
    }

    public List<WebElement> submitFailedRegistration() {
        driver.findElement(submitBy).click();
        return ElementOperations.waitForAndGetElements(xpathErrorElementsBy, driver, wait);
    }

    public void deleteDefaultValues() {
        driver.findElement(emailBy).clear();
        driver.findElement(aliasBy).clear();
        new Select(driver.findElement(countryBy)).selectByValue("");
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(countryBy)).selectByVisibleText(country);
    }

    public void checkMandatoryFields() {
        List<WebElement> mandatoryElements = this.getMandatoryElements();
        List<WebElement> errorElements = this.getErrorElements();
        /*
         * Get field ids which are marked with *.
         * Get error message from the hashmap for the corresponding field id.
         * Search for the error message in the errorElements list.
         */

        HashMap<String, String> errorMessages = new HashMap<>();
        errorMessages.put("phone_mobile", "You must register at least one phone number.");
        errorMessages.put("firstname", "firstname is required.");
        errorMessages.put("customer_firstname", "firstname is required.");
        errorMessages.put("lastname", "lastname is required.");
        errorMessages.put("customer_lastname", "lastname is required.");
        errorMessages.put("email", "email is required.");
        errorMessages.put("passwd", "passwd is required.");
        errorMessages.put("alias", "alias is required.");
        errorMessages.put("address1", "address1 is required.");
        errorMessages.put("city", "city is required.");
        errorMessages.put("postcode", "The Zip/Postal code you've entered is invalid. It must follow this format: 00000");
        errorMessages.put("id_state", "This country requires you to choose a State.");
        errorMessages.put("id_country", "id_country is required.");
//        errorMessages.put("id_country", "Country is invalid.");
//        errorMessages.put("id_country", "Country cannot be loaded with address->id_country");

        for (WebElement mandatoryElement : mandatoryElements) {
            String mandatoryElementFor = mandatoryElement.getAttribute("for");
            if (mandatoryElement.isDisplayed() && driver.findElement(By.id(mandatoryElementFor)).getAttribute("value").isEmpty()) {
                boolean contains = false;
                for (WebElement errorElement : errorElements) {
                    if (errorElement.getText().equals(errorMessages.get(mandatoryElementFor))) {
                        contains = true;
                        break;
                    }
                }
                Assertions.assertTrue(contains, "Not contains error message: " + errorMessages.get(mandatoryElementFor));
            }
        }
    }
}
