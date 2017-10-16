Feature: Create Account Registration
	As a user
	I want to create the system's first user
	In order to access various features offered

Scenario: Account Registration
	Given the user is on create account page
	When the setup first account screen is displayed
	And user enters "testerson" in userName field
	And user enters "testerson" in screenName field
	And user enters "Test Testerson" in fullName field
	And user enters "henrykastler+test%1s@gmail.com" in emailAddress field
	
	And user enters "password" in passwordText field
	And user confirms "password" in password confirm field
	
	And user selects en_US in locale field
	And user selects America/Chicago in timezone field
	And user submits the form
	Then a success message should be displayed
	
