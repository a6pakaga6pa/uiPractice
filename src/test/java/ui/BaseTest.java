package ui;

import com.microsoft.playwright.Page;
import org.example.config.Config;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.example.util.BrowserFactory.getPlaywright;

public class BaseTest {

    protected Page page;
    protected static Config config;

    @BeforeClass
    public void init() {
        config = Config.getInstance();
    }

    @AfterMethod
    public void shutDown() {
        getPlaywright().close();
    }
}
