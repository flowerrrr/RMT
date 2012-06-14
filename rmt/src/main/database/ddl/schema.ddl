
    create table Activity (
        id bigint not null auto_increment,
        objectStatus integer,
        date datetime,
        message blob,
        club_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Club (
        id bigint not null auto_increment,
        objectStatus integer,
        lat double precision,
        lng double precision,
        name varchar(255),
        primary key (id)
    );

    create table Event (
        eventType varchar(31) not null,
        id bigint not null auto_increment,
        objectStatus integer,
        canceled bit,
        comment varchar(255),
        dateTime datetime,
        invitationSent bit,
        summary varchar(255),
        kickoff time,
        surfaceList varchar(255),
        club_id bigint,
        createdBy_id bigint,
        team_id bigint,
        venue_id bigint,
        uniform_id bigint,
        opponent_id bigint,
        primary key (id)
    );

    create table Invitation (
        id bigint not null auto_increment,
        objectStatus integer,
        comment varchar(255),
        date datetime,
        guestName varchar(255),
        invitationSent bit,
        invitationSentDate datetime,
        managerComment varchar(255),
        noResponseReminderSent bit,
        noResponseReminderSentDate datetime,
        status varchar(255),
        unsureReminderSent bit,
        unsureReminderSentDate datetime,
        event_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Opponent (
        id bigint not null auto_increment,
        objectStatus integer,
        name varchar(255),
        url varchar(255),
        club_id bigint,
        primary key (id),
        unique (name, club_id)
    );

    create table Player (
        id bigint not null auto_increment,
        objectStatus integer,
        notification bit,
        optional bit,
        retired bit,
        team_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Property (
        id bigint not null auto_increment,
        objectStatus integer,
        key varchar(255),
        value varchar(255),
        club_id bigint,
        primary key (id)
    );

    create table Role (
        id bigint not null auto_increment,
        objectStatus integer,
        authority varchar(255),
        user_id bigint,
        primary key (id),
        unique (user_id, authority)
    );

    create table Team (
        id bigint not null auto_increment,
        objectStatus integer,
        name varchar(255),
        url varchar(255),
        club_id bigint,
        primary key (id),
        unique (name, club_id)
    );

    create table Uniform (
        id bigint not null auto_increment,
        objectStatus integer,
        name varchar(255),
        shirt varchar(255),
        shorts varchar(255),
        socks varchar(255),
        team_id bigint,
        primary key (id)
    );

    create table Users (
        id bigint not null auto_increment,
        objectStatus integer,
        username varchar(255),
        enabled bit,
        password varchar(255),
        fullname varchar(255),
        initialPassword varchar(255),
        invitationSent bit,
        phoneNumber varchar(255),
        secondEmail varchar(255),
        status varchar(255),
        club_id bigint,
        primary key (id),
        unique (username),
        unique (fullname, club_id)
    );

    create table Venue (
        id bigint not null auto_increment,
        objectStatus integer,
        address varchar(255),
        lat double precision,
        lng double precision,
        name varchar(255),
        club_id bigint,
        primary key (id)
    );

    create index ix_club on Activity (club_id);

    create index ix_objectstatus on Activity (objectStatus);

    create index ix_date on Activity (date);

    alter table Activity 
        add index FKA126572FCFF3A135 (club_id), 
        add constraint FKA126572FCFF3A135 
        foreign key (club_id) 
        references Club (id);

    alter table Activity 
        add index FKA126572F9343CD15 (user_id), 
        add constraint FKA126572F9343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_objectstatus on Club (objectStatus);

    create index ix_club on Event (club_id);

    create index ix_invitationsent on Event (invitationSent);

    create index ix_objectstatus on Event (objectStatus);

    create index ix_datetime on Event (dateTime);

    create index ix_canceled on Event (canceled);

    create index ix_team on Event (team_id);

    alter table Event 
        add index FK403827AB1F8A41F (uniform_id), 
        add constraint FK403827AB1F8A41F 
        foreign key (uniform_id) 
        references Uniform (id);

    alter table Event 
        add index FK403827A139AE321 (createdBy_id), 
        add constraint FK403827A139AE321 
        foreign key (createdBy_id) 
        references Users (id);

    alter table Event 
        add index FK403827A68101B7F (venue_id), 
        add constraint FK403827A68101B7F 
        foreign key (venue_id) 
        references Venue (id);

    alter table Event 
        add index FK403827ACFF3A135 (club_id), 
        add constraint FK403827ACFF3A135 
        foreign key (club_id) 
        references Club (id);

    alter table Event 
        add index FK403827A6913F655 (opponent_id), 
        add constraint FK403827A6913F655 
        foreign key (opponent_id) 
        references Opponent (id);

    alter table Event 
        add index FK403827A463E6BD5 (team_id), 
        add constraint FK403827A463E6BD5 
        foreign key (team_id) 
        references Team (id);

    create index ix_invitationsent on Invitation (invitationSent);

    create index ix_noresponseremindersentdate on Invitation (noResponseReminderSentDate);

    create index ix_invitationsentdate on Invitation (invitationSentDate);

    create index ix_objectstatus on Invitation (objectStatus);

    create index ix_event on Invitation (event_id);

    create index ix_user on Invitation (user_id);

    create index ix_status on Invitation (status);

    create index ix_noresponseremindersent on Invitation (noResponseReminderSent);

    create index ix_unsureremindersentdate on Invitation (unsureReminderSentDate);

    create index ix_unsureremindersent on Invitation (unsureReminderSent);

    create index ix_date on Invitation (date);

    alter table Invitation 
        add index FKBE1153B9C761238B (event_id), 
        add constraint FKBE1153B9C761238B 
        foreign key (event_id) 
        references Event (id);

    alter table Invitation 
        add index FKBE1153B99343CD15 (user_id), 
        add constraint FKBE1153B99343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_club on Opponent (club_id);

    create index ix_objectstatus on Opponent (objectStatus);

    alter table Opponent 
        add index FKF8A5457DCFF3A135 (club_id), 
        add constraint FKF8A5457DCFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_retired on Player (retired);

    create index ix_notification on Player (notification);

    create index ix_objectstatus on Player (objectStatus);

    create index ix_user on Player (user_id);

    create index ix_team on Player (team_id);

    create index ix_optional on Player (optional);

    alter table Player 
        add index FK8EA387019343CD15 (user_id), 
        add constraint FK8EA387019343CD15 
        foreign key (user_id) 
        references Users (id);

    alter table Player 
        add index FK8EA38701463E6BD5 (team_id), 
        add constraint FK8EA38701463E6BD5 
        foreign key (team_id) 
        references Team (id);

    create index ix_club on Property (club_id);

    create index ix_objectstatus on Property (objectStatus);

    alter table Property 
        add index FKC8A841F5CFF3A135 (club_id), 
        add constraint FKC8A841F5CFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_objectstatus on Role (objectStatus);

    create index ix_user on Role (user_id);

    create index ix_authority on Role (authority);

    alter table Role 
        add index FK26F4969343CD15 (user_id), 
        add constraint FK26F4969343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_club on Team (club_id);

    create index ix_objectstatus on Team (objectStatus);

    alter table Team 
        add index FK27B67DCFF3A135 (club_id), 
        add constraint FK27B67DCFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_objectstatus on Uniform (objectStatus);

    create index ix_team on Uniform (team_id);

    alter table Uniform 
        add index FK521E7194463E6BD5 (team_id), 
        add constraint FK521E7194463E6BD5 
        foreign key (team_id) 
        references Team (id);

    create index ix_club on Users (club_id);

    create index ix_objectstatus on Users (objectStatus);

    create index ix_status on Users (status);

    create index ix_enabled on Users (enabled);

    create index ix_fullname on Users (fullname);

    create index ix_username on Users (username);

    alter table Users 
        add index FK4E39DE8CFF3A135 (club_id), 
        add constraint FK4E39DE8CFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_club on Venue (club_id);

    create index ix_objectstatus on Venue (objectStatus);

    alter table Venue 
        add index FK4EB7A4FCFF3A135 (club_id), 
        add constraint FK4EB7A4FCFF3A135 
        foreign key (club_id) 
        references Club (id);
