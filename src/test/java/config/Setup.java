package config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {
  public Properties prop = new Properties();
    @BeforeTest
    public void setup() throws IOException {
        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }

    @AfterMethod
    public void reload() throws IOException {
        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }
}
