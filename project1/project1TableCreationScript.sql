DROP TABLE all_emp;
DROP SEQUENCE inc;
DROP TRIGGER tri;

CREATE TABLE all_emp(
    e_id INTEGER PRIMARY KEY,
    e_un VARCHAR2(50) UNIQUE NOT NULL,
    e_pw VARCHAR(50) NOT NULL,
    e_em VARCHAR(100) NOT NULL,
    e_fn varchar(100) NOT NULL,
    e_ln varchar(100) NOT NULL
);

CREATE SEQUENCE inc
    MINVALUE 1
    MAXVALUE 2147483648
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
--DROP TRIGGER requests_pk;
--DROP SEQUENCE requests_seq;
--DROP TABLE requests;

CREATE TABLE requests(
    r_id INTEGER PRIMARY KEY,
    e_id INTEGER NOT NULL,
    r_date DATE DEFAULT CURRENT_DATE,
    r_amt NUMBER(14,2),
    r_img BLOB DEFAULT NULL,
    r_stat VARCHAR2(20) DEFAULT 'Pending',
    CONSTRAINT fk_e_id FOREIGN KEY (e_id) REFERENCES all_emp(e_id) ON DELETE CASCADE,
    CONSTRAINT r_stat_valid CHECK (r_stat = 'Pending' OR r_stat = 'Approved' OR r_stat = 'Denied'),
    CONSTRAINT r_amt_positive CHECK (r_amt > 0.0)
);
/

CREATE SEQUENCE requests_seq
    MINVALUE 1
    MAXVALUE 2147483648
    START WITH 1
    INCREMENT BY 1;
/
    
CREATE OR REPLACE TRIGGER requests_pk
    BEFORE INSERT ON requests
    FOR EACH ROW
BEGIN
    SELECT requests_seq.NEXTVAL INTO :NEW.r_id FROM DUAL;
END;
/

COMMIT;