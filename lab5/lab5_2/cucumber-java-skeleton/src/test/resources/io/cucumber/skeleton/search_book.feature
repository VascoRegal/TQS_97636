Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013/03/14 with category 'Sci-Fi'
    Given a book with the title 'Some other book', written by 'Tim Tomson', published in 2014/09/03 with category 'Drama'
    Given a book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012/01/01 with category 'Sci-Fi'
    When the customer searches for books published between 2013/01/01 and 2014/01/01
    Then 2 books should have been found
    Then Book 1 should have the title 'Some other book'
    Then Book 2 should have the title 'One good book'