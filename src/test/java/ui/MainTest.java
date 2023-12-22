package ui;

import org.example.config.Config;
import org.example.enums.BrowserEnum;
import org.example.page.EconomicCalendarPage;
import org.example.page.StartPage;
import org.example.page.VideoPage;
import org.example.util.BrowserFactory;
import org.testng.annotations.Test;

public class MainTest extends BaseTest {

    @Test(dataProvider = "viewPortSize", dataProviderClass = DataProviders.class)
    public void mainTest(String viewPortSize) {
        page = BrowserFactory.initBrowserPage(BrowserEnum.FIREFOX, viewPortSize);
        StartPage startPage = new StartPage(page);
        startPage.gotoMainPage(Config.getInstance().getBaseUrl());
        startPage.gotoResearchAndEducation();
        EconomicCalendarPage calendar = startPage.gotoEconomicCalendar();
        calendar.verifySliderFlow();
        VideoPage videoPage = calendar.gotoEducationalVideos();
        videoPage.selectIntro()
                .selectTestLesson()
                .playForSeconds(5);

    }
}
