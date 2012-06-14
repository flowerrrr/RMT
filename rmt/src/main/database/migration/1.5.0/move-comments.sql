; moves comments from invitation table to new comment table
; tricky thing to give correct createDate for comment records

insert into comment (invitation_id, author_id, createDate, text)
        select id, user_id, date, comment from invitation where comment is not null;


insert into comment (invitation_id, author_id, createDate, text)
        select i.id, createdby_id, dateadd('mi', 1, date), managercomment
  from invitation i left join event e on (i.event_id = e.id)
  where managercomment is not null;
