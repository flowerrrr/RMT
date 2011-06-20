
    create table Authorities (
        id bigint not null auto_increment,
        authority varchar(255),
        username varchar(255),
        primary key (id),
        unique (username, authority)
    );

    create table Club (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Team (
        id bigint not null auto_increment,
        name varchar(255),
        url varchar(255),
        club_id bigint,
        primary key (id),
        unique (name, club_id)
    );

    create table Users (
        id bigint not null auto_increment,
        email varchar(255),
        enabled bit,
        password varchar(255),
        username varchar(255) unique,
        club_id bigint,
        primary key (id),
        unique (username)
    );

    alter table Authorities 
        add index FK8E78BB01C9E35D5E (username), 
        add constraint FK8E78BB01C9E35D5E 
        foreign key (username) 
        references Users (username);

    alter table Team 
        add index FK27B67DA64B977A (club_id), 
        add constraint FK27B67DA64B977A 
        foreign key (club_id) 
        references Club (id);

    alter table Users 
        add index FK4E39DE8A64B977A (club_id), 
        add constraint FK4E39DE8A64B977A 
        foreign key (club_id) 
        references Club (id);
