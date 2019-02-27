insert into traeger (name)
values('Feuerwehr');

insert into benutzer (einkaeufer, mail, password, name, traeger_id)
values(false, 'test@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'Superuser', (select max(id) from traeger));

insert into tokenconfig (issuer, expiration, clock, secret)
values ('wbs', 86400, 300, 'secret');

commit;