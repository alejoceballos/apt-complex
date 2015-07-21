SET SQL_SAFE_UPDATES = 0;

-- ----------------------------------------------------------------------------
-- SCHEMA
-- ----------------------------------------------------------------------------

-- CREATE DATABASE IF NOT EXISTS `aptcomplexdb`;
-- USE `aptcomplexdb`;

CREATE DATABASE IF NOT EXISTS `aptcomplexdbtest`;
USE `aptcomplexdbtest`;

-- ----------------------------------------------------------------------------
-- USER
-- ----------------------------------------------------------------------------

-- CREATE USER 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
-- GRANT DELETE, EXECUTE, INSERT, LOCK TABLES, SELECT, SHOW VIEW, UPDATE ON aptcomplexdb.* TO 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
-- GRANT DELETE, EXECUTE, INSERT, LOCK TABLES, SELECT, SHOW VIEW, UPDATE ON aptcomplexdbtest.* TO 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
-- FLUSH PRIVILEGES;

-- ----------------------------------------------------------------------------
-- DROP TABLES IN THE RIGHT ORDER
-- ----------------------------------------------------------------------------

DROP TABLE IF EXISTS `residence`;
DROP TABLE IF EXISTS `apartment`;
DROP TABLE IF EXISTS `person`;

-- ----------------------------------------------------------------------------
-- TABLE - APARTMENT
-- ----------------------------------------------------------------------------

CREATE TABLE `apartment` (
  `id` INT NOT NULL,
  `number` VARCHAR(4) NOT NULL,
  `version` BIGINT NOT NULL DEFAULT 1,

  PRIMARY KEY (`id`),

  UNIQUE INDEX `apt_nmbr_uq` (`number` ASC)
);

-- ----------------------------------------------------------------------------
-- Insert data

INSERT INTO `apartment`
  (`id`, `number`)
VALUES
  (1, '101'), (2, '102'), (3, '103'),
  (4, '201'), (5, '202'), (6, '203'),
  (7, '301'), (8, '302'), (9, '303'),
  (10, '401'), (11, '402'), (12, '403'),
  (13, '501'), (14, '502'), (15, '503'),
  (16, '601'), (17, '602'), (18, '603'),
  (19, '701'), (20, '702'), (21, '703'),
  (22, '801'), (23, '802'), (24, '803'),
  (25, '901'), (26, '902'), (27, '903'),
  (28, '1001'), (29, '1002'), (30, '1003'),
  (31, '1101'), (32, '1102'), (33, '1103'),
  (34, '1201'), (35, '1202'), (36, '1203');

-- ----------------------------------------------------------------------------
-- TABLE - PERSON
-- ----------------------------------------------------------------------------

CREATE TABLE `person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL,
  `middle_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(50) NULL,
  `document` VARCHAR(11) NULL,
  `version` BIGINT NOT NULL DEFAULT 1,

  PRIMARY KEY (`id`)
);

-- ----------------------------------------------------------------------------
-- Insert data

INSERT INTO `person`
  (`id`, `first_name`, `middle_name`, `last_name`)
VALUES
  (1, 'John', '1', 'Doe'), (2, 'John', '2', 'Doe'), (3, 'John', '3', 'Doe'),
  (4, 'John', '4', 'Doe'), (5, 'John', '5', 'Doe'), (6, 'John', '6', 'Doe'),
  (7, 'John', '7', 'Doe'), (8, 'John', '8', 'Doe'), (9, 'John', '9', 'Doe'),
  (10, 'John', '10', 'Doe'), (11, 'John', '11', 'Doe'), (12, 'John', '12', 'Doe'),
  (13, 'John', '13', 'Doe'), (14, 'John', '14', 'Doe'), (15, 'John', '15', 'Doe'),
  (16, 'John', '16', 'Doe'), (17, 'John', '17', 'Doe'), (18, 'John', '18', 'Doe'),
  (19, 'John', '19', 'Doe'), (20, 'John', '20', 'Doe'), (21, 'John', '21', 'Doe'),
  (22, 'John', '22', 'Doe'), (23, 'Alejo', NULL, 'Ceballos'), (24, 'John', '24', 'Doe'),
  (25, 'John', '25', 'Doe'), (26, 'John', '26', 'Doe'), (27, 'John', '27', 'Doe'),
  (28, 'John', '28', 'Doe'), (29, 'John', '29', 'Doe'), (30, 'John', '30', 'Doe'),
  (31, 'John', '31', 'Doe'), (32, 'John', '32', 'Doe'), (33, 'John', '33', 'Doe'),
  (34, 'John', '34', 'Doe'), (35, 'John', '35', 'Doe'), (36, 'John', '36', 'Doe');

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - RESIDENCE, THE APARTMENT RESIDENT (PERSON)
-- ----------------------------------------------------------------------------

CREATE TABLE `residence` (
  `apartment_id` INT NOT NULL,
  `person_id` INT NOT NULL,

  PRIMARY KEY (`apartment_id`, `person_id`),

  CONSTRAINT `fk_rsdnc_aprtmnt`
	FOREIGN KEY (`apartment_id`)
		REFERENCES `apartment` (`id`),

  CONSTRAINT `fk_rsdnc_prsn`
	FOREIGN KEY (`person_id`)
		REFERENCES `person` (`id`)
);

-- ----------------------------------------------------------------------------
-- Insert data

INSERT INTO `residence`
  (`apartment_id`, `person_id`)
VALUES
  (1, 1), (2, 2), (3, 3),
  (4, 4), (5, 5), (6, 6),
  (7, 7), (8, 8), (9, 9),
  (10, 10), (11, 11), (12, 12),
  (13, 13), (14, 14), (15, 15),
  (16, 16), (17, 17), (18, 18),
  (19, 19), (20, 20), (21, 21),
  (22, 22), (23, 23), (24, 24),
  (25, 25), (26, 26), (27, 27),
  (28, 28), (29, 29), (30, 30),
  (31, 31), (32, 32), (33, 33),
  (34, 34), (35, 35), (36, 36);

COMMIT;