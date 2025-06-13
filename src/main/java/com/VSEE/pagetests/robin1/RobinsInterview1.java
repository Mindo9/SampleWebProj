package com.VSEE.pagetests.robin1;

import com.VSEE.keywords.WebKeywords;
import com.VSEE.log.LogHelper;
import com.VSEE.page.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;

public class RobinsInterview1 extends Page {

    public final String robin_interview1 = config.getConfigProperties().getProperty("robin_interview1");
    public final WebKeywords action;
    public Logger logger = LogHelper.getLogger();

    public RobinsInterview1(WebKeywords action) {
        String locators = "locators";
        this.action = action;
        this.setRepoFile(locators);
    }

    @Step("Open browser")
    public void openBrowser() {
        action.navigateToUrl(robin_interview1);
        logger.info("Open browser with URL: {}", robin_interview1);
    }

    @Step("Enter Waiting Room")
    public void login(String userName) {
        action.setText(findWebElement("NAME_PROCESS"), userName);
        logger.info("Input user name: {}", userName);
        action.click(findWebElement("CKB_TELEMEDICINE_CONSULTATION"));
        logger.info("Enter checkbox telemedicine consultation");
        action.click(findWebElement("BTN_ENTER_WROOM"));
        logger.info("Enter waiting room");
    }

    public boolean verifiedChat() {
        action.click(findWebElement("BTN_CONTINUE_CALL"));
        return action.getElement(findWebElement("BUBBLE_CHAT")).isDisplayed();
    }
}
