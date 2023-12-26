INSERT INTO SURVEY (TITLE, DESCRIPTION)
VALUES ('My first survey', 'This is my first survey description');
INSERT INTO SURVEY (TITLE, DESCRIPTION)
VALUES ('My second survey', 'This is my second survey description');

INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (1, 'Most Popular Cloud Platform Today', 'AWS,Azure,GCP,Oracle cloud', 'AWS');
INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (1, 'Fastest Growing Cloud Platform', 'AWS,Azure,GCP,Oracle cloud', 'GCP');
INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (1, 'Most Popular DevOps Tool', 'Kubernetes,Docker,Terraform,Azure DevOps', 'Kubernetes');

INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (2, 'Most Popular programming language', 'Java,C++,Python,JavaScript', 'JavaScript');
INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (2, 'Best programming language for Machine Learning', 'Java,GoLang,Python,JavaScript', 'Python');
INSERT INTO QUESTION (SURVEY_ID, NAME, OPTIONS, CORRECT_ANSWER)
VALUES (2, 'Most Popular UI framework', 'Angular,React', 'React');

