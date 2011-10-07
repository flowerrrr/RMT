
    create table Club (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Comment (
        id bigint not null auto_increment,
        comment varchar(255),
        author_id bigint,
        response_id bigint,
        primary key (id)
    );

    create table Event (
        eventType varchar(31) not null,
        id bigint not null auto_increment,
        comment varchar(255),
        date datetime not null,
        summary varchar(40) not null,
        time time not null,
        kickOff datetime,
        club_id bigint not null,
        team_id bigint not null,
        venue_id bigint,
        jersey_id bigint,
        opponent_id bigint,
        primary key (id)
    );

    create table Invitation (
        id bigint not null auto_increment,
        body varchar(255) not null,
        date datetime not null,
        subject varchar(255) not null,
        event_id bigint,
        primary key (id)
    );

    create table Jersey (
        id bigint not null auto_increment,
        shirt varchar(255),
        shorts varchar(255),
        socks varchar(255),
        team_id bigint,
        primary key (id)
    );

    create table Manager (
        team_id bigint not null,
        user_id bigint not null
    );

    create table Opponent (
        id bigint not null auto_increment,
        name varchar(40) not null,
        url varchar(255),
        primary key (id)
    );

    create table Player (
        id bigint not null auto_increment,
        optional bit,
        team_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Response (
        id bigint not null auto_increment,
        date datetime not null,
        guestName varchar(255),
        status integer not null,
        event_id bigint,
        player_id bigint,
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

    alter table Comment 
        add index FK9BDE863FCA54B59A (author_id), 
        add constraint FK9BDE863FCA54B59A 
        foreign key (author_id) 
        references Users (id);

    alter table Comment 
        add index FK9BDE863F69EFA79A (response_id), 
        add constraint FK9BDE863F69EFA79A 
        foreign key (response_id) 
        references Response (id);

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
        add index FK403827ACA4D421A (opponent_id), 
        add constraint FK403827ACA4D421A 
        foreign key (opponent_id) 
        references Opponent (id);

    alter table Event 
        add index FK403827A1C96621A (team_id), 
        add constraint FK403827A1C96621A 
        foreign key (team_id) 
        references Team (id);

    alter table Event 
        add index FK403827A2B4253A (jersey_id), 
        add constraint FK403827A2B4253A 
        foreign key (jersey_id) 
        references Jersey (id);

    alter table Invitation 
        add index FKBE1153B9D1F985A6 (event_id), 
        add constraint FKBE1153B9D1F985A6 
        foreign key (event_id) 
        references Event (id);

    alter table Jersey 
        add index FK840B72901C96621A (team_id), 
        add constraint FK840B72901C96621A 
        foreign key (team_id) 
        references Team (id);

    alter table Manager 
        add index FK9501A78D699BC35A (user_id), 
        add constraint FK9501A78D699BC35A 
        foreign key (user_id) 
        references Users (id);

    alter table Manager 
        add index FK9501A78D1C96621A (team_id), 
        add constraint FK9501A78D1C96621A 
        foreign key (team_id) 
        references Team (id);

    alter table Player 
        add index FK8EA38701699BC35A (user_id), 
        add constraint FK8EA38701699BC35A 
        foreign key (user_id) 
        references Users (id);

    alter table Player 
        add index FK8EA387011C96621A (team_id), 
        add constraint FK8EA387011C96621A 
        foreign key (team_id) 
        references Team (id);

    alter table Response 
        add index FKEF917861D1F985A6 (event_id), 
        add constraint FKEF917861D1F985A6 
        foreign key (event_id) 
        references Event (id);

    alter table Response 
        add index FKEF917861E4FF039A (player_id), 
        add constraint FKEF917861E4FF039A 
        foreign key (player_id) 
        references Player (id);

    alter table Role 
        add index FK26F496699BC35A (user_id), 
        add constraint FK26F496699BC35A 
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

    alter table Venue 
        add index FK4EB7A4FA64B977A (club_id), 
        add constraint FK4EB7A4FA64B977A 
        foreign key (club_id) 
        references Club (id);
