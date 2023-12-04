-- use with care https://bcrypt-generator.com/
-- alice user has password alice
-- bob user has password bob
INSERT INTO BLOG_USER (ID, USERNAME, PASSWORD_HASH) VALUES (1, 'alice', '$2a$12$2mEZ1CqdXbkhQt2Sa4uGzO.CcvbRsQQfItoHWOzBDK.BeI0I7c.di');
INSERT INTO BLOG_USER (ID, USERNAME, PASSWORD_HASH) VALUES (2, 'bob', '$2a$12$IC/gYzWsbIXf3Et9vF9EyuPsA6w0AsDWy7m0mwA5ud0dRfGO4BFAy');
