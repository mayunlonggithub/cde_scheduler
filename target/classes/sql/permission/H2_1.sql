-- 权限相关表
create table t_user(id int not null,account  varchar(30) not null,name varchar(50) not null,password varchar(80) not null,status varchar(15) not null,create_time DATETIME ,modify_time DATETIME ,primary key (id));
create table t_role(id  INT not null,name varchar(30) not null,description varchar(100),create_time DATETIME ,modify_time DATETIME,primary key (id));
create table t_menu(id int not null,name varchar(50) not null,icon varchar(50),url varchar(200),code varchar(18) not null,hide char(1),primary key (id));
create table r_role_menu(role_id INT not null,menu_id INT not null,primary key (role_id, menu_id))
create table r_user_role(user_id int not null,role_id int not null,primary key (user_id, role_id));
INSERT INTO t_id_generator(id_key,id_value) VALUES ('user',100)
INSERT INTO t_id_generator(id_key,id_value) VALUES ('role',100)
INSERT INTO t_id_generator(id_key,id_value) VALUES ('menu',1000)
-- 添加一个超级管理员角色和用户
INSERT INTO t_role(id,name,description) VALUES(0,'root','超级管理员')
INSERT INTO t_user(id,account,name,password,status) VALUES(1,'root','超级管理员','6ee93a8e0dba1d3e7d78be0403474aa82df9fdf68f13e748bb5bb539040f8b03efeb176ebb104e54','active')
INSERT INTO r_user_role(user_id,role_id) VALUES(1,0)
-- 添加权限部分的菜单项
INSERT INTO t_menu (id, name, icon, url, code, hide) VALUES (1, '系统管理', 'Hui-iconfont-system', null, '900', 'N')
INSERT INTO t_menu (id, name, icon, url, code, hide) VALUES (2, '账号管理', null, 'manager/userManager', '900100', 'N')
INSERT INTO t_menu (id, name, icon, url, code, hide) VALUES (3, '角色管理', null, 'manager/roleManager', '900200', 'N')
INSERT INTO t_menu (id, name, icon, url, code, hide) VALUES (4, '系统日志', null, 'manager/logManager', '900400', 'N')
