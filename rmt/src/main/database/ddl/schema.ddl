
    create table Activity (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        date datetime,
        message blob,
        club_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table BArticle (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        heading varchar(80),
        text longtext,
        club_id bigint,
        author_id bigint,
        event_id bigint,
        primary key (id)
    );

    create table BComment (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        text longtext,
        article_id bigint,
        author_id bigint,
        primary key (id)
    );

    create table CalItem (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        allDay bit,
        autoDecline bit,
        endDateTime datetime,
        startDateTime datetime,
        summary varchar(255),
        type varchar(255),
        user_id bigint,
        primary key (id)
    );

    create table Club (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        lat double precision,
        lng double precision,
        name varchar(255),
        primary key (id)
    );

    create table Comment (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        text varchar(255),
        author_id bigint,
        invitation_id bigint,
        primary key (id)
    );

    create table Event (
        eventType varchar(31) not null,
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        canceled bit,
        comment varchar(255),
        dateTime datetime,
        dateTimeEnd datetime,
        invitationSent bit,
        summary varchar(255),
        kickoff time,
        surfaceList varchar(255),
        club_id bigint,
        createdBy_id bigint,
        opponent_id bigint,
        team_id bigint,
        venue_id bigint,
        uniform_id bigint,
        primary key (id)
    );

    create table EventTeam (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        rank integer,
        event_id bigint,
        primary key (id)
    );

    create table EventTeamPlayer (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        sort_order integer,
        eventTeam_id bigint,
        invitation_id bigint,
        primary key (id),
        unique (eventTeam_id, invitation_id)
    );

    create table Invitation (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        date datetime,
        guestName varchar(255),
        invitationSent bit,
        invitationSentDate datetime,
        noResponseReminderSent bit,
        noResponseReminderSentDate datetime,
        status varchar(255),
        unsureReminderSent bit,
        unsureReminderSentDate datetime,
        event_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Lineup (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        published bit not null,
        event_id bigint,
        primary key (id)
    );

    create table LineupItem (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        absLeft bigint,
        absTop bigint,
        relLeft double precision,
        relTop double precision,
        invitation_id bigint,
        lineup_id bigint,
        primary key (id)
    );

    create table Opponent (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        url varchar(255),
        club_id bigint,
        primary key (id),
        unique (name, club_id)
    );

    create table Player (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        notification bit,
        optional bit,
        retired bit,
        team_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Property (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        value varchar(255),
        club_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table Role (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        authority varchar(255),
        user_id bigint,
        primary key (id),
        unique (user_id, authority)
    );

    create table Team (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        url varchar(255),
        club_id bigint,
        primary key (id),
        unique (name, club_id)
    );

    create table Uniform (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        name varchar(255),
        shirt varchar(255),
        shorts varchar(255),
        socks varchar(255),
        team_id bigint,
        primary key (id)
    );

    create table Users (
        id bigint not null auto_increment,
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        username varchar(255),
        enabled bit,
        password varchar(255),
        fullname varchar(255),
        initialPassword varchar(255),
        invitationSent bit,
        lastLogin datetime,
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
        createDate datetime,
        objectStatus integer,
        updateDate datetime,
        address varchar(255),
        lat double precision,
        lng double precision,
        name varchar(255),
        club_id bigint,
        primary key (id)
    );

    create index ix_club on Activity (club_id);

    create index ix_createDate on Activity (createDate);

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

    create index ix_club on BArticle (club_id);

    create index ix_createDate on BArticle (createDate);

    create index ix_objectstatus on BArticle (objectStatus);

    create index ix_event on BArticle (event_id);

    create index ix_author on BArticle (author_id);

    alter table BArticle 
        add index FKFF9EF654C761238B (event_id), 
        add constraint FKFF9EF654C761238B 
        foreign key (event_id) 
        references Event (id);

    alter table BArticle 
        add index FKFF9EF654CFF3A135 (club_id), 
        add constraint FKFF9EF654CFF3A135 
        foreign key (club_id) 
        references Club (id);

    alter table BArticle 
        add index FKFF9EF654F3FCBF55 (author_id), 
        add constraint FKFF9EF654F3FCBF55 
        foreign key (author_id) 
        references Users (id);

    create index ix_barticle on BComment (article_id);

    create index ix_createDate on BComment (createDate);

    create index ix_objectstatus on BComment (objectStatus);

    create index ix_author on BComment (author_id);

    alter table BComment 
        add index FK63EC17BDB47968F3 (article_id), 
        add constraint FK63EC17BDB47968F3 
        foreign key (article_id) 
        references BArticle (id);

    alter table BComment 
        add index FK63EC17BDF3FCBF55 (author_id), 
        add constraint FK63EC17BDF3FCBF55 
        foreign key (author_id) 
        references Users (id);

    create index ix_type on CalItem (type);

    create index ix_createDate on CalItem (createDate);

    create index ix_objectstatus on CalItem (objectStatus);

    create index ix_user on CalItem (user_id);

    create index ix_startDate on CalItem (startDateTime);

    create index ix_endDate on CalItem (endDateTime);

    alter table CalItem 
        add index FK83DC6FE19343CD15 (user_id), 
        add constraint FK83DC6FE19343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_createDate on Club (createDate);

    create index ix_objectstatus on Club (objectStatus);

    create index ix_createDate on Comment (createDate);

    create index ix_objectstatus on Comment (objectStatus);

    create index ix_invitation on Comment (invitation_id);

    create index ix_author on Comment (author_id);

    alter table Comment 
        add index FK9BDE863FF05EFA15 (invitation_id), 
        add constraint FK9BDE863FF05EFA15 
        foreign key (invitation_id) 
        references Invitation (id);

    alter table Comment 
        add index FK9BDE863FF3FCBF55 (author_id), 
        add constraint FK9BDE863FF3FCBF55 
        foreign key (author_id) 
        references Users (id);

    create index ix_club on Event (club_id);

    create index ix_invitationsent on Event (invitationSent);

    create index ix_createDate on Event (createDate);

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

    create index ix_createDate on EventTeam (createDate);

    create index ix_objectstatus on EventTeam (objectStatus);

    create index ix_event on EventTeam (event_id);

    alter table EventTeam 
        add index FK79516BF7C761238B (event_id), 
        add constraint FK79516BF7C761238B 
        foreign key (event_id) 
        references Event (id);

    create index ix_createDate on EventTeamPlayer (createDate);

    create index ix_objectstatus on EventTeamPlayer (objectStatus);

    create index ix_invitation on EventTeamPlayer (invitation_id);

    create index ix_team on EventTeamPlayer (eventTeam_id);

    alter table EventTeamPlayer 
        add index FK4C1BDDB8AC1BA81F (eventTeam_id), 
        add constraint FK4C1BDDB8AC1BA81F 
        foreign key (eventTeam_id) 
        references EventTeam (id);

    alter table EventTeamPlayer 
        add index FK4C1BDDB8F05EFA15 (invitation_id), 
        add constraint FK4C1BDDB8F05EFA15 
        foreign key (invitation_id) 
        references Invitation (id);

    create index ix_invitationsent on Invitation (invitationSent);

    create index ix_noresponseremindersentdate on Invitation (noResponseReminderSentDate);

    create index ix_createDate on Invitation (createDate);

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

    create index ix_createDate on Lineup (createDate);

    create index ix_objectstatus on Lineup (objectStatus);

    create index ix_event on Lineup (event_id);

    alter table Lineup 
        add index FK87AB7DAFC761238B (event_id), 
        add constraint FK87AB7DAFC761238B 
        foreign key (event_id) 
        references Event (id);

    create index ix_createDate on LineupItem (createDate);

    create index ix_objectstatus on LineupItem (objectStatus);

    create index ix_lineup on LineupItem (lineup_id);

    alter table LineupItem 
        add index FKA569FD62438EE3D5 (lineup_id), 
        add constraint FKA569FD62438EE3D5 
        foreign key (lineup_id) 
        references Lineup (id);

    alter table LineupItem 
        add index FKA569FD62F05EFA15 (invitation_id), 
        add constraint FKA569FD62F05EFA15 
        foreign key (invitation_id) 
        references Invitation (id);

    create index ix_club on Opponent (club_id);

    create index ix_createDate on Opponent (createDate);

    create index ix_objectstatus on Opponent (objectStatus);

    alter table Opponent 
        add index FKF8A5457DCFF3A135 (club_id), 
        add constraint FKF8A5457DCFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_retired on Player (retired);

    create index ix_notification on Player (notification);

    create index ix_createDate on Player (createDate);

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

    create index ix_createDate on Property (createDate);

    create index ix_objectstatus on Property (objectStatus);

    create index ix_user on Property (user_id);

    alter table Property 
        add index FKC8A841F5CFF3A135 (club_id), 
        add constraint FKC8A841F5CFF3A135 
        foreign key (club_id) 
        references Club (id);

    alter table Property 
        add index FKC8A841F59343CD15 (user_id), 
        add constraint FKC8A841F59343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_createDate on Role (createDate);

    create index ix_objectstatus on Role (objectStatus);

    create index ix_user on Role (user_id);

    create index ix_authority on Role (authority);

    alter table Role 
        add index FK26F4969343CD15 (user_id), 
        add constraint FK26F4969343CD15 
        foreign key (user_id) 
        references Users (id);

    create index ix_club on Team (club_id);

    create index ix_createDate on Team (createDate);

    create index ix_objectstatus on Team (objectStatus);

    alter table Team 
        add index FK27B67DCFF3A135 (club_id), 
        add constraint FK27B67DCFF3A135 
        foreign key (club_id) 
        references Club (id);

    create index ix_createDate on Uniform (createDate);

    create index ix_objectstatus on Uniform (objectStatus);

    create index ix_team on Uniform (team_id);

    alter table Uniform 
        add index FK521E7194463E6BD5 (team_id), 
        add constraint FK521E7194463E6BD5 
        foreign key (team_id) 
        references Team (id);

    create index ix_club on Users (club_id);

    create index ix_createDate on Users (createDate);

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

    create index ix_createDate on Venue (createDate);

    create index ix_objectstatus on Venue (objectStatus);

    alter table Venue 
        add index FK4EB7A4FCFF3A135 (club_id), 
        add constraint FK4EB7A4FCFF3A135 
        foreign key (club_id) 
        references Club (id);
