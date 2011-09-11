
    create table Club (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Event (
        eventType varchar(31) not null,
        id bigint not null auto_increment,
        allDay bit,
        comment varchar(255),
        date datetime,
        kickOff datetime,
        club_id bigint not null,
        team_id bigint,
        venue_id bigint,
        opponent_id bigint,
        primary key (id)
    );

    create table Invitation (
        id bigint not null auto_increment,
        event_id bigint,
        user_id bigint,
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

    create table Team_Player (
        teams_id bigint not null,
        players_id bigint not null,
        primary key (teams_id, players_id)
    );

    create table Users (
        id bigint not null auto_increment,
        username varchar(255) not null unique,
        enabled bit not null,
        fullname varchar(255) not null,
        password varchar(255),
        status integer,
        club_id bigint not null,
        primary key (id),
        unique (fullname, club_id)
    );

    create table Venue (
        id bigint not null auto_increment,
        address varchar(255),
        lat double precision,
        lng double precision,
        name varchar(80) not null,
        club_id bigint not null,
        primary key (id)
    );

    alter table Event 
        add index FK403827A5CB6EDDA (venue_id), 
        add constraint FK403827A5CB6EDDA 
        foreign key (venue_id) 
        references Venue (id);

    alter table Event 
        add index FK403827AA64B977A (club_id), 
        add constraint FK403827AA64B977A 
        foreign key (club_id) 
        references Club (id);

    alter table Event 
        add index FK403827A37C9631A (opponent_id), 
        add constraint FK403827A37C9631A 
        foreign key (opponent_id) 
        references Team (id);

    alter table Event 
        add index FK403827A1C96621A (team_id), 
        add constraint FK403827A1C96621A 
        foreign key (team_id) 
        references Team (id);

    alter table Invitation 
        add index FKBE1153B9E96D79FA (event_id), 
        add constraint FKBE1153B9E96D79FA 
        foreign key (event_id) 
        references Event (id);

    alter table Invitation 
        add index FKBE1153B9D0F4C297 (user_id), 
        add constraint FKBE1153B9D0F4C297 
        foreign key (user_id) 
        references Users (id);

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

    alter table Team_Player 
        add index FKD4E5F70356B3CF50 (players_id), 
        add constraint FKD4E5F70356B3CF50 
        foreign key (players_id) 
        references Users (id);

    alter table Team_Player 
        add index FKD4E5F703EF9B021 (teams_id), 
        add constraint FKD4E5F703EF9B021 
        foreign key (teams_id) 
        references Team (id);

    alter table Users 
        add index FK4E39DE8A64B977A (club_id), 
        add constraint FK4E39DE8A64B977A 
        foreign key (club_id) 
        references Club (id);

    alter table Venue 
        add index FK4EB7A4FA64B977A (club_id), 
        add constraint FK4EB7A4FA64B977A 
        foreign key (club_id) 
        references Club (id);
