package com.VSEE.helper;

import org.openqa.selenium.By;

import java.util.Set;

public class WebCommonHelper {
    public static int getIndex(Set<String> set, String value) {
        int result = 0;
        for (String entry : set) {
            if (entry.equals(value)) {
                return result;
            }
            result++;
        }
        return -1;
    }

    /**
     * get location of element
     *
     * @param ex     : expression to get element
     * @param typeEx : type of expression( id, xpath, linkText,...)
     * @param param  : parameter in expression
     */
    public static By getByStrategy(String ex, String typeEx, String param) {

        By result;
        switch (typeEx) {
            case "id":
                result = By.id(ex);
                break;
            case "xpath":
                result = By.xpath(ex);
                break;
            case "linkText":
                result = By.linkText(ex);
                break;
            case "className":
                result = By.className(ex);
                break;
            case "cssSelector":
                result = By.cssSelector(ex);
                break;
            case "name":
                result = By.name(ex);
                break;
            case "partialLinkText":
                result = By.partialLinkText(ex);
                break;
            case "tagName":
                result = By.tagName(ex);
                break;
            case "xpath_param":
                result = By.xpath(ex.replace("${param}", param));
                break;
            default:
                result = null;
        }
        return result;
    }
}
