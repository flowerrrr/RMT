
    create table Club (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Role (
        id bigint not null auto_increment,
        authority varchar(255) not null,
        user_id bigint,
        primary key (id),
        unique (user_id, authority)
    );

    create table Team (
        id bigint not null auto_increment,
        name varchar(40) not null,
        url varchar(255),
        club_id bigint not null,
        primary key (id),
        unique (name, club_id)
    );

    create table Users (
        id bigint not null auto_increment,
        username varchar(255) not null unique,
        enabled bit not null,
        fullname varchar(255) not null,
        password varchar(255) not null,
        club_id bigint not null,
        primary key (id)
    );

    alter table Role 
        add index FK26F496D0F4C297 (user_id), 
        add constraint FK26F496D0F4C297 
        foreign key (user_id) 
        references Users (id);

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
