Feature: Login Process
 
As a user
I want to login to my account
So that I can access the features associated to my account
 
Scenario: No account found
Given the user is on the login page
When user enters "testola" as the email address
And enters "testola" as the password
And submits the form
Then a fail message is displayed

Scenario: Account found
Given the user is on the login page
When user enters "testerson" as the email address
And enters "password" as the password
And submits the form
Then the user is redirected to the admin

