# language: en
Feature: Mocked environment should be available
In order to avoid external dependencies we mock external api
calls

    Scenario: Check mocked environment is available
        Given mock server is running on host localhost
        And http normal requests go to port 9080
        And http secure request go to port 9443
        When 'ping' endpoint is invoked using GET method
        Then mockserver responded with 'pong'
        And response header 'content-type' is 'text/plain'
