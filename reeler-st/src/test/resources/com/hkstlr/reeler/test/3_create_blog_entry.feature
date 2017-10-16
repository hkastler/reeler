Feature: Create Blog Entry
	As a user
	I want to create a blog entry

Scenario: Account Registration
        Given the user is logged in
	Given the user has a blog and is on create entry page
        
	When the user enters 
|title|category|tags|content|
|Blog Post %1s|General|tag%1s|basic content here|


	And user submits the create entry form
	Then a create entry success message should be displayed
