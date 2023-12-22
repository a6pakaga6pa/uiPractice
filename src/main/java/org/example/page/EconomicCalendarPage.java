package org.example.page;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import static org.testng.Assert.assertTrue;

public class EconomicCalendarPage {
    private static final String SLIDER_TEXT = "//div[text()='%s']";
    private static final String IFRAME = "//iframe[@title='iframe']";
    private static final String DAY_LOCATOR = "//button/div[contains(text(), '%s')]";
    private static final String WEEK_RANGE_LOCATOR = "//button/div[contains(text(), '%s')]/parent::button";
    private static final String DOW_LOCATOR = "//div/span[contains(text(), '%s')]";
    private Page page;
    private static double increment = 20;
    private static double sliderX;
    private static double sliderY;
    private static double sliderRight;
    private static FrameLocator frameLocator;
    private final LocalDate TODAY = LocalDate.now();

    public EconomicCalendarPage(Page page) {
        this.page = page;
    }

    public EconomicCalendarPage verifySliderFlow() {

        var todayLocator = String.format(SLIDER_TEXT, "Today");
        var tomorrowLocator = String.format(SLIDER_TEXT, "Tomorrow");
        var nextWeekLocator = String.format(SLIDER_TEXT, "Next Week");

        frameLocator = page.frameLocator(IFRAME);
        BoundingBox sliderBox = frameLocator.getByRole(AriaRole.SLIDER)
                .boundingBox();
        frameLocator.locator(String.format(DOW_LOCATOR, getCurrentDow())).waitFor();
        sliderX = sliderBox.x;
        sliderY = sliderBox.y + sliderBox.height / 2;
        sliderRight = sliderBox.x + sliderBox.width;
        page.mouse().move(sliderX, sliderY);
        page.mouse().down();

        // Check today
        slideToText(todayLocator);
        assertTrue(frameLocator.getByText(getCurrentYear() + "-" + getCurrentMonth()).isVisible(), "Current data is not visible");
        Locator day = frameLocator.locator(String.format(DAY_LOCATOR, getCurrentDay()));
        assertTrue(day.getAttribute("class").contains("mat-calendar-body-selected"), "Current day is not selected");

        // Check tomorrow
        slideToText(tomorrowLocator);
        assertTrue(frameLocator.getByText(getCurrentYear() + "-" + getCurrentMonth()).isVisible(), "Current data is not visible");
        Locator tomorrow = frameLocator.locator(String.format(DAY_LOCATOR, getNextDay()));
        assertTrue(tomorrow.getAttribute("class").contains("mat-calendar-body-selected"), "Current day is not selected");

        // Check next week
        slideToText(nextWeekLocator);
        var nextWeekMonday = getMondayDayNextWeek();
        // TODO Can't verify if visually next week includes days of the next month. If so, logic should be updated to include days from the next month as well
        for (int i = nextWeekMonday; i <= nextWeekMonday + 6; i++) {
            Locator currentDateOfWeek = frameLocator.locator(String.format(WEEK_RANGE_LOCATOR, i));
            assertTrue(currentDateOfWeek.getAttribute("class").contains("mat-calendar-body-in-range"), String.format("Current '%s' day is not selected", i));
        }
        page.mouse().up();
        return this;
    }

    public VideoPage gotoEducationalVideos() {
        ElementHandle link = page.querySelector("text=RESEARCH & EDUCATION        ");
        link.click();
        page.locator("//a[contains(text(), 'Educational Videos')]").click();
        return new VideoPage(page);
    }

    public void slideToText(String text) {
        while (!frameLocator.locator(text).isVisible() && sliderX < sliderRight) {
            sliderX += increment;
            page.mouse().move(sliderX, sliderY);
        }
        page.mouse().click(sliderX, sliderY);
        page.mouse().down();
    }

    public int getCurrentDay() {
        return TODAY.getDayOfMonth();
    }

    public int getNextDay() {
        return TODAY.plusDays(1).getDayOfMonth();
    }

    public int getCurrentMonth() {
        return TODAY.getMonthValue();
    }

    public int getCurrentYear() {
        return TODAY.getYear();
    }

    public String getCurrentDow() {
        return TODAY
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public int getMondayDayNextWeek() {
        LocalDate nextMonday = TODAY
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return nextMonday.getDayOfMonth();
    }

}
