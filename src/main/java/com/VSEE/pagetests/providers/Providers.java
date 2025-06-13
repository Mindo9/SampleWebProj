package com.VSEE.pagetests.providers;

import com.VSEE.keywords.WebKeywords;
import com.VSEE.log.LogHelper;
import com.VSEE.page.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import java.security.Key;

public class Providers extends Page {

    private final WebKeywords action;
    public Logger logger = LogHelper.getLogger();
    public final String vss_meeting2 = config.getConfigProperties().getProperty("robin_interview1");
    private final String xpathPatientCall = "//h4[normalize-space()='Visit ID {idNo}']/ancestor::div[2]/following-sibling::div//div[contains(@class,'action_buttons')]//a[1]";
    private final String boardAnnotations = "//div[.id='board-annotations']";

    public Providers(WebKeywords action) {
        String locators = "locators";
        this.action = action;
        this.setRepoFile(locators);
    }

    @Step("Open browser")
    public void openBrowser() {
        action.openBrowser(vss_meeting2);
        action.maximizeWindow();
        logger.info("Open browser with URL: {}", vss_meeting2);
    }

    @Step("Login to providers view")
    public void login(String userName, String password) {
        action.click(findWebElement("LBL_FOR_PROVIDERS"));
        logger.info("Click on label For Providers");
        action.setText(findWebElement("USER_NAME"), userName);
        logger.info("Input user name: {}", userName);
        action.setText(findWebElement("PASSWORD"), password);
        logger.info("Input password name: {}", password);
        action.click(findWebElement("BTN_LOGIN"));
        logger.info("Login");
    }

    public void startACall(String id) {
        action.click(findWebElement("LBL_GETTING_READY"));
        String patientCall = xpathPatientCall.replace("{idNo}", id);
        action.click(patientCall);
        action.click(findWebElement("BTN_CONTINUE_CALL"));
    }

    public void chatWithPatient() {
        action.moveByOffSet(500, 500);
        action.click(findWebElement("ICON_BOX_CHAT"));
        action.setText(findWebElement("INBOX_TEXT_CHAT"), "Are U OK");
    }
}
