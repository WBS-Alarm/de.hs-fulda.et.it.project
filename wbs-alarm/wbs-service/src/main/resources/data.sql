insert into traeger (name)
values('Feuerwehr');

insert into zielorte(name, auto, aktiv, traeger_id)
values('Wäscherei', true, true, (select id from traeger where name = 'Feuerwehr'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Wareneingang', true, true, (select id from traeger where name = 'Feuerwehr'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Lager', true, true, (select id from traeger where name = 'Feuerwehr'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Aussonderung', true, true, (select id from traeger where name = 'Feuerwehr'));

insert into kategorien(name, aktiv, traeger_id)
values('Polo-Hemd', true, (select id from traeger where name = 'Feuerwehr'));

insert into groessen(name, aktiv, kategorie_id)
values('XXL', true, (select k.id from kategorien k where k.name = 'Polo-Hemd' and k.traeger_id = (select id from traeger where name = 'Feuerwehr')));


insert into traeger (name)
values('Helsa');

insert into zielorte(name, auto, aktiv, traeger_id)
values('Wäscherei', true, true, (select id from traeger where name = 'Helsa'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Wareneingang', true, true, (select id from traeger where name = 'Helsa'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Lager', true, true, (select id from traeger where name = 'Helsa'));
insert into zielorte(name, auto, aktiv, traeger_id)
values('Aussonderung', true, true, (select id from traeger where name = 'Helsa'));

insert into kategorien(name, aktiv, traeger_id)
values('Polo-Hemd', true, (select id from traeger where name = 'Helsa'));

insert into groessen(name, aktiv, kategorie_id)
values('XXL', true, (select k.id from kategorien k where k.name = 'Polo-Hemd' and k.traeger_id = (select id from traeger where name = 'Helsa')));



insert into benutzer (aktiv, einkaeufer, mail, password, name, traeger_id)
values(true, false, 'superuser@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'Superuser', (select id from traeger where name = 'Feuerwehr'));

insert into benutzer (aktiv, einkaeufer, mail, password, name, traeger_id)
values(true, false, 'leser@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'Leser', (select id from traeger where name = 'Feuerwehr'));

insert into benutzer (aktiv, einkaeufer, mail, password, name, traeger_id)
values(true, false, 'helsaUser@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'HelsaUser', (select id from traeger where name = 'Helsa'));

insert into benutzer (aktiv, einkaeufer, mail, password, name, traeger_id)
values(true, false, 'helsaUser@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'ForDelete', (select id from traeger where name = 'Helsa'));

insert into benutzer (aktiv, einkaeufer, mail, password, name, traeger_id)
values(true, false, 'helsaUser@domain.de', '$2a$12$7w0m4I2kjbQgM0hp0erh4OXqEoBxeYqNoaLUZKpiaISqnIcw91t3i', 'ForGrantTest', (select id from traeger where name = 'Feuerwehr'));

-- Konfiguration der Token
insert into tokenconfig (issuer, expiration, clock, secret)
values ('wbs', 86400, 300, 'secret');

-- Festlegen der Rollengruppen für die Anwendung
insert into authority (id, code, bezeichnung, aktiv)
values(1, 'ADMIN', 'Trägeradministration', false);
insert into authority (id, code, bezeichnung, aktiv)
values(2, 'CONTROL','Administration', true);
insert into authority (id, code, bezeichnung, aktiv)
values(3, 'MANAGE','Kleiderverwaltung', true);
insert into authority (id, code, bezeichnung, aktiv)
values(4, 'WRITE','Kleiderbuchung', true);
insert into authority (id, code, bezeichnung, aktiv)
values(5, 'READ','Lesender Zugriff', true);

insert into granted_authority (user_id, authority_id)
values ((select id from benutzer where name = 'Superuser'), 1);

insert into granted_authority (user_id, authority_id)
values ((select id from benutzer where name = 'Leser'), 5);

insert into granted_authority (user_id, authority_id)
values ((select id from benutzer where name = 'HelsaUser'), 2);

commit;