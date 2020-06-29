DROP TABLE df_user_bulletins;
DROP TABLE df_town_bulletins;
DROP TABLE df_weapons;
DROP TABLE df_consumables;
DROP TABLE df_users_skills;
DROP TABLE df_characters_skills;
DROP TABLE df_characters_legacy;
DROP TABLE df_characters_stats;
DROP TABLE df_skills;
DROP TABLE df_characters_items;
DROP TABLE df_characters;
DROP TABLE df_towns_structures;
DROP TABLE df_towns;
DROP TABLE df_users;
DROP TABLE df_structure_costs;
DROP TABLE df_structure_requirements;
DROP TABLE df_structures;
DROP TABLE df_town_zone_items;
DROP TABLE df_town_zones;
DROP TABLE df_items;

CREATE TABLE df_users(
user_id NUMBER,
username VARCHAR2(50),
password VARCHAR2(70),
salt VARCHAR2(150),
email VARCHAR2(150),
PRIMARY KEY (user_id)
);

CREATE TABLE df_user_bulletins(
bulletin_id NUMBER,
user_id NUMBER,
content VARCHAR2(300),
posted_time DATE,
PRIMARY KEY (bulletin_id),
CONSTRAINT fk_bulletin_user FOREIGN KEY (user_id) REFERENCES df_users(user_id)
);

CREATE TABLE df_characters(
char_id NUMBER,
user_id NUMBER NOT NULL,
town_id NUMBER DEFAULT 0,
current_ap NUMBER DEFAULT 16,
max_ap NUMBER DEFAULT 16,
zone_id NUMBER,
name VARCHAR2(40) NOT NULL,
classification VARCHAR2(20),
PRIMARY KEY (char_id),
CONSTRAINT fk_user_char FOREIGN KEY (user_id) REFERENCES df_users(user_id)
);

CREATE TABLE df_skills(
skill_id NUMBER,
name VARCHAR2(50),
description VARCHAR2(500),
PRIMARY KEY (skill_id)
);

--Linking table to store unlocked skills by user
CREATE TABLE df_users_skills(
user_id NUMBER NOT NULL,
skill_id NUMBER NOT NULL,
PRIMARY KEY (user_id, skill_id),
CONSTRAINT fk_users_skills FOREIGN KEY (user_id) REFERENCES df_users(user_id),
CONSTRAINT fk_skills_users FOREIGN KEY (skill_id) REFERENCES df_skills(skill_id)
);

--Linking table to store active skills on characters
CREATE TABLE df_characters_skills(
char_id NUMBER NOT NULL,
skill_id NUMBER NOT NULL,
PRIMARY KEY (char_id, skill_id),
CONSTRAINT fk_characters_skills FOREIGN KEY (char_id) REFERENCES df_characters(char_id),
CONSTRAINT fk_skills_characters FOREIGN KEY (skill_id) REFERENCES df_skills(skill_id)
);

CREATE TABLE df_characters_stats(
char_id NUMBER NOT NULL,
construction_contributions NUMBER DEFAULT 0,
zeds_killed NUMBER DEFAULT 0,
distance_travelled NUMBER DEFAULT 0,
times_looted NUMBER DEFAULT 0,
successful_camps NUMBER DEFAULT 0,
day_of_death NUMBER DEFAULT null,
PRIMARY KEY (char_id),
CONSTRAINT fk_char_stats FOREIGN KEY (char_id) REFERENCES df_characters(char_id)
);

CREATE TABLE df_characters_legacy(
char_id NUMBER NOT NULL,
construction_contributions NUMBER DEFAULT 0,
zeds_killed NUMBER DEFAULT 0,
distance_travelled NUMBER DEFAULT 0,
times_looted NUMBER DEFAULT 0,
successful_camps NUMBER DEFAULT 0,
lvl NUMBER DEFAULT 1,
current_xp NUMBER DEFAULT 0,
deaths NUMBER DEFAULT 0,
PRIMARY KEY (char_id),
CONSTRAINT fk_char_legacy FOREIGN KEY (char_id) REFERENCES df_characters(char_id)
);

CREATE TABLE df_items(
item_id NUMBER NOT NULL,
name VARCHAR2(30) NOT NULL,
description VARCHAR2(500),
mass NUMBER(3) DEFAULT 2,
rarity VARCHAR2(30),
category VARCHAR2(25),
PRIMARY KEY (item_id)
);

CREATE TABLE df_weapons(
item_id NUMBER NOT NULL,
ammo_id NUMBER DEFAULT -1,
min_kills NUMBER DEFAULT 0,
max_kills NUMBER DEFAULT 1,
chance_of_injury NUMBER DEFAULT 50,
chance_of_break NUMBER DEFAULT 10,
item_on_break NUMBER DEFAULT 0,
ap_cost NUMBER DEFAULT 0,
chance_output NUMBER DEFAULT 0,
ammo_output NUMBER DEFAULT 0,
PRIMARY KEY (item_id),
CONSTRAINT item_weapon_fk FOREIGN KEY (item_id) REFERENCES df_items(item_id)
);

CREATE TABLE df_consumables(
item_id NUMBER NOT NULL,
ap_gain NUMBER DEFAULT 100,
consume_type VARCHAR2(30),
health_gain NUMBER DEFAULT 0,
PRIMARY KEY (item_id)
);

CREATE TABLE df_structures(
structure_id NUMBER NOT NULL,
name VARCHAR2(100),
description VARCHAR2(4000),
defence NUMBER DEFAULT 0,
ap_cost NUMBER DEFAULT 10,
levels NUMBER DEFAULT 1,
PRIMARY KEY (structure_id)
);

CREATE TABLE df_structure_requirements(
structure_id NUMBER NOT NULL,
required_id NUMBER NOT NULL,
required_level NUMBER DEFAULT 1,
PRIMARY KEY (structure_id, required_id),
CONSTRAINT struc_fk FOREIGN KEY (structure_id) REFERENCES df_structures(structure_id),
CONSTRAINT required_struc_fk FOREIGN KEY (required_id) REFERENCES df_structures(structure_id)
);

CREATE TABLE df_structure_costs(
structure_id NUMBER NOT NULL,
item_id NUMBER NOT NULL,
item_quantity NUMBER DEFAULT 1,
PRIMARY KEY (structure_id, item_id)
);

CREATE TABLE df_towns(
town_id NUMBER NOT NULL,
name VARCHAR2(300),
max_chars NUMBER DEFAULT 10,
horde_size NUMBER,
defence NUMBER,
day_number NUMBER DEFAULT 0,
map_size NUMBER DEFAULT 11,
game_mode VARCHAR2(20) DEFAULT 'Regular',
status VARCHAR2(20) DEFAULT 'New',
PRIMARY KEY (town_id)
);

CREATE TABLE df_town_bulletins(
bulletin_id NUMBER,
town_id NUMBER,
content VARCHAR2(4000),
posted_time DATE,
PRIMARY KEY(bulletin_id)
);

CREATE TABLE df_towns_structures(
town_id NUMBER NOT NULL,
structure_id NUMBER NOT NULL,
current_level NUMBER DEFAULT 1,
current_ap NUMBER DEFAULT 0,
PRIMARY KEY (town_id, structure_id)
);

CREATE TABLE df_town_zones(
zone_id NUMBER NOT NULL PRIMARY KEY,
town_id NUMBER NOT NULL,
x NUMBER NOT NULL,
y NUMBER NOT NULL,
lootability NUMBER DEFAULT 10,
zeds NUMBER DEFAULT 0,
special_zone VARCHAR2(50) DEFAULT NULL
);

CREATE TABLE df_town_zone_items(
stack_id NUMBER,
zone_id NUMBER NOT NULL,
item_id NUMBER NOT NULL,
quantity NUMBER DEFAULT 1,
PRIMARY KEY (stack_id),
CONSTRAINT fk_town_items FOREIGN KEY (item_id) REFERENCES df_items(item_id)
);

CREATE TABLE df_characters_items(
stack_id NUMBER,
char_id NUMBER NOT NULL,
item_id NUMBER NOT NULL,
quantity NUMBER DEFAULT 1,
PRIMARY KEY (stack_id),
CONSTRAINT fk_char_items FOREIGN KEY (item_id) REFERENCES df_items(item_id)
);

------------------------------
------------------------------
----------SEQUENCES-----------
------------------------------
------------------------------

DROP SEQUENCE df_user_id_seq;
DROP SEQUENCE df_char_id_seq;
DROP SEQUENCE df_item_id_seq;
DROP SEQUENCE df_structure_id_seq;
DROP SEQUENCE df_town_id_seq;
DROP SEQUENCE df_user_bulletin_seq;
DROP SEQUENCE df_town_bulletin_seq;
DROP SEQUENCE df_zone_seq;
DROP SEQUENCE df_stack_seq;

CREATE SEQUENCE df_user_id_seq 
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_char_id_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_item_id_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_structure_id_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_town_id_seq
START WITH 2
INCREMENT BY 1;

CREATE SEQUENCE df_user_bulletin_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_town_bulletin_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_zone_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE df_stack_seq
START WITH 1
INCREMENT BY 1;

------------------------------
------------------------------
----------SEED DATA-----------
------------ITEMS-------------
------------------------------

INSERT INTO df_users (user_id, username, password, salt, email)
VALUES (0, 'UnitTestUser', 'Test', 'salt', 'email@email.com');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Water Ration', 
'Water is essential to keeping your survivors hydrated and alive. Be sure to ration if you want to survive.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (df_item_id_seq.CURRVAL, 100, 'Drink', 0);

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Bits of Food', 
'Scraps of food are better than nothing. They won''t stop your stomach from rumbling, but they will keep you alive.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (df_item_id_seq.CURRVAL, 80, 'Eat', 0);

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Arrow', 
'Could be used with a bow.',
1, 'Epic', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Wood Board', 
'Sturdy wooden board. Could be useful for building.',
2, 'Common', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Sheet Metal', 
'Sheet of metal. Could be useful for building.',
3, 'Common', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Makeshift Spear', 
'A wooden stick with a sharp stone affixed to the end. Good for poking zeds.',
2, 'Epic', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 0,          1,          1,          15,             15,                 0,           1,         0,            0 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Stone', 
'Just a solid stone.',
2, 'Scrap', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Bow', 
'A basic longbow. Can use arrows to kill zombies from a safe distance.',
2, 'Epic', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 3,          0,          1,          1,             5,                 0,           1,         80,            3 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Rope', 
'A length of rope. Could be used to tie things together.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Battery', 
'A basic battery.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Brick', 
'A basic building material.',
2, 'Common', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Cloth', 
'Piece of cloth material.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Grenade', 
'A questionably old explosive.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 0,          5,          20,          25,             100,                 0,           0,         0,            0 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Pistol', 
'A basic handgun. Uses small bullets.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 17,          1,          3,          1,             1,                 0,           0,         0,            0 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Slingshot', 
'Used to propel rocks at unsuspecting targets.',
2, 'Rare', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 16,          -2,          1,          10,             10,                 0,           1,         100,            16 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Rock', 
'A rock. Can be launched with a slingshot.',
1, 'UnCommon', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Small Bullet', 
'Ammunition for small arms.',
1, 'Rare', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Sharp Stick', 
'Just a stick that has been sharpened at one end.',
2, 'Rare', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 0,          0,          1,          20,             25,                 0,           1,         0,            0 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Assault Rifle', 
'Machine gun. Uses medium ammo that has been packed into an ammo magazine.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (df_item_id_seq.CURRVAL, 22,          12,          30,          1,             1,                 0,           0,         100,            21 );

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Bag of Sand', 
'Justa bag of dirt.',
2, 'Scrap', 'Resource');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Empty Mag', 
'Empty ammo magazine, can be filled with medium bullets.',
2, 'Rare', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Full Mag', 
'Full ammo magazine. Empties after each use.',
3, 'Legendary', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Medium Bullet', 
'Ammunition for medium arms.',
1, 'Rare', 'Ammo');

INSERT INTO df_items (item_id, name, description, mass, rarity, category) 
VALUES (df_item_id_seq.NEXTVAL, 'Carrot', 
'A fresh picked carrot. Restores full AP if you have not eaten yet today.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (df_item_id_seq.CURRVAL, 100, 'Eat', 0);

commit;