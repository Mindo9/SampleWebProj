package com.SWP.loginpage;

import com.SWP.log.LogHelper;
import com.SWP.page.Page;
import com.SWP.utils.keywords.WebKeywords;
import io.qameta.allure.Step;
import org.slf4j.Logger;

public class LoginPage extends Page {

    public WebKeywords action;
    public Logger logger= LogHelper.getLogger();
    private final String fe_uat_url = config.getConfigProperties().getProperty("fe_uat_url");


    public LoginPage(WebKeywords action) {
        String locators = "locators";
        this.action=action;
        this.setRepoFile(locators);
    }

    @Step("Login")
    public void login(String userName, String password) throws InterruptedException {
        action.navigateToUrl(fe_uat_url);
        action.setText(findWebElement("USER_NAME"), userName);
        logger.info("Input user name: {}", userName);
        action.setText(findWebElement("PASSWORD"), password);
        logger.info("Input password: {}", password);
        action.click(findWebElement("BTN_LOGIN"));
        Thread.sleep(5000);
    }
}
