CREATE TABLE Feed(
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(128)
);

CREATE TABLE Feed_Source(
   id INT AUTO_INCREMENT PRIMARY KEY,
   feed_id int,
   name VARCHAR(128),
   source VARCHAR(2000)
)