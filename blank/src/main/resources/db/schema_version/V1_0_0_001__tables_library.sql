CREATE TABLE category(
                         id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                         name varchar(50) NOT NULL,
                         PRIMARY KEY (id)
)ENGINE=InnoDB;


CREATE TABLE book(
                     id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                     title varchar(50) NOT NULL,
                     pages int(10) NOT NULL,
                     isbn varchar(13) NOT NULL,
                     price decimal(6,2) NOT NULL,
                     summary varchar(350) NOT NULL,
                     editorial varchar(35) NOT NULL,
                     release_date Date NOT NULL,
                     deleted boolean NOT NULL,
                     PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE author(
                       id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                       name varchar(50) NOT NULL,
                       first_name varchar(50) NOT NULL,
                       second_name varchar(50) NOT NULL,
                       birthday date,
                       PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE book_author(
                            book_id int(10)  UNSIGNED NOT NULL,
                            author_id int(10)  UNSIGNED NOT NULL,
                            PRIMARY KEY (book_id, author_id),
                            CONSTRAINT book_author_book_id_fk FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT book_author_author_id_fk FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

CREATE TABLE book_category(
                            book_id int(10)  UNSIGNED NOT NULL,
                            category_id int(10)  UNSIGNED NOT NULL,
                            PRIMARY KEY (book_id, category_id),
                            CONSTRAINT book_category_book_id_fk FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT book_category_category_id_fk FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

CREATE TABLE ranking(
                        id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                        score int(1) NOT NULL,
                        book_id int(10) UNSIGNED NOT NULL,
                        CONSTRAINT ranking_book_id_fk FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE ON UPDATE CASCADE,
                        PRIMARY KEY (id)
)ENGINE=InnoDB;
