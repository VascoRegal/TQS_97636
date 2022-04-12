Feature: Finding flights
    Background: BlazeDemo page
        Given I am on the BlazeDemo Homepage

    Scenario: Choosing places
        When I choose departure 'Portland'
        Then departure is 'Portland'
        When I choose destination 'London'
        Then destination is 'London'
        When I find flights
        Then page title is 'BlazeDemo - reserve'

        When I choose flight 3
        Then page title is 'BlazeDemo Purchase'

        When I input name 'Vasco'
        Then name is 'Vasco'

        When I input address '123'
        Then address is '123'

        When I find flights
        Then page title is 'BlazeDemo Confirmation'
