package org.example.util;

import com.microsoft.playwright.*;
import org.example.enums.BrowserEnum;

import java.awt.*;

public class BrowserFactory {

    private static Browser browser;
    private static Playwright playwright;
    private static BrowserType browserType;

    public static Page initBrowserPage(BrowserEnum browserEnum, String resolution) {
        playwright = Playwright.create();
        int height;
        int width;
        switch (browserEnum) {
            case CHROME:
                browserType = playwright.chromium();
                break;
            case FIREFOX:
                browserType = playwright.firefox();
                break;
            case SAFARI:
                browserType = playwright.webkit();
                break;
            default:
                browserType = playwright.chromium();
        }
        browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
        if(resolution.equals("MAX")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            height = (int)screenSize.getHeight();
            width = (int)screenSize.getWidth();
        } else {
            width = Integer.parseInt(resolution.split("x")[0]);
            height = Integer.parseInt(resolution.split("x")[1]);
        }
        return browser
                .newContext(new Browser.NewContextOptions().setViewportSize(width, height))
                .newPage();
    }

    public static Playwright getPlaywright() {
        return playwright;
    }
}
