CREATE TABLE SURVEY
(
    ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    TITLE VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE QUESTION
(
    ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    SURVEY_ID INT NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    OPTIONS VARCHAR(255) NOT NULL,
    CORRECT_ANSWER VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK_SURVEY_QUESTION FOREIGN KEY (SURVEY_ID) REFERENCES SURVEY(ID)
);

CREATE TABLE APP_USER
(
    ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    EMAIL VARCHAR(255),
    PASSWORD VARCHAR(255),
    ROLE VARCHAR(255),
    PRIMARY KEY (ID)
);