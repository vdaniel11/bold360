package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElementOperations {
    public static WebElement waitForAndGetElement(By locator, WebDriver driver, WebDriverWait wait) {
        WebElement a;
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public static List<WebElement> waitForAndGetElements(By locator, WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public static boolean searchElement(JavascriptExecutor js, WebElement webElement, int scrollBy) {
        double previousTop;
        double currentTop = 0;
        boolean elementFound = false;

        do {
            previousTop = currentTop;
            if (((boolean) js.executeScript("var rect = arguments[0].getBoundingClientRect(); " +
                    "return (rect.top >= 0 && rect.left >= 0 && " +
                    "rect.bottom <= (window.innerHeight || document. documentElement.clientHeight) && " +
                    "rect.right <= (window.innerWidth || document. documentElement.clientWidth));", webElement)) &&
                    webElement.isDisplayed()) {
                elementFound = true;
            } else {
                js.executeScript("window.scrollBy(0, " + scrollBy + ");");
                currentTop = (double) js.executeScript("var position = arguments[0].getBoundingClientRect();return position.top;", webElement);
            }

        }
        while (previousTop != currentTop && !elementFound);

        return elementFound;
    }
}
