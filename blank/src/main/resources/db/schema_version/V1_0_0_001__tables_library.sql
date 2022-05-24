CREATE TABLE product(
                         id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                         product_name varchar(50) NOT NULL,
                         product_code int(11) NOT NULL,
                         product_brand varchar(50) NOT NULL,
                         product_expiration_date DATE NOT NULL,
                         product_price int(11) NOT NULL,
                         product_cost int(11) NOT NULL,
                         product_quantity int(11) NOT NULL,
                         deleted boolean NOT NULL,
                         PRIMARY KEY (id)
)ENGINE=InnoDB;


CREATE TABLE sale(
                     id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                     sale_date varchar(50) NOT NULL,
                     sale_quantity int(10) NOT NULL,
                     deleted boolean NOT NULL,
                     PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE user(
                        id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                        username varchar(50) NOT NULL,
                        name varchar(50) NOT NULL,
                        phone_number int(11) NOT NULL,
                        password varchar(18) NOT NULL,
                        deleted boolean NOT NULL,
                       PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE sale_user(
                            sale_id int(10)  UNSIGNED NOT NULL,
                            user_id int(10)  UNSIGNED NOT NULL,
                            PRIMARY KEY (sale_id, user_id),
                            CONSTRAINT sale_user_sale_id_fk FOREIGN KEY (sale_id) REFERENCES sale (id) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT sale_user_user_id_fk FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

CREATE TABLE sale_product(
                            sale_id int(10)  UNSIGNED NOT NULL,
                            product_id int(10)  UNSIGNED NOT NULL,
                            PRIMARY KEY (book_id, category_id),
                            CONSTRAINT sale_product_sale_id_fk FOREIGN KEY (sale_id) REFERENCES sale (id) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT sale_product_product_id_fk FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

