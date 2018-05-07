--SQL Lab Assignment
--Ian
--May 5th, 2018

--SECTION 2 : SQL Queries

--2.1 SELECT
--Task – Select all records from the Employee table.
SELECT * FROM EMPLOYEE;
/
--Task – Select all records from the Employee table where last name is King.
SELECT * FROM EMPLOYEE WHERE LASTNAME = 'King';
/
--Task – Select all records from the Employee table where first name is Andrew and REPORTSTO is NULL.
SELECT * FROM EMPLOYEE WHERE FIRSTNAME = 'Andrew' AND REPORTSTO IS NULL;
/

--2.2 ORDER BY
--Task – Select all albums in Album table and sort result set in descending order by title.
SELECT * FROM ALBUM ORDER BY TITLE DESC;
/
--Task – Select first name from Customer and sort result set in ascending order by city
SELECT FIRSTNAME, CITY FROM CUSTOMER ORDER BY CITY DESC;
/

--2.3 INSER INTO
--Task – Insert two new records into Genre table 
INSERT INTO GENRE (GENREID, NAME) VALUES (26, 'Post Rock');
INSERT INTO GENRE (GENREID, NAME) VALUES (27, 'Indie Rock');
--Task – Insert two new records into Employee table
INSERT INTO EMPLOYEE (EMPLOYEEID, LASTNAME, FIRSTNAME, TITLE ,REPORTSTO ,BIRTHDATE, HIREDATE ,ADDRESS, CITY,
    STATE, COUNTRY, POSTALCODE, PHONE, FAX, EMAIL)
    VALUES
    (9, 'Solo', 'Han', 'IT Staff' , 6, TO_DATE('31-jan-1987', 'DD-MM-YYYY') , TO_DATE('05-may-2018', 'DD-MM-YYYY'), 
    '2318 243rd Pl', 'Calgary', 'AB', 'Canada', 'T5K 2N1', '1-234-234-3343', '1-234-234-3344', 'hansolo@gmail.com');
INSERT INTO EMPLOYEE (EMPLOYEEID, LASTNAME, FIRSTNAME, TITLE ,REPORTSTO ,BIRTHDATE, HIREDATE ,ADDRESS, CITY,
    STATE, COUNTRY, POSTALCODE, PHONE, FAX, EMAIL)
    VALUES
    (10, 'Skywalker', 'Luke', 'IT Staff' , 6, TO_DATE('31-jan-1989', 'DD-MM-YYYY') , TO_DATE('05-may-2018', 'DD-MM-YYYY'), 
    '2318 243rd Pl', 'Calgary', 'AB', 'Canada', 'T5K 2N1', '1-234-234-3343', '1-234-234-3344', 'lskywalker@gmail.com');
--Task – Insert two new records into Customer table
INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, COMPANY, ADDRESS, CITY,
    STATE, COUNTRY, POSTALCODE, PHONE, FAX, EMAIL, SUPPORTREPID)
    VALUES
    (60, 'Jack', 'Sky', 'Fatburger', '2343 Blue Pl', 'Calgary', 'AB', 'Canada', 'T5K 2N1',
    '1-432-234-4333', '1-234-343-2343', 'jsky@gmail.com', 3);
INSERT INTO CUSTOMER (CUSTOMERID, FIRSTNAME, LASTNAME, COMPANY, ADDRESS, CITY,
    STATE, COUNTRY, POSTALCODE, PHONE, FAX, EMAIL, SUPPORTREPID)
    VALUES
    (61, 'Jill', 'Sky', 'Panda Express', '2343 Blue Pl', 'Calgary', 'AB', 'Canada', 'T5K 2N1',
    '1-432-234-4333', '1-234-343-2343', 'jillsky@gmail.com', 3);
/

--2.4 UPDATE
--Task – Update Aaron Mitchell in Customer table to Robert Walter
UPDATE CUSTOMER SET FIRSTNAME = 'Robert', LASTNAME = 'Walter' WHERE FIRSTNAME = 'Aaron' AND LASTNAME = 'Mitchell';
--Task – Update name of artist in the Artist table “Creedence Clearwater Revival” to “CCR”    
UPDATE ARTIST SET NAME = 'CCR' WHERE NAME = 'Creedence Clearwater Revival';
/

--2.5 LIKE
--Task – Select all invoices with a billing address like “T%”
SELECT * FROM INVOICE WHERE BILLINGADDRESS LIKE 'T%';
/

--2.6 BETWEEN
--Task – Select all invoices that have a total between 15 and 50
SELECT * FROM INVOICE WHERE TOTAL BETWEEN 15 AND 50;
--Task – Select all employees hired between 1st of June 2003 and 1st of March 2004
SELECT * FROM EMPLOYEE WHERE HIREDATE BETWEEN TO_DATE('01-jun-2003', 'dd-mm-yyyy') AND TO_DATE('01-MAR-2004', 'dd-mm-yyyy');
/

--2.7 DELETE
--Task – Delete a record in Customer table where the name is Robert Walter (There may be constraints that rely on this, find out how to resolve them).
ALTER TABLE INVOICE DROP CONSTRAINT FK_INVOICECUSTOMERID;
DELETE FROM CUSTOMER WHERE FIRSTNAME = 'Robert' AND LASTNAME = 'Walter';
DELETE FROM INVOICELINE WHERE INVOICEID IN (SELECT INVOICEID FROM INVOICE WHERE CUSTOMERID = 32);
DELETE FROM INVOICE WHERE CUSTOMERID = 32;
ALTER TABLE INVOICE ADD CONSTRAINT FK_INVOICECUSTOMERID
    FOREIGN KEY (CUSTOMERID)
    REFERENCES CUSTOMER(CUSTOMERID);
/

--Section 3

--3.1 System Defined Functions
--Task – Create a function that returns the current time.
CREATE OR REPLACE FUNCTION RETURN_CURR_TIME
    RETURN TIMESTAMP
IS
BEGIN
    RETURN SYSTIMESTAMP;
END RETURN_CURR_TIME;
/

--Task – create a function that returns the length of name in MEDIATYPE table
CREATE OR REPLACE FUNCTION MEDIATYPE_NAME_LEN (MEDIATYPE_NAME VARCHAR2)
    RETURN INTEGER
IS
BEGIN
    RETURN LENGTH(MEDIATYPE_NAME);
END MEDIATYPE_NAME_LEN;

--3.2 System Defined Aggregate Functions
--Task – Create a function that returns the average total of all invoices
CREATE OR REPLACE FUNCTION AVG_TOTAL_INVOICES
    RETURN NUMBER
IS
    t NUMBER;
    
    CURSOR c IS SELECT AVG(TOTAL) FROM INVOICE;
BEGIN
    OPEN c;
    FETCH c INTO t;
    
    if(c%NOTFOUND) THEN
        t := 0;
    END IF;
    CLOSE c;
    RETURN t;
END AVG_TOTAL_INVOICES;
/

--Task – Create a function that returns the most expensive track
CREATE OR REPLACE FUNCTION MOST_EXPENSIVE_TRACK 
    RETURN NUMBER
IS
    t NUMBER;
    CURSOR c is SELECT MAX(UNITPRICE) FROM TRACK;
BEGIN
    OPEN c;
    FETCH C INTO t;
    IF(C%NOTFOUND) THEN
        t := 0;
    END IF;
    RETURN t;
END MOST_EXPENSIVE_TRACK;
/

--3.3 User Defined Scalar Functions
--Task – Create a function that returns the average price of invoiceline items in the invoiceline table
CREATE OR REPLACE FUNCTION AVG_PRICE_INVOICELINE_ITEM
    RETURN NUMBER
IS
    CURSOR c IS SELECT AVG(UNITPRICE) FROM INVOICELINE;
    t NUMBER;
BEGIN
    OPEN c;
    FETCH c INTO t;
    IF(c%NOTFOUND) THEN
        t := 0;
    END IF;
    RETURN t;
END AVG_PRICE_INVOICELINE_ITEM;
/

--3.4 User Defined Table Valued Functions
--Creating the type to hold each employee row
CREATE TYPE EMPLOYEE_ROW AS OBJECT
    (EMPLOYEEID	NUMBER,
     LASTNAME	VARCHAR2(20),
     FIRSTNAME	VARCHAR2(20),
     TITLE	VARCHAR2(30),
     REPORTSTO	NUMBER,
     BIRTHDATE	DATE,
     HIREDATE	DATE,
     ADDRESS	VARCHAR2(70),
     CITY	VARCHAR2(40),
     STATE	VARCHAR2(40),
     COUNTRY	VARCHAR2(40),
     POSTALCODE	VARCHAR2(10),
     PHONE	VARCHAR2(24),
     FAX	VARCHAR2(24),
     EMAIL	VARCHAR2(60));
/
--Creating the table to hold the rows
CREATE TYPE EMPLOYEE_TABLE AS TABLE OF EMPLOYEE_ROW;
/

--Creating the table valued function which returns the employees born after 1968
CREATE OR REPLACE FUNCTION GET_EMPLOYEES_BORN_AFTER_1968
    RETURN EMPLOYEE_TABLE 
AS
    CURSOR c IS SELECT * FROM EMPLOYEE WHERE BIRTHDATE > TO_DATE('31-12-1968', 'dd-mm-yyyy');
    employee_t EMPLOYEE_TABLE := EMPLOYEE_TABLE();
    EMPLOYEEID_V	NUMBER;
    LASTNAME_V	VARCHAR2(20);
    FIRSTNAME_V	VARCHAR2(20);
    TITLE_V	VARCHAR2(30);
    REPORTSTO_V	NUMBER;
    BIRTHDATE_V	DATE;
    HIREDATE_V	DATE;
    ADDRESS_V	VARCHAR2(70);
    CITY_V	VARCHAR2(40);
    STATE_V	VARCHAR2(40);
    COUNTRY_V	VARCHAR2(40);
    POSTALCODE_V	VARCHAR2(10);
    PHONE_V	VARCHAR2(24);
    FAX_V	VARCHAR2(24);
    EMAIL_V	VARCHAR2(60);
BEGIN
    OPEN c;
    LOOP
        FETCH c INTO EMPLOYEEID_V, LASTNAME_V, FIRSTNAME_V, TITLE_V, REPORTSTO_V, BIRTHDATE_V, HIREDATE_V, ADDRESS_V, CITY_V, STATE_V,
                    COUNTRY_V, POSTALCODE_V, PHONE_V, FAX_V, EMAIL_V;
        EXIT WHEN c%NOTFOUND;
        employee_t.EXTEND;
        employee_t(employee_t.LAST) := EMPLOYEE_ROW(EMPLOYEEID_V, LASTNAME_V, FIRSTNAME_V, TITLE_V, REPORTSTO_V, BIRTHDATE_V, HIREDATE_V, ADDRESS_V, CITY_V, STATE_V,
                    COUNTRY_V, POSTALCODE_V, PHONE_V, FAX_V, EMAIL_V);
    END LOOP;
    CLOSE c;
    RETURN employee_t;
END GET_EMPLOYEES_BORN_AFTER_1968;

--TEST:   SELECT * FROM TABLE(GET_EMPLOYEES_BORN_AFTER_1968);
