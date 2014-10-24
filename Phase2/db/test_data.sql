-- Test data; run this after create_db.sql

insert into category (name) values ("Category1"), ("Category2"), ("Category3"), ("Category4");

insert into user (username, password, created_at) values ('myname1', '18bad84e97334e3484bc2e91bed52073a576ec7a146242941004a5b8325499fd', '2014-10-23 20:04:25'), ('myname2', 'fce0f210e184925f9bb30c51b77b89fda6eb008f62838c98559f7883efdef0fe', '2014-10-23 20:04:31');

insert into topic (cat_id, name, user_id, created_at) values (1, "Topic1", 1, '2014-10-23 20:37:49'), (1, 'Topic2', 2, '2014-10-23 20:37:49'), (2, 'Topic3', 1, '2014-10-23 20:37:49');
