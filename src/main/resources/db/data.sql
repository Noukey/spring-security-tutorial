INSERT INTO user (id, username, password, name, age) VALUES (1, 'noukey', '123456', '李四', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', '123456', '张三', 29);

INSERT INTO authority (id, name,url) VALUES (1, 'ROLE_USER','/users/**');
INSERT INTO authority (id, name,url) VALUES (2, 'ROLE_ADMIN','/admins/**');
INSERT INTO authority (id, name) VALUES (3, 'ROLE_USER_ADD');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 3);