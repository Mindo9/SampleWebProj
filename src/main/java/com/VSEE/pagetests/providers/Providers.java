package com.VSEE.pagetests.providers;

import com.VSEE.keywords.WebKeywords;
import com.VSEE.log.LogHelper;
import com.VSEE.page.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;

public class Providers extends Page {

    public WebKeywords action;
    public Logger logger= LogHelper.getLogger();
    private final String vss_meeting2 = config.getConfigProperties().getProperty("vss_meeting2");

    public Providers(WebKeywords action) {
        String locators = "locators";
        this.action=action;
        this.setRepoFile(locators);
    }

    @Step("Open browser")
    public void openBrowser(){
        action.navigateToUrl(vss_meeting2);
    }

    @Step("Login to providers view")
    public void login(String userName, String password) {
        action.setText(findWebElement("USER_NAME"), userName);
        logger.info("Input user name: {}", userName);
        action.setText(findWebElement("PASSWORD"), password);
        logger.info("Input password name: {}", password);
        action.click(findWebElement("BTN_LOGIN"));
        logger.info("Login");
    }

    @Step("No one online before")
    public boolean NoOneOnline() {
        return action.getText(findWebElement("LBL_VISIT_GRP")).equals("Sẵn sàng cho Thăm Khám (0)");
    }
}
