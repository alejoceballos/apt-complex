-- ----------------------------------------------------------------------------
-- USER
-- ----------------------------------------------------------------------------

CREATE USER 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
GRANT DELETE, EXECUTE, INSERT, LOCK TABLES, SELECT, SHOW VIEW, UPDATE ON aptcomplexdb.* TO 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
GRANT DELETE, EXECUTE, INSERT, LOCK TABLES, SELECT, SHOW VIEW, UPDATE ON aptcomplexdbtest.* TO 'aptcomplexusr'@'%' IDENTIFIED BY 'aptcomplexpwd';
FLUSH PRIVILEGES;
