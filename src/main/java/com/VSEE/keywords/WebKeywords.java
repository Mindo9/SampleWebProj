package com.VSEE.keywords;

import com.VSEE.config.ConfigSettings;
import com.VSEE.driver.DriverManager;
import com.VSEE.driver.DriverManagerFactory;
import com.VSEE.enums.DriverType;
import com.VSEE.helper.WebCommonHelper;
import com.VSEE.log.LogHelper;
import com.VSEE.testNGObject.TestObject;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class WebKeywords {
    private static Logger logger = LogHelper.getLogger();
    private ConfigSettings config;
    public WebDriver driver;
    public String browser;
    private static DriverManager driverManager;
    private static int defaultTimeout;

    public WebKeywords() {
        config = new ConfigSettings(System.getProperty("user.dir"));
        browser = config.getBrowser();
        defaultTimeout = Integer.parseInt(config.getDefaultTimeout());
    }

    public void openBrowser(String... url) {
        logger.info("Running with " + browser);
        driverManager = DriverManagerFactory.getManager(DriverType.valueOf(browser.toUpperCase()));
        WebDriver driver = driverManager.getDriver();
        logger.info(MessageFormat.format("Open ''{0}'' successfully", browser.toUpperCase()));
        String rawUrl = url.length > 0 ? url[0] : "";
        if (rawUrl != null && !rawUrl.isEmpty()) {
            driver.get(rawUrl);
            logger.info(MessageFormat.format("Navigate to ''{0}'' successfully", rawUrl));
        }
    }

    public void maximizeWindow() {
        WebDriver driver = driverManager.getDriver();
        driver.manage().window().maximize();
        logger.info("Maximized window successfully");
    }

    public void closeBrowser() {
        WebDriver driver = driverManager.getDriver();
        logger.info("Closing browser");
        driver.close();
        logger.info("Close browser successfully");
    }

    public void click(Object locator, int... timeout) {
        try {
//			JavascriptExecutor jse = (JavascriptExecutor) driver;
//			jse.executeScript("window.scrollBy(0,250)");
            TestObject to = new TestObject();
            String weId = to.getObjectName();
            WebElement we = getElement(locator, timeout);
            if (we.isEnabled()) {
                logger.info(MessageFormat.format("Clicking web element ''{0}''", weId));
                we.click();
                logger.info(MessageFormat.format("Clicked web element ''{0}'' successfully", weId));
            } else {
                logger.error(MessageFormat
                        .format("Unable to click web element ''{0}'' because the web element is not enable", weId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to click web element because ''{0}''", e.getMessage()));
        }
    }

    public void click(String locator, int... timeout) {
        try {
            WebDriver driver = driverManager.getDriver();
            WebElement we = driver.findElement(By.xpath(locator));
            if (we.isEnabled()) {
                logger.info(MessageFormat.format("Clicking web element ''{0}''", locator));
                we.click();
                logger.info(MessageFormat.format("Clicked web element ''{0}'' successfully", locator));
            } else {
                logger.error(MessageFormat
                        .format("Unable to click web element ''{0}'' because the web element is not enable", locator));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to click web element because ''{0}''", e.getMessage()));
        }
    }

    public void clickAction(Object locator, int... timeout) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
            TestObject to = new TestObject();
            String weId = to.getObjectName();
            WebElement we = getElement(locator, timeout);
            if (we.isEnabled()) {
                logger.info(MessageFormat.format("Clicking web element ''{0}''", weId));
                Actions ac = new Actions(driver);
                ac.click(we);
//				we.click();
                logger.info(MessageFormat.format("Clicked web element ''{0}'' successfully", weId));
            } else {
                logger.error(MessageFormat
                        .format("Unable to click web element ''{0}'' because the web element is not enable", weId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to click web element because ''{0}''", e.getMessage()));
        }
    }

    public void navigateToUrl(final String url, int... timeout) {
        int waitTime = timeout.length > 0 ? timeout[0] : defaultTimeout;
        int pollingTime = waitTime / 10;
        logger.debug("Navigating to " + url);
        Boolean status;
        WebDriver driver = driverManager.getDriver();
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(waitTime))
                    .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            status = (Boolean) wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    driver.navigate().to(url);
                    return true;
                }
            });
        } catch (TimeoutException e) {
            status = false;
            logger.error(MessageFormat.format("Reached timeout. Unable to navigate to ''{0}''", url));
        }
        if (status) {
            logger.info(MessageFormat.format("Navigate to ''{0}'' successfully", url));
        }
    }

    public void setText(Object locator, String text, int... timeout) {
        TestObject to = new TestObject();
        String weId = to.getObjectName();
        WebElement we = getElement(locator, timeout);
        try {
            if (we.isEnabled()) {
                logger.info(MessageFormat.format("Clearing web element ''{0}''", weId));
                we.clear();
                logger.info(MessageFormat.format("Cleared web element ''{0}'' successfully", weId));
                we.sendKeys(text);
                we.sendKeys(Keys.ENTER);
                ;
                logger.info(MessageFormat.format("Set text ''{0}'' to web element ''{1}'' successfully", text, weId));
            } else {
                logger.error(MessageFormat.format(
                        "Unable to set text ''{0}'' to web element ''{1}'' because the web element is not enable", text,
                        weId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to set text ''{0}'' to web element ''{1}'' because ''{2}''", text,
                    weId, e.getMessage()));
        }
    }

    public WebElement getElement(Object locator, int... timeout) {
        final By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
        int waitTime = timeout.length > 0 ? timeout[0] : defaultTimeout;
        int poolingTime = waitTime / 10;
        WebDriver driver = driverManager.getDriver();
        TestObject to = new TestObject();
        String weId = to.getObjectName();
        WebElement we = null;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(waitTime))
                    .pollingEvery(Duration.ofSeconds(poolingTime)).ignoring(NoSuchElementException.class);
            we = (WebElement) wait.until(new Function<WebDriver, WebElement>() {

                public WebElement apply(WebDriver driver) {
                    // TODO Auto-generated method stub
                    return driver.findElement(by);
                }
            });
            if (we != null) {
                logger.info(MessageFormat.format(
                        "Found {0} web elements with id: ''{1}'' located by ''{2}'' in ''{3}'' second(s)", 1, weId, by,
                        timeout));
                return we;
            }
        } catch (TimeoutException e) {
            logger.error(MessageFormat.format("Reached Timeout. Unable to find web element located by ''{0}''", by));
        }
        return null;
    }

    public String getText(Object locator, int... timeout) {
        TestObject to = new TestObject();
        String weId = to.getObjectName();
        String actualText = "";
        try {
            WebElement we = getElement(locator, timeout);
            if (we != null) {
                logger.info(MessageFormat.format("Getting text of web element ''{0}''", weId));
                actualText = we.getText();
                logger.info(MessageFormat.format("Text of web element ''{0}'' is ''{1}''", weId, actualText));
            } else {
                logger.error(MessageFormat.format("Unable to get text of web element ''{0}''", weId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to get text of web element because ''{0}''", e.getMessage()));
        }
        return actualText;
    }

    public List<WebElement> getElements(Object locator, int... timeout) {
        List<WebElement> wes = null;
        int waitTime = timeout.length > 0 ? timeout[0] : defaultTimeout;
        float timeCount = 0;

        long miliseconds = System.currentTimeMillis();
        final By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
        WebDriver driver = driverManager.getDriver();
        TestObject to = new TestObject();
        String weId = to.getObjectName();
        while (timeCount < waitTime) {
            wes = driver.findElements(by);
            if (wes != null) {

                logger.info(MessageFormat.format(
                        "Found {0} web elements with id: ''{1}'' located by ''{2}'' in ''{3}'' second(s)", wes.size(),
                        weId, by, waitTime));
                return wes;
            }
            timeCount += ((double) (System.currentTimeMillis() - miliseconds) / 1000);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeCount += 0.5;
            miliseconds = System.currentTimeMillis();
        }

        logger.error(MessageFormat.format("Reached Timeout. Unable to find web element located by ''{0}''", by));
        return null;
    }

    public Boolean getIndexList(WebElement we, int... timeout) {

        try {
            if (we != null) {
                Object actuatText = we.getSize();
                logger.error(MessageFormat.format(
                        "Actual text ''{0}'' and Expected text ''{1}'' of web element ''{2}'' are not the same",
                        actuatText, we));
                return true;
            } else {

            }

        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to verify web element text because ''{0}''", e.getMessage()));
        }
        return false;

    }

    public String getAttribute(Object locator, String attribute, int... timeout) {
        TestObject to = new TestObject();
        String weId = to.getObjectName();
        String actualText = "";
        try {
            WebElement we = getElement(locator, timeout);
            if (we != null) {
                logger.info(MessageFormat.format("Getting text of web element ''{0}''", weId));
                actualText = we.getAttribute(attribute);
                logger.info(MessageFormat.format("Text of web element ''{0}'' is ''{1}''", weId, actualText));
            } else {
                logger.error(MessageFormat.format("Unable to get text of web element ''{0}''", weId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Unable to get text of web element because ''{0}''", e.getMessage()));
        }
        return actualText;
    }

    public WebDriver getWebDriver() {
        return driverManager.getDriver();
    }

    public static byte[] takeScreenshot(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static boolean checkForHttp500(WebDriver driver) {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        List<LogEntry> logList = logs.getAll();

        for (LogEntry entry : logList) {
            if (entry.getMessage().contains("500")) {
                Allure.attachment("Console_500_Error", new ByteArrayInputStream(takeScreenshot(driver)));
                return true;
            }
        }
        return false;
    }

    public void switchTab(int windowNumber) {
        WebDriver driver = driverManager.getDriver();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(windowNumber));
    }

    public void newTab(int windowNumber) {
        WebDriver driver = driverManager.getDriver();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(windowNumber));
    }

    public String getWindowHandle() {
        WebDriver driver = driverManager.getDriver();
        logger.info("Getting current window handle");
        return driver.getWindowHandle();
    }

    public void switchWindow(String windowHandle) {
        WebDriver driver = driverManager.getDriver();
        driver.switchTo().window(windowHandle);
    }

    public String getCurrentUrl() {
        WebDriver driver = driverManager.getDriver();
        return driver.getCurrentUrl();
    }

    public void hover(String locator){
        WebDriver driver = driverManager.getDriver();
        WebElement we = driver.findElement(By.xpath(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(we).perform();
    }

    public void moveByOffSet(int xOff, int yOff){
        WebDriver driver = driverManager.getDriver();
        Actions actions = new Actions(driver);
        actions.moveByOffset(xOff, yOff);
    }

    public void switchToWindowIndex(Object index) {
        WebDriver driver = driverManager.getDriver();
        Set<String> allWindowHandles = driver.getWindowHandles();
        logger.info("Checking index range");
        if (index == null) {
            throw new IllegalArgumentException("Index is null");
        }
        boolean switched = false;
        logger.debug(MessageFormat.format("Switching window with index: ''{0}''", String.valueOf(index)));
        for (String handle : allWindowHandles) {
            int indexOfHandle = WebCommonHelper.getIndex(allWindowHandles, handle);
            if (indexOfHandle == Integer.parseInt(String.valueOf(index))) {
                try {
                    driver.switchTo().window(handle); // Switch to the desired window first and then execute
                    // commands
                    // using driver
                } catch (NoSuchWindowException e) {
                    logger.error(MessageFormat.format("Unable to switch to window with index: ''{0}''", index));
                }
                switched = true;
            }
        }
        if (switched) {
            logger.info(MessageFormat.format("Switched to window with index: ''{0}''", String.valueOf(index)));
        } else {
            logger.error(MessageFormat.format("Unable to switch to window with index: ''{0}''", String.valueOf(index)));
        }
    }

    public void switchNewWindow(String url) {
        WebDriver driver = driverManager.getDriver();
        WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
        newWindow.get(url);
    }

    public void altTab() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        // Simulate ALT+TAB (may cycle only once)
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(500);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

}
