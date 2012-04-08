
    create table Club (
        id bigint not null auto_increment,
        name varchar(50) not null,
        primary key (id)
    );

    create table Event (
        eventType varchar(31) not null,
        id bigint not null auto_increment,
        comment varchar(255),
        date datetime not null,
        summary varchar(50) not null,
        time time not null,
        kickOff time,
        club_id bigint not null,
        team_id bigint not null,
        venue_id bigint,
        uniform_id bigint,
        opponent_id bigint,
        primary key (id)
    );

    create table Invitation (
        id bigint not null auto_increment,
        comment varchar(255),
        date datetime,
        guestName varchar(50),
        managerComment varchar(255),
        status varchar(255) not null,
        event_id bigint not null,
        user_id bigint,
        primary key (id)
    );

    create table Uniform (
        id bigint not null auto_increment,
        shirt varchar(50) not null,
        shorts varchar(50) not null,
        socks varchar(50) not null,
        team_id bigint,
        primary key (id)
    );

    create table Opponent (
        id bigint not null auto_increment,
        name varchar(50) not null,
        url varchar(255),
        primary key (id)
    );

    create table Player (
        id bigint not null auto_increment,
        notification bit not null,
        optional bit not null,
        retired bit not null,
        team_id bigint,
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
        name varchar(50) not null,
        url varchar(255),
        club_id bigint not null,
        primary key (id),
        unique (name, club_id)
    );

    create table Users (
        id bigint not null auto_increment,
        username varchar(80) not null,
        enabled bit not null,
        password varchar(50) not null,
        fullname varchar(50) not null,
        initialPassword varchar(50),
        invitationSent bit,
        status varchar(255) not null,
        club_id bigint not null,
        primary key (id),
        unique (username),
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

    create index ix_club on Event (club_id);

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
        add index FK403827A2B4253A (uniform_id),
        add constraint FK403827A2B4253A 
        foreign key (uniform_id)
        references Uniform (id);

    create index ix_event on Invitation (event_id);

    create index ix_user on Invitation (user_id);

    create index ix_status on Invitation (status);

    alter table Invitation 
        add index FKBE1153B9D1F985A6 (event_id), 
        add constraint FKBE1153B9D1F985A6 
        foreign key (event_id) 
        references Event (id);

    alter table Invitation 
        add index FKBE1153B9699BC35A (user_id), 
        add constraint FKBE1153B9699BC35A 
        foreign key (user_id) 
        references Users (id);

    create index ix_team on Uniform (team_id);

    alter table Uniform
        add index FK840B72901C96621A (team_id), 
        add constraint FK840B72901C96621A 
        foreign key (team_id) 
        references Team (id);

    create index ix_retired on Player (retired);

    create index ix_notification on Player (notification);

    create index ix_user on Player (user_id);

    create index ix_team on Player (team_id);

    create index ix_optional on Player (optional);

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

    create index ix_user on Role (user_id);

    alter table Role 
        add index FK26F496699BC35A (user_id), 
        add constraint FK26F496699BC35A 
        foreign key (user_id) 
        references Users (id);

    create index ix_club on Team (club_id);

    alter table Team 
        add index FK27B67DA64B977A (club_id), 
        add constraint FK27B67DA64B977A 
        foreign key (club_id) 
        references Club (id);

    create index ix_club on Users (club_id);

    create index ix_status on Users (status);

    create index ix_enabled on Users (enabled);

    create index ix_username on Users (username);

    alter table Users 
        add index FK4E39DE8A64B977A (club_id), 
        add constraint FK4E39DE8A64B977A 
        foreign key (club_id) 
        references Club (id);

    create index ix_club on Venue (club_id);

    alter table Venue 
        add index FK4EB7A4FA64B977A (club_id), 
        add constraint FK4EB7A4FA64B977A 
        foreign key (club_id) 
        references Club (id);
