-- view for spring security

CREATE OR REPLACE VIEW authorities AS
  select r.authority AS authority,u.username AS username
  from (role r join users u) where (r.user_id = u.id);



