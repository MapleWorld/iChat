-- Tested with MySQL v5.1.73

create table user (
	id int AUTO_INCREMENT PRIMARY KEY,
	username varchar(40) UNIQUE NOT NULL,
	password varchar(100) NOT NULL,
	created_at datetime NOT NULL,
	banned tinyint default 0 NOT NULL,
	admin tinyint default 0 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table category (
	id int AUTO_INCREMENT PRIMARY KEY,
	name varchar(100) UNIQUE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table topic (
	id int AUTO_INCREMENT PRIMARY KEY,
	cat_id int NOT NULL,
	name varchar(100) UNIQUE NOT NULL,
	user_id int NOT NULL,
	created_at datetime NOT NULL,
	FOREIGN KEY (cat_id) references category(id),
	FOREIGN KEY (user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table thread (
	id int AUTO_INCREMENT PRIMARY KEY,
	cat_id int NOT NULL,
	title varchar(250) NOT NULL,
	body varchar(6000) NOT NULL,
	user_id int NOT NULL,
	created_at datetime NOT NULL,
	updated_at datetime NOT NULL,
	FOREIGN KEY (cat_id) references category(id),
	FOREIGN KEY (user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table reply (
	id int AUTO_INCREMENT PRIMARY KEY,
	user_id int NOT NULL,
	thread_id int NOT NULL,
	body varchar(6000) NOT NULL,
	created_at datetime NOT NULL,
	updated_at datetime NOT NULL,
	FOREIGN KEY (user_id) references user(id),
	FOREIGN KEY (thread_id)references thread(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table thread_topics (
	id int AUTO_INCREMENT UNIQUE,
	thread_id int,
	topic_id int,
	PRIMARY KEY(thread_id, topic_id),
	FOREIGN KEY (thread_id) references thread(id),
	FOREIGN KEY (topic_id) references topic(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table sessions (
	sessionid varchar(100) PRIMARY KEY,
	userid int NOT NULL,
	expires datetime NOT NULL,
	FOREIGN KEY (userid) references user (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table subscription (
	user_id int,
	topic_id int,
	PRIMARY KEY (user_id, topic_id),
	FOREIGN KEY (user_id) references user (id),
	FOREIGN KEY (topic_id) references topic (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table contact (
	owner_id int,
	contact_id int,
	PRIMARY KEY (owner_id, contact_id),
	FOREIGN KEY (owner_id) REFERENCES user (id),
	FOREIGN KEY (contact_id) REFERENCES user (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table message (
	id int AUTO_INCREMENT,
	userid_from int,
	userid_to int,
	send_time datetime,
	subject varchar(255),
	body varchar(6000),
	unread tinyint NOT NULL DEFAULT 1,
	inbox tinyint NOT NULL DEFAULT 1,
	sentbox tinyint NOT NULL DEFAULT 1,
	PRIMARY KEY (id),
	FOREIGN KEY (userid_from) references user (id),
	FOREIGN KEY (userid_to) references user (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
