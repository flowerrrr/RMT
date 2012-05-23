; initialize fields after introducing new field.
update invitation set NORESPONSEREMINDERSENT = false;
update invitation set UNSUREREMINDERSENT = false;

update invitation set invitationsentdate = subdate(now(), interval 5 day)  where invitationsent = true;