package ui;

import org.example.config.Config;
import org.testng.annotations.DataProvider;


public class DataProviders {

    @DataProvider(name = "viewPortSize")
    public Object[][] viewPortSize() {
        return new Object[][]{
                {Config.getInstance().getResolutions().get(0)},
                {Config.getInstance().getResolutions().get(1)},
                // TODO 800*600 resolution wasn't implemented
        };
    }

}


