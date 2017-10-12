Feature: Create Blog
	As a user
	I want to create a blog

Scenario: Create Blog from admin home page
	Given an admin user is logged in
            When the user is on admin home page
            Then the user clicks on the create blog link
            And the create blog form is displayed
            And user enters "blog" in create blog form name field
            And user enters "blog blog blog" in create blog form tagline field
            And user enters "blog" in create blog form handle field
            And user enters "henrykastler+test%1s@gmail.com" in the create blog form emailAddress field		
            And user selects en_US in the create blog form locale field
            And user selects America/Chicago in the create blog form timezone field
            And user submits the create blog form
            Then a blog created success message should be displayed
	
