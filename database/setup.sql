CREATE TABLE planets(
    id                  BIGINT(20) NOT NULL AUTO_INCREMENT,
    name                VARCHAR(50) NOT NULL,
    climate             VARCHAR(50) NOT NULL,
    terrain             VARCHAR(50) NOT NULL,
    appearance_films    INT(2) NOT NULL,
    create_date         DATETIME NOT NULL,
    PRIMARY KEY(id)
);