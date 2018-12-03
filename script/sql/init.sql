-- docker run --name postgresql -itd --restart always --publish 5432:5432 sameersbn/postgresql:9.6
-- docker exec -it postgresql sudo -u postgres psql

DROP SCHEMA IF EXISTS doka2 CASCADE;
CREATE SCHEMA doka2;
SET SEARCH_PATH = doka2;

BEGIN TRANSACTION ;

CREATE TABLE item (
  item_id SERIAL NOT NULL PRIMARY KEY,
  item_type VARCHAR(32) NOT NULL,
  title VARCHAR(128) NOT NULL,
  modifier INT NOT NULL
);

CREATE TABLE hero (
  hero_id BIGINT NOT NULL PRIMARY KEY,
  first_name VARCHAR(1024),
  last_name VARCHAR(1024),
  user_name VARCHAR(1042),
  hero_name VARCHAR(1042),
  hero_race VARCHAR(32),
  language_code VARCHAR(256),
  creation_time TIMESTAMP NOT NULL,
  last_request TIMESTAMP NOT NULL,
  weapon_id INT REFERENCES item (item_id),
  tool_id INT REFERENCES item(item_id),
  experience INT NOT NULL
);
CREATE INDEX usr_weapon_id_idx ON item(item_id);
CREATE INDEX usr_tool_id_idx ON item(item_id);

CREATE TABLE searching (
  searching_id SERIAL NOT NULL PRIMARY KEY,
  search_type VARCHAR(32) NOT NULL,
  hero_id BIGINT REFERENCES hero(hero_id),
  is_active BOOLEAN NOT NULL,
  start_time TIMESTAMP NOT NULL ,
  finish_time TIMESTAMP NOT NULL
);

CREATE INDEX searching_hero_id_idx ON searching(hero_id);

CREATE TABLE build (
  build_id SERIAL NOT NULL PRIMARY KEY,
  build_size_type VARCHAR(32) NOT NULL,
  creator_id BIGINT NOT NULL REFERENCES hero(hero_id),
  start_time TIMESTAMP NOT NULL,
  finish_time TIMESTAMP NOT NULL,
  build_active BOOLEAN NOT NULL,
  size INT,
  gained_exp INT,
  destroyer_id BIGINT REFERENCES hero(hero_id),
  start_destroy_time TIMESTAMP,
  finish_destroy_time TIMESTAMP,
  gained_destroy_exp INT,
  destroy_active BOOLEAN NOT NULL
);

CREATE TABLE item_title (
   item_title_id SERIAL NOT NULL PRIMARY KEY,
   title VARCHAR(32) NOT NULL,
   item_type VARCHAR(32) NOT NULL,
   title_type VARCHAR(32) NOT NULL,
   sex VARCHAR(32) NOT NULL,
   min_level INT NOT NULL,
   max_level INT NOT NULL
);

CREATE INDEX build_creator_id_idx ON hero(hero_id);
CREATE INDEX build_destroyer_id_idx ON hero(hero_id);

COMMIT ;

DROP ROLE IF EXISTS doka2;
CREATE ROLE doka2 PASSWORD 'doka2' LOGIN;

GRANT ALL PRIVILEGES ON SCHEMA doka2 TO doka2;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA doka2 TO doka2;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA doka2 TO doka2;
ALTER ROLE doka2 SET search_path = doka2;

