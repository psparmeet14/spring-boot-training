CREATE TABLE SURVEY
(
    ID INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    TITLE VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL
);

CREATE TABLE QUESTION
(
    ID INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    SURVEY_ID INT NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    OPTIONS VARCHAR(255) NOT NULL,
    CORRECT_ANSWER VARCHAR(255) NOT NULL,
    CONSTRAINT FK_SURVEY_QUESTION FOREIGN KEY (SURVEY_ID) REFERENCES SURVEY(ID)
);

CREATE TABLE APP_USER
(
    ID INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    EMAIL VARCHAR(255),
    PASSWORD VARCHAR(255),
    ROLE VARCHAR(255)
);
