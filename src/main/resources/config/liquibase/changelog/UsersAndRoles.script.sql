Insert into Users (login,password_hash,first_name,last_name,email,image_url,activated,lang_key,created_by,last_modified_by) Values 
('system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','',1,'en','system','system')

insert into authorities values ('ROLE_ADMIN'),('ROLE_USER'),('ROLE_REFERRE')