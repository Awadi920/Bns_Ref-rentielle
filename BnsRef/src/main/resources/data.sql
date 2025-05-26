INSERT INTO type_relation (code, description)
SELECT 'ONE_TO_MANY', 'One to Many Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'ONE_TO_MANY');

INSERT INTO type_relation (code, description)
SELECT 'ONE_TO_ONE', 'One to One Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'ONE_TO_ONE');

INSERT INTO type_relation (code, description)
SELECT 'MANY_TO_ONE', 'Many to One Relation'
    WHERE NOT EXISTS (SELECT 1 FROM type_relation WHERE code = 'MANY_TO_ONE');