--DROP TABLE all_emp;
--DROP SEQUENCE inc;
--DROP TRIGGER tri;

CREATE TABLE all_emp(
    e_id INTEGER PRIMARY KEY,
    e_un VARCHAR2(50) UNIQUE NOT NULL,
    e_pw VARCHAR(50) NOT NULL,
    e_em VARCHAR(100),
    e_fn varchar(100),
    e_ln varchar(100)
);

CREATE SEQUENCE inc
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER tri
    BEFORE INSERT ON all_emp
    FOR EACH ROW
BEGIN
    SELECT inc.NEXTVAL INTO :NEW.e_id FROM DUAL;
END;

INSERT INTO all_emp (e_un, e_pw, e_em, e_fn, e_ln) VALUES ('Username', 'Password', 'Email', 'Firstname', 'Lastname');
COMMIT;