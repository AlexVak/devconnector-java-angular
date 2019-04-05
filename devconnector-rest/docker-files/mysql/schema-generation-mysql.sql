create table users (id bigint not null auto_increment, created_at datetime not null, updated_at datetime not null, avatar varchar(255), email varchar(40), name varchar(40), password varchar(100), primary key (id)) engine=InnoDB;
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);
create table users (id bigint not null auto_increment, created_at datetime not null, updated_at datetime not null, avatar varchar(255), email varchar(40), name varchar(40), password varchar(100), primary key (id)) engine=InnoDB
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
