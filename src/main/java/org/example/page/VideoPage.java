package org.example.page;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;

public class VideoPage {
    private static final String BIG_PLAY_BUTTON = "//div[@class='player-big-play-button']";
    private static final String IFRAME = "//iframe[@title='Video Player']";
    private static final String PAUSE_BUTTON = "//div[@class='player-play-pause player-button']";
    private static final String TRIGGER_VALUE = "//div[text()='00:0%s']";
    private Page page;
    private static FrameLocator frameLocator;

    public VideoPage(Page page) {
        this.page = page;
    }

    public VideoPage selectIntro() {
        var link = page.locator("//button[contains(text(),'Intro')]");
        link.click();

        return this;
    }

    public VideoPage selectTestLesson() {
        var link = page.locator("//a[contains(text(),'1.1')]");
        link.click();

        return this;
    }

    public VideoPage playForSeconds(int seconds) {
        frameLocator = page.frameLocator(IFRAME);
        frameLocator.locator(BIG_PLAY_BUTTON).waitFor();
        frameLocator.locator(BIG_PLAY_BUTTON).click();
        frameLocator.locator(String.format(TRIGGER_VALUE, seconds)).waitFor();
        frameLocator.locator(PAUSE_BUTTON).click();
        return this;
    }
}
