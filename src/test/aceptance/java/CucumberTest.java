import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
    strict=true
    , plugin = {
            "pretty",
            "html:build/reports/cucumber",
            "json:build/reports/cucumber/cucumber.json"
    }
    , monochrome = true
    ,features = "classpath:features/"
    ,glue={"classpath:cucumber.steps"}
    // ,tags={"~@manual", "~@review", "~@pending"}
)
public class CucumberTest {

}

