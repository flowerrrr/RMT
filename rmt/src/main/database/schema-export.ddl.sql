create table Authorities (id bigint not null auto_increment, authority varchar(255), username varchar(255), primary key (id), unique (username, authority));
create table MyTeamBE (id bigint not null auto_increment, name varchar(255), url varchar(255), primary key (id));
create table Users (id bigint not null auto_increment, email varchar(255), enabled bit, password varchar(255), username varchar(255) unique, primary key (id), unique (username));
alter table Authorities add index FK8E78BB01C9E35D5E (username), add constraint FK8E78BB01C9E35D5E foreign key (username) references Users (username);
