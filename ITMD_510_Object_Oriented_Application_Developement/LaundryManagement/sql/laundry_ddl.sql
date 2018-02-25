CREATE TABLE IF NOT EXISTS schavan7_users
	(user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(40) UNIQUE,
	password VARCHAR(30),
	phone VARCHAR(11),
	user_type VARCHAR(15),
	created TIMESTAMP default CURRENT_TIMESTAMP);


CREATE TABLE IF NOT EXISTS schavan7_addresses
	(address_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	full_name VARCHAR(100),
	address_line1 VARCHAR(40),
	address_line2 VARCHAR(40),
	city VARCHAR(40),
	state VARCHAR(4),
	zip_code VARCHAR(10),
	phone VARCHAR(15),
	addressable_id  INT,
	addressable_type VARCHAR(40)
	);


	
CREATE TABLE IF NOT EXISTS schavan7_service_items
	(service_item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	service_name VARCHAR(40),	
	price decimal(8,2) DEFAULT 0.0,
	service_type VARCHAR(50),
	created TIMESTAMP default CURRENT_TIMESTAMP);
	
	
CREATE TABLE IF NOT EXISTS schavan7_carts
	(cart_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT,
	tax DECIMAL(8,2) DEFAULT 0.0,
	total DECIMAL(8,2) DEFAULT 0.0,
	order_pickup_date DATE,
	FOREIGN KEY (user_id) REFERENCES schavan7_users(user_id)
	);
	
CREATE TABLE IF NOT EXISTS schavan7_cart_items
	(cart_item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cart_id INT NOT NULL,
	service_name VARCHAR(40),	
	price decimal(8,2) DEFAULT 0.0,
	service_type VARCHAR(50),
	quantity INT(4) DEFAULT 0,
	tax DECIMAL(8,2) DEFAULT 0.0,
	sub_total_price DECIMAL(8,2) DEFAULT 0.0,
	total_price DECIMAL(8,2) DEFAULT 0.0,
	created TIMESTAMP default CURRENT_TIMESTAMP,
	FOREIGN KEY(cart_id) REFERENCES schavan7_carts(cart_id));
	
CREATE TABLE IF NOT EXISTS schavan7_orders
	(order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT,
	order_pickup_date DATE,
	status ENUM ('submitted','processing','delivered',''),
	tax DECIMAL(8,2) DEFAULT 0.0,
	total DECIMAL(8,2) DEFAULT 0.0,
	created TIMESTAMP default CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES schavan7_users(user_id)
	);	

CREATE TABLE IF NOT EXISTS schavan7_order_items
	(order_item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	order_id INT,
	price DECIMAL(8,2),
	tax DECIMAL(8,2),
	service_name VARCHAR(40),
	service_type VARCHAR(50),
	quantity INT(5) DEFAULT 0,
	sub_total_price DECIMAL(8,2) DEFAULT 0.0,
	total_price DECIMAL(8,2),
	FOREIGN KEY(order_id) REFERENCES schavan7_orders(order_id));
	
	


INSERT INTO schavan7_users(first_name,last_name,email,password,phone,user_type) VALUES ("Shrija","Chavan","admin@example.com","password","1234567890","AdminUser");






