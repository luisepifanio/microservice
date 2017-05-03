package aceptance.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
    strict = true
    , monochrome = false
    , glue = {"classpath:aceptance.steps"}
    // ,tags={"~@manual", "~@review", "~@pending"}
    , format = {
            "pretty",
            "html:build/reports/cucumber",
            "json:build/reports/cucumber/cucumber.json"
    }
    , features = "classpath:features/"
)
public class AcceptanceSpec {

}

