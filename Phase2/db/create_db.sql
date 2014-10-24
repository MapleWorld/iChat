-- Tested with MySQL v5.1.73

create table user (
	id int AUTO_INCREMENT PRIMARY KEY,
	username varchar(40) UNIQUE NOT NULL,
	password varchar(100) NOT NULL,
	created_at datetime NOT NULL,
	banned tinyint default 0 NOT NULL
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
	FOREIGN KEY (user_id) references user(id),
	FOREIGN KEY (thread_id)references thread(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table thread_topics (
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