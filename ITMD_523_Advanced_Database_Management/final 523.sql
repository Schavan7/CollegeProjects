#USER table--
CREATE TABLE USER(
   user_id INTEGER  NOT NULL PRIMARY KEY,
  FirstName  VARCHAR(10) NOT NULL,
  LastName   VARCHAR(10) NOT NULL,
  ZipCode    INTEGER  NOT NULL,
  City       VARCHAR(20) NOT NULL,
  State      VARCHAR(3) NOT NULL,
  
  email      VARCHAR(26) NOT NULL,
  dept_id        INTEGER NOT NULL,
  username        VARCHAR(11) NOT NULL,
  password        VARCHAR(11) NOT NULL,
  FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);

#Department table
CREATE TABLE Department(
   dept_id INTEGER  NOT NULL PRIMARY KEY 
  ,dept_name          VARCHAR(17) NOT NULL
);

#Course Table:

CREATE TABLE Course(
   course_id     INTEGER  NOT NULL PRIMARY KEY 
  ,course_name          VARCHAR(26) NOT NULL
  ,dept_id INTEGER  NOT NULL
  ,FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);


#Class Table:

CREATE TABLE Class(
   class_id   INTEGER  NOT NULL PRIMARY KEY 
  ,user_id INTEGER  NOT NULL
  ,course_id  INTEGER  NOT NULL
  ,capacity   INTEGER  NOT NULL
  ,FOREIGN KEY (user_id) REFERENCES USER(user_id)
  ,FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

#GRADE TABLE:

CREATE TABLE Grade(
session_id INTEGER NOT NULL PRIMARY KEY
,grade INTEGER NOT NULL
,user_id INTEGER  NOT NULL
 ,class_id   INTEGER  NOT NULL
 ,FOREIGN KEY (user_id) REFERENCES USER(user_id)
 ,FOREIGN KEY (class_id) REFERENCES Class(class_id)
);

#Admin TABLE:

CREATE TABLE Admin(
admin_id INTEGER NOT NULL PRIMARY KEY
,admin_name VARCHAR(26) NOT NULL
,admin_password VARCHAR(26) NOT NULL
);






select *from class;

