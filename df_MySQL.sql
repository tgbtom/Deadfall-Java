CREATE TABLE df_users
(user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50),
password VARCHAR(70),
salt VARCHAR(150),
email VARCHAR(150)
);

CREATE TABLE df_user_bulletins(
bulletin_id INTEGER AUTO_INCREMENT PRIMARY KEY,
user_id INTEGER NOT NULL,
content VARCHAR(750),
posted_time TIMESTAMP
);

CREATE TABLE df_characters(
char_id INTEGER AUTO_INCREMENT PRIMARY KEY,
user_id INTEGER NOT NULL,
town_id INTEGER DEFAULT 0,
current_ap INTEGER DEFAULT 16,
max_ap INTEGER DEFAULT 16,
zone_id INTEGER,
name VARCHAR(40) NOT NULL,
classification VARCHAR(20)
);

CREATE TABLE df_skills(
skill_id INTEGER AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50),
description VARCHAR(500)
);

CREATE TABLE df_users_skills(
user_id INTEGER PRIMARY KEY,
skill_id INTEGER NOT NULL
);

CREATE TABLE df_characters_skills(
char_id INTEGER PRIMARY KEY,
skill_id INTEGER NOT NULL
);

CREATE TABLE df_characters_stats(
char_id INTEGER PRIMARY KEY,
construction_contributions INTEGER DEFAULT 0,
zeds_killed INTEGER DEFAULT 0,
distance_travelled INTEGER DEFAULT 0,
times_looted INTEGER DEFAULT 0,
successful_camps INTEGER DEFAULT 0,
day_of_death INTEGER DEFAULT null
);

CREATE TABLE df_characters_legacy(
char_id INTEGER NOT NULL,
construction_contributions INTEGER DEFAULT 0,
zeds_killed INTEGER DEFAULT 0,
distance_travelled INTEGER DEFAULT 0,
times_looted INTEGER DEFAULT 0,
successful_camps INTEGER DEFAULT 0,
lvl INTEGER DEFAULT 1,
current_xp INTEGER DEFAULT 0,
deaths INTEGER DEFAULT 0
);

CREATE TABLE df_items(
item_id INTEGER AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL,
description VARCHAR(500),
mass INTEGER(3) DEFAULT 2,
rarity VARCHAR(30),
category VARCHAR(25)
);

CREATE TABLE df_weapons(
item_id INTEGER PRIMARY KEY,
ammo_id INTEGER DEFAULT -1,
min_kills INTEGER DEFAULT 0,
max_kills INTEGER DEFAULT 1,
chance_of_injury INTEGER DEFAULT 50,
chance_of_break INTEGER DEFAULT 10,
item_on_break INTEGER DEFAULT 0,
ap_cost INTEGER DEFAULT 0,
chance_output INTEGER DEFAULT 0,
ammo_output INTEGER DEFAULT 0
);

CREATE TABLE df_consumables(
item_id INTEGER PRIMARY KEY,
ap_gain INTEGER DEFAULT 100,
consume_type VARCHAR(30),
health_gain INTEGER DEFAULT 0
);

CREATE TABLE df_structures(
structure_id INTEGER AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
category VARCHAR(40),
description VARCHAR(4000),
defence INTEGER DEFAULT 0,
ap_cost INTEGER DEFAULT 10,
levels INTEGER DEFAULT 1
);

CREATE TABLE df_structure_requirements(
structure_id INTEGER PRIMARY KEY,
required_id INTEGER NOT NULL,
required_level INTEGER DEFAULT 1
);

CREATE TABLE df_structure_costs(
structure_id INTEGER PRIMARY KEY,
item_id INTEGER NOT NULL,
item_quantity INTEGER DEFAULT 1
);

CREATE TABLE df_towns(
town_id INTEGER AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(300),
max_chars INTEGER DEFAULT 10,
horde_size INTEGER,
defence INTEGER,
day_number INTEGER DEFAULT 0,
map_size INTEGER DEFAULT 11,
game_mode VARCHAR(20) DEFAULT 'Regular',
status VARCHAR(20) DEFAULT 'New'
);

CREATE TABLE df_town_bulletins(
bulletin_id INTEGER AUTO_INCREMENT PRIMARY KEY,
town_id INTEGER NOT NULL,
content VARCHAR(4000),
posted_time TIMESTAMP
);

CREATE TABLE df_towns_structures(
town_id INTEGER PRIMARY KEY,
structure_id INTEGER NOT NULL,
current_level INTEGER DEFAULT 1,
current_ap INTEGER DEFAULT 0
);

CREATE TABLE df_town_zones(
zone_id INTEGER AUTO_INCREMENT PRIMARY KEY,
town_id INTEGER NOT NULL,
x INTEGER NOT NULL,
y INTEGER NOT NULL,
lootability INTEGER DEFAULT 10,
zeds INTEGER DEFAULT 0,
special_zone VARCHAR(50) DEFAULT NULL
);

CREATE TABLE df_town_zone_items(
stack_id INTEGER AUTO_INCREMENT PRIMARY KEY,
zone_id INTEGER NOT NULL,
item_id INTEGER NOT NULL,
quantity INTEGER DEFAULT 1
);

CREATE TABLE df_characters_items(
stack_id INTEGER AUTO_INCREMENT PRIMARY KEY,
char_id INTEGER NOT NULL,
item_id INTEGER NOT NULL,
quantity INTEGER DEFAULT 1
);

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Water Ration', 
'Water is essential to keeping your survivors hydrated and alive. Be sure to ration if you want to survive.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (1, 100, 'Drink', 0);

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Bits of Food', 
'Scraps of food are better than nothing. They won''t stop your stomach from rumbling, but they will keep you alive.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (2, 80, 'Eat', 0);

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Arrow', 
'Could be used with a bow.',
1, 'Epic', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Wood Board', 
'Sturdy wooden board. Could be useful for building.',
2, 'Common', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Sheet Metal', 
'Sheet of metal. Could be useful for building.',
3, 'Common', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Makeshift Spear', 
'A wooden stick with a sharp stone affixed to the end. Good for poking zeds.',
2, 'Epic', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (6, 0,          1,          1,          15,             15,                 0,           1,         0,            0 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Stone', 
'Just a solid stone.',
2, 'Scrap', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Bow', 
'A basic longbow. Can use arrows to kill zombies from a safe distance.',
2, 'Epic', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (8, 3,          0,          1,          1,             5,                 0,           1,         80,            3 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Rope', 
'A length of rope. Could be used to tie things together.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Battery', 
'A basic battery.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Brick', 
'A basic building material.',
2, 'Common', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Cloth', 
'Piece of cloth material.',
2, 'UnCommon', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Grenade', 
'A questionably old explosive.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (13, 0,          5,          20,          25,             100,                 0,           0,         0,            0 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Pistol', 
'A basic handgun. Uses small bullets.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (14, 17,          1,          3,          1,             1,                 0,           0,         0,            0 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Slingshot', 
'Used to propel rocks at unsuspecting targets.',
2, 'Rare', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (15, 16,          -2,          1,          10,             10,                 0,           1,         100,            16 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Rock', 
'A rock. Can be launched with a slingshot.',
1, 'UnCommon', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Small Bullet', 
'Ammunition for small arms.',
1, 'Rare', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Sharp Stick', 
'Just a stick that has been sharpened at one end.',
2, 'Rare', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (18, 0,          0,          1,          20,             25,                 0,           1,         0,            0 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Assault Rifle', 
'Machine gun. Uses medium ammo that has been packed into an ammo magazine.',
2, 'Legendary', 'Weapon');

INSERT INTO df_weapons(item_id, ammo_id, min_kills, max_kills, chance_of_injury, chance_of_break, item_on_break, ap_cost, chance_output, ammo_output)
VALUES (19, 22,          12,          30,          1,             1,                 0,           0,         100,            21 );

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Bag of Sand', 
'Justa bag of dirt.',
2, 'Scrap', 'Resource');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Empty Mag', 
'Empty ammo magazine, can be filled with medium bullets.',
2, 'Rare', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Full Mag', 
'Full ammo magazine. Empties after each use.',
3, 'Legendary', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Medium Bullet', 
'Ammunition for medium arms.',
1, 'Rare', 'Ammo');

INSERT INTO df_items (name, description, mass, rarity, category) 
VALUES ('Carrot', 
'A fresh picked carrot. Restores full AP if you have not eaten yet today.',
2, 'Rare', 'Consumable');

INSERT INTO df_consumables(item_id, ap_gain, consume_type, health_gain)
VALUES (24, 100, 'Eat', 0);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Perimeter Fence', 'Defence', 'Build a fence establishing a perimeter for the settlement. Will Keep some zeds out.', 
50, 45, 5);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Wooden Wall', 'Defence', 'Improve the perimeter around the settlement with a large wooden wall.', 
100, 80, 10);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Inner Wall', 'Defence', 'Create a secondary wall, just in case there is a breach in the main wall.', 
75, 90, 10);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Trenches', 'Defence', 'Dig out trenches between the outer and inner wall. Great place to set traps.', 
40, 100, 15);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Spike Pits', 'Defence', 'Set up sharp spikes in some areas of the trenches to impale falling zeds.', 
65, 35, 5);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Wooden Support', 'Defence', 'Add structural support the walls.', 
30, 40, 5);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Metal Patching', 'Defence', 'Reinforce the outer wall with metal.', 
115, 85, 5);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Sentry Tower', 'Defence', 'Each level will build a sentry tower on a different section of the wall. Used as a lookout.', 
85, 120, 4);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('MG Nest', 'Defence', 'Set up machine gun nests near the perimeter.', 
250, 50, 2);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Water Reserve', 'Supply', 'Establish a holding area for all clean water reserves in the settlement. Once built; provides 2-4 water rations per day.', 
0, 50, 1);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Vegetable Garden', 'Supply', 'A small plot to grow vegetables. Generates food overnight. Grows 1-4 food per night.', 
0, 85, 1);

INSERT INTO df_structures(name, category, description, defence, ap_cost, levels)
VALUES ('Fabrikator Workshop', 'Production', 'Allows the conversion of basic resources at a 3:1 Ratio.', 
0, 45, 1);
