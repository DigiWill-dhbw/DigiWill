package de.digiwill;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.junit.Cucumber;
import de.digiwill.util.SeleniumDriverUtils;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/de.digiwill/")
public class CucumberTest {

}
