


CREATE TABLE IF NOT EXISTS User
	(user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstName VARCHAR(30),
	lastName VARCHAR(30),
	email VARCHAR(40) UNIQUE,
	password VARCHAR(30),
	phone INT,
	Gender VARCHAR(5),
	member TINYINT(1),
	created DATETIME);

CREATE TABLE IF NOT EXISTS Roles(
	role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30));


CREATE TABLE IF NOT EXISTS User_role
	(user_id INT,
	role_id INT,
	FOREIGN KEY (user_id) REFERENCES User(user_id),
	FOREIGN KEY (role_id) REFERENCES Roles(role_id)
	);


CREATE TABLE IF NOT EXISTS Order(
	order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	customer_id INT,
	order_date DATE,
	status ENUM('submitted','processing','delivered'),
	discount DECIMAL(20,4),
	tax DECIMAL(20,4),
	total DECIMAL(20,4),
	FOREIGN KEY (customer_id) REFERENCES User(user_id)
	);

CREATE TABLE IF NOT EXISTS Delivery
	(delivery_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	order_id INT,
	delivery_agent_id INT,
	pickup_date DATE,
	drop_date DATE
	FOREIGN KEY (order_id) REFERENCES Order(order_id)
	FOREIGN KEY (delivery_agent_id) REFERENCES User(user_id));


CREATE TABLE IF NOT EXISTS Address
	(address_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	address_line1 VARCHAR2(40),
	address_line2 VARCHAR2(40),
	city VARCHAR2(40),
	state VARCHAR2(40),
	zip_code INT,
	address_type VARCHAR2(40),
	addressable_id INT);

CREATE TABLE IF NOT EXISTS ServiceItem
	(serviceItem_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	admin_user_id INT,
	service_name VARCHAR2(40),	
	price INT,
	active BOOLEAN,
	service_type VARCHAR2(50),
	FOREIGN KEY (admin_user_id) REFERENCES User(user_id));

CREATE TABLE IF NOT EXISTS OrderItems
	(orderItems_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	order_id INT,
	price INT,
	service_name VARCHAR2(40),
	service_type VARCHAR2(50),
	quantity INT,
	FOREIGN KEY (order_id) REFERENCES Order(order_id));

CREATE TABLE IF NOT EXISTS Promotion(
	promotion_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	admin_user_id INT,
	discount_percentage DECIMAL(20,4),
	FOREIGN KEY (admin_user_id) REFERENCES User(user_id));


	
	









