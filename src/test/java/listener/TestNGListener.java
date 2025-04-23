package listener;

import com.SWP.config.ConfigSettings;
import com.SWP.driver.DriverManager;
import com.SWP.driver.DriverManagerFactory;
import com.SWP.enums.DriverType;
import com.SWP.log.LogHelper;
import com.SWP.utils.keywords.WebKeywords;
import org.slf4j.Logger;
import org.testng.ITestNGListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNGListener implements ITestNGListener {

    public WebKeywords action;
    public static Logger logger = LogHelper.getLogger();
    private static DriverManager driverManager;

    public TestNGListener() {
        action = new WebKeywords();
    }

    public TestNGListener(WebKeywords action) {
        this.action = action;

    }

    @BeforeTest
    public void beforeTest() throws Throwable {
        action.openBrowser();
        action.maximizeWindow();
        logger.info("Before Test");

    }

//    @Test
//    public void test(){
//
//    }

    @AfterTest
    public void afterTest() {
        action.closeBrowser();
    }

}
