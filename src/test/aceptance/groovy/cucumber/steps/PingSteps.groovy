package cucumber.steps

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Given(~/^mock server is running on host (.+)$/) { String host ->
    println("host => $host")
}

Given(~/^http normal requests go to port (\d+)$/) { int normalPort ->
    // Write code here that turns the phrase above into concrete actions
    println("normalPort => $normalPort")
}

Given(~/^http secure request go to port (\d+)$/) { int securePort ->
    // Write code here that turns the phrase above into concrete actions
    println("securePort => $securePort")
}

When(~/^'(.+)' endpoint is invoked using (.+) method$/) { String path, String httpMethod ->
    // Write code here that turns the phrase above into concrete actions
    println("path => $path")
    println("httpMethod => $httpMethod")
}

Then(~/^mockserver responds catching '(.+)'$/) { String responseBody ->
    // Write code here that turns the phrase above into concrete actions
    println("responseBody => $responseBody")
}

Then(~/^response header '(.+)' is '(.+)'$/) { String headerName, String headerValue ->
// Write code here that turns the phrase above into concrete actions
    println("headerName => $headerName")
    println("headerValue => $headerValue")

    assert true
}
