CREATE TABLE Feed(
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(128),
     last_updated DATETIME
);

CREATE TABLE Feed_Source(
   id INT AUTO_INCREMENT PRIMARY KEY,
   feed_id int,
   name VARCHAR(128),
   source VARCHAR(2048),
   last_updated DATETIME,
   update_status INT
);

CREATE TABLE Feed_Item(
    id INT AUTO_INCREMENT PRIMARY KEY,
    feed_source_id int,
    link VARCHAR(2048),
    title LONGTEXT,
    description LONGTEXT,
    pub_date DATETIME,
    guid VARCHAR(2048),
    UNIQUE(guid)
);