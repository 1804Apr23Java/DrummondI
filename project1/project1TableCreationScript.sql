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

COMMIT;

--///////////////////////////////////////////////////////
CREATE TABLE requests(
    r_id INTEGER PRIMARY KEY,
    e_id INTEGER NOT NULL,
    r_date DATE DEFAULT CURRENT_DATE,
    r_amt NUMBER(14,2),
    r_img BLOB,
    r_stat INTEGER DEFAULT 1,
    CONSTRAINT fk_e_id FOREIGN KEY (e_id) REFERENCES all_emp(e_id),
    CONSTRAINT r_stat_valid CHECK (r_stat = 1 OR r_stat = 2 OR r_stat = 3)
);
/

CREATE SEQUENCE requests_seq
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;
    
CREATE OR REPLACE TRIGGER requests_pk
    BEFORE INSERT ON requests
    FOR EACH ROW
BEGIN
    SELECT requests_seq.NEXTVAL INTO :NEW.r_id FROM DUAL;
END;

COMMIT;