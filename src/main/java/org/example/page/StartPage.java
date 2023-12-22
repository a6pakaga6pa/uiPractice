package org.example.page;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.example.config.Config;

public class StartPage {
    private static final String ACCEPT_ALL_BTN = "//button[text()='ACCEPT ALL']";
    private static final String ECONOMIC_CALENDAR = "//a[contains(text(),'Economic Calendar')]";
    private Page page;
    private static double increment = 20;

    public StartPage(Page page) {
        this.page = page;
    }

    public StartPage gotoMainPage(String baseUrl) {
        page.navigate(Config.getInstance().getBaseUrl());
        page.click(ACCEPT_ALL_BTN);

        return this;
    }

    public StartPage gotoResearchAndEducation() {
        ElementHandle link = page.querySelector("text=RESEARCH & EDUCATION        ");
        link.click();
        return this;
    }

    public EconomicCalendarPage gotoEconomicCalendar() {
        page.click(ECONOMIC_CALENDAR);
        return new EconomicCalendarPage(page);
    }


}
