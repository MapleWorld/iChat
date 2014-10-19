-- Tested with MySQL v5.1.73

create table user (
	id int AUTO_INCREMENT PRIMARY KEY,
	username varchar(40) UNIQUE,
	password varchar(100),
	created_at datetime,
	banned tinyint default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table category (
	id int AUTO_INCREMENT PRIMARY KEY,
	name varchar(100) UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table topic (
	id int AUTO_INCREMENT PRIMARY KEY,
	cat_id int references category(id),
	name varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table thread (
	id int AUTO_INCREMENT PRIMARY KEY,
	cat_id int references category(id),
	title varchar(250),
	body varchar(6000),
	created_at datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table reply (
	id int AUTO_INCREMENT PRIMARY KEY,
	user_id int references user(id),
	thread_id int references thread(id),
	body varchar(6000)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table thread_topics (
	thread_id int references thread(id),
	topic_id int references topic(id),
	PRIMARY KEY(thread_id, topic_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table sessions (
	sessionid varchar(100) PRIMARY KEY,
	userid int,
	datetime expires
)ENGINE=InnoDB DEFAULT CHARSET=utf8;