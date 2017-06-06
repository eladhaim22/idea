Insert into Users (login,password_hash,first_name,last_name,email,image_url,activated,lang_key,created_by,last_modified_by) Values 
('system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','',1,'en','system','system'),
('admin','$2a$10$6V.c45nzFHdGfk9m1FJ0teYRx7TXtg04hXe0BLAKIafy/m.RyEQYu','Admin','Admin','admin@localhost','',1,'en','admin','admin')

Insert into authorities values ('ROLE_ADMIN'), ('ROLE_USER'),('ROLE_REFERRE')

Insert into users_authorities values ((select id from Users where login = 'admin'),'ROLE_ADMIN'),
((select id from Users where login = 'admin'),'ROLE_USER')