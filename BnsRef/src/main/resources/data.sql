create table if not exists type_relation (
    code varchar(50) not null primary key,
    description varchar(255) not null
);
INSERT INTO type_relation (code, description)
SELECT 'ONE_TO_MANY', 'One to Many Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'ONE_TO_MANY');

INSERT INTO type_relation (code, description)
SELECT 'ONE_TO_ONE', 'One to One Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'ONE_TO_ONE');

INSERT INTO type_relation (code, description)
SELECT 'MANY_TO_ONE', 'Many to One Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'MANY_TO_ONE');


create table if not exists language (
    code_language varchar(50) not null primary key,
    language_name varchar(255) not null
);
insert into language (code_language, language_name)
SELECT 'EN', 'English'
    WHERE NOT EXISTS (SELECT 1 FROM language WHERE code_language = 'EN');
insert into language (code_language, language_name)
SELECT 'FR', 'French'
    WHERE NOT EXISTS (SELECT 1 FROM language WHERE code_language = 'FR');
insert into language (code_language, language_name)
SELECT 'AR', 'Arabic'
    WHERE NOT EXISTS (SELECT 1 FROM language WHERE code_language = 'AR');