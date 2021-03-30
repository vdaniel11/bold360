package guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;


    private String mainHandle;
    private final By iFrameBy = By.xpath("//article[@class=\"item-page\"]//iframe[@id=\"a077aa5e\"]");
    private final By iFrameImgBy = By.xpath("html/body/a/img");
    private final By testingMainMenuBy = By.xpath("//div[@class=\"menu-block\"]/ul/li/a[contains(text(),\"Testing\")]");
    private final By seleniumSubMenuBy = By.xpath("//*[contains(text(),\"Selenium\") and contains(@href,\"selenium-tutorial\")]");
    private final By redJoinBtnBy = By.xpath("//button[contains(@id,\"providence-FieldsElementButton\")]");


    public WelcomePage(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String url){
        this.driver = driver;
        this.wait = wait;
        this.js = js;
        driver.get(url);
    }

    public void switchToIFrameAndClickOnImg(){
        driver.switchTo().frame(driver.findElement(iFrameBy));
        driver.findElement(iFrameImgBy).click();
    }

    public void switchToTab (String pageTitle) {
        mainHandle = driver.getWindowHandle();
        for (String tabHandle : driver.getWindowHandles()) {
            driver.switchTo().window(tabHandle);
            if (driver.getTitle().equalsIgnoreCase(pageTitle)) {
                break;
            }
        }
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public void switchBackToMainHandle (){
        driver.close();
        driver.switchTo().window(mainHandle);
    }

    public Actions selectTestingMainMenu(){
        WebElement testingMainMenuItem = driver.findElement(testingMainMenuBy);
        Actions action = new Actions(driver);
        action.moveToElement(testingMainMenuItem);
        return action;
    }

    public void selectSeleniumMenu(Actions action){
        WebElement seleniumSubMenu = driver.findElement(seleniumSubMenuBy);
        action.moveToElement(seleniumSubMenu);
        action.click().build().perform();
    }

    public WebElement getRedJoinBtn(){
        return Utils.ElementOperations.waitForAndGetElement(redJoinBtnBy, driver, wait);
    }
}
