--Week 2 Code Challenge Entity Creation Script
--Ian

CREATE TABLE DEPARTMENT(
    DEPARTMENT_ID INTEGER PRIMARY KEY,
    DEPARTMENT_NAME VARCHAR(60) UNIQUE NOT NULL
);
/

CREATE TABLE EMPLOYEE(
    EMPLOYEE_ID INTEGER PRIMARY KEY,
    EMP_FIRSTNAME VARCHAR2(60) NOT NULL,
    EMP_LASTNAME VARCHAR2(60) NOT NULL,
    DEPARTMENT_ID INTEGER NOT NULL,
    SALARY NUMBER(14,2),
    EMP_EMAIL VARCHAR2(60) UNIQUE,
    CONSTRAINT DEPARTMENT_ID_FK FOREIGN  KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID)
);
/

--Department primary key autoincrement sequence
CREATE SEQUENCE DEPARTMENT_SEQUENCE_PK
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;
/

--Department primary key autoincrement trigger
CREATE OR REPLACE TRIGGER DEPARTMENT_INSERT_INC_PK
    BEFORE INSERT ON DEPARTMENT
    FOR EACH ROW
BEGIN
    SELECT DEPARTMENT_SEQUENCE_PK.NEXTVAL INTO :NEW.DEPARTMENT_ID FROM DUAL;
END;
/

--Employee primary key autoincrement sequence
CREATE SEQUENCE EMPLOYEE_SEQUENCE_PK
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;
/

--Employee primary key autoincrement trigger
CREATE OR REPLACE TRIGGER EMPLOYEE_INSERT_INC_PK
    BEFORE INSERT ON EMPLOYEE
    FOR EACH ROW
BEGIN
    SELECT EMPLOYEE_SEQUENCE_PK.NEXTVAL INTO :NEW.EMPLOYEE_ID FROM DUAL;
END;
/

--Inserting departments



