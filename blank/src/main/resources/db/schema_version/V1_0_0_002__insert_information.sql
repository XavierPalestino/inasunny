insert into category (name) values ("Arte"), ("Drama");

insert into book (title, pages, isbn, price, summary, editorial, release_date, deleted) values 
  ("title 1", 785, "7766-0543-1", 597.99, "summary 1", "Editorial 1", "1995-08-04", 0),
  ("title 2", 235, "7766-0543-2", 747.99, "summary 2", "Editorial 2", "1777-01-01", 0),
  ("title 3", 785, "7766-0543-3", 600.00, "summary 3", "Editorial 3", "1500-08-04", 0),
  ("title 4", 235, "7766-0543-4", 335.00, "summary 4", "Editorial 4", "1650-04-01", 0);

insert into author(name, first_name, second_name, birthday) values 
  ("author-1", "fn-1", "sn-1", "1950-10-06"),
  ("author-2", "fn-2", "sn-2", "1960-11-07"),
  ("author-3", "fn-3", "sn-3", "1970-12-08"),
  ("author-4", "fn-4", "sn-4", "1980-01-09"),
  ("author-5", "fn-5", "sn-5", "1990-02-10"),
  ("author-6", "fn-6", "sn-6", "1940-03-11");

insert into book_author(book_id, author_id) VALUES 
  (1, 1), (1, 2), (1, 3), (2, 1), (3, 2), (4, 3);
  
insert into book_category(book_id, category_id) VALUES 
  (1, 1), (2, 2), (3, 1), (3, 2), (4, 1), (4, 2);

insert into ranking(score, book_id) values 
  (4,1), (4,1), (3,1), (5,1), (3,2), (4,2), (4,3), (3,3), (4,3);
