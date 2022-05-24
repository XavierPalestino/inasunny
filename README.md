# iwana-sale
Basic project to demo the use of Spring, Hibernate and JPA.


## Project 

```
mvn clean install -P dev -DskipTests=true
java -jar blank-api.jar

https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/project-setup
```

#Test
Postman:
    The file that can be imported to Postman is in: src/main/resources/postman

## Database

name: test
username: dev
password: demo

## Description of the work performed
 Tasks:
 
    - CRUD operations for:
    	- Author
    	- Book
    	- Category
    - DBUnit methods
    - Use of Controller Advice to catch errors
    - Custom exception
    - Endpoints to retrieve information
    
 Endpoints:

    1. Show all sales sorted by year of publication in ascending order, the results should be paginated. (10 sales in total, leaving 2 sales per page)
    2. Books descending by year of publication. (10 sales in total, leaving 2 sales per page)
    3. Search for those sales where X user participated by matching either his first or last name.
    4. Where the price is in the range from priceMinimum to priceMaximum.
    5. Given a number, search for sales containing that number of users.
    6. Search for those sales in a date range (StartDate, EndDate) of publication.
    7. Number of sales that exist of x products
    8. Filter sales by products.
    9. Filter sales in ascending and descending order by number of pages contained in the sale.
