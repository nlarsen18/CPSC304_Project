drop table branch cascade constraints;

CREATE TABLE branch (
	branch_id integer not null PRIMARY KEY,
	branch_name varchar2(20) not null,
	branch_addr varchar2(50),
	branch_city varchar2(20) not null,
	branch_phone integer 
);

INSERT INTO branch(branch_id, branch_name, branch_addr, branch_city, branch_phone) VALUES (1, 'ABC', '123 Charming Ave', 'Vancouver', 6041234567);
INSERT INTO branch(branch_id, branch_name, branch_addr, branch_city, branch_phone) VALUES (2, 'DEF', '123 Coco Ave', 'Vancouver', 6044567890);