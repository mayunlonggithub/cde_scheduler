-- ID生成表,所有业务表通用
CREATE TABLE t_id_generator(id_key VARCHAR2(30),id_value INT NOT NULL,primary key (id_key));