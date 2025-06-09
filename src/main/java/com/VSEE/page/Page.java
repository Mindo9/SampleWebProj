package com.VSEE.page;

import com.VSEE.config.ConfigSettings;
import com.VSEE.helper.FileHelper;
import com.VSEE.helper.WebCommonHelper;
import com.VSEE.log.LogHelper;
import com.VSEE.testNGObject.TestObject;
import com.VSEE.keywords.WebKeywords;
import com.jayway.jsonpath.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Page {
    protected static final Logger logger = LogHelper.getLogger();
    protected WebKeywords action;
    protected HashMap<String, String> repoFile;
    protected ConfigSettings config;
    private TestObject to = new TestObject();


    protected Page() {
        action = new WebKeywords();
        PageFactory.initElements(new AjaxElementLocatorFactory(action.getWebDriver(), 30), this);
        config = new ConfigSettings(System.getProperty("user.dir"));
    }

    protected Page(WebKeywords action) {
        this();
        this.action = action;
        config = new ConfigSettings(System.getProperty("user.dir"));
    }

    /**
     * @param strRepoFile
     * @return
     */
    public String setRepoFile(String strRepoFile) {
        if (this.repoFile == null) {
            this.repoFile = new HashMap<>();
        }
        // String strClassName = Reflection.getCallerClass(2).getSimpleName();
        String strClassName = getCallerClass(2).getSimpleName();
        logger.info("Set Repo File - Get caller class 2: " + strClassName); //Set Repo File - Get caller class 2: W3SchoolSignUp

//	    Json file
        strRepoFile = strRepoFile + File.separator + strClassName + ".json"; // Repo file relative path: object_repository\W3SchoolSignUp.json
        logger.info("Repo file relative path: " + strRepoFile);
        strRepoFile = FileHelper.getCorrectJsonFilePath(strRepoFile);
        return this.repoFile.put(strClassName, strRepoFile);
    }

    private static Class<?> getCallerClass(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        try {
            return getCallerClassFromStackTrace(index + 1);
        } catch (final ClassNotFoundException e) {
            logger.error("Could not find class in ReflectionUtil.getCallerClass({}), index<" + index + ">"
                    + ", exception< " + e + ">");
        }
        return null;
    }

    private static Class<?> getCallerClassFromStackTrace(final int index) throws ClassNotFoundException {

        final StackTraceElement[] elements = new Throwable().getStackTrace();
        int i = 0;
        for (final StackTraceElement element : elements) {
            if (isValidlMethod(element)) {
                if (i == index) {
                    return Class.forName(element.getClassName());
                }
                ++i;
            }
        }
        throw new IndexOutOfBoundsException(Integer.toString(index));
    }

    private static boolean isValidlMethod(final StackTraceElement element) {
        if (element.isNativeMethod()) {
            return false;
        }
        final String cn = element.getClassName();
        if (cn.startsWith("sun.reflect.")) {
            return false;
        }
        final String mn = element.getMethodName();
        if (cn.startsWith("java.lang.reflect.") && (mn.equals("invoke") || mn.equals("newInstance"))) {
            return false;
        }
        if (cn.equals("java.lang.Class") && mn.equals("newInstance")) {
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    public String getRepoFile() {
        String strClassName = getCallerClass(3).getSimpleName();
        return this.repoFile.get(strClassName);
    }

    /**
     * @param nameOfElement
     * @return
     */
    public By findWebElement(String nameOfElement) {
        this.to.setObjectName(nameOfElement);
        String repoFile = this.getRepoFile();
        try {
            File jsonRepoFile = new File(repoFile);
            String value = JsonPath.read(jsonRepoFile, "$." + nameOfElement).toString();
            String[] locator = value.split(":");
            return WebCommonHelper.getByStrategy(locator[1], locator[0], "");
        } catch (IOException e) {
            logger.error("Class Page | Method findWebElement | Error message: " + e.getMessage());
        }
        return null;

    }
}
