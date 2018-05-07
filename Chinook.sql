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

--4.0 Stored Procedures

--4.1 Basic Stored Procedure
--Task – Create a stored procedure that selects the first and last names of all the employees.
CREATE OR REPLACE PROCEDURE FIRST_AND_LAST_NAME_EMPLOYEES(employees_result OUT SYS_REFCURSOR) AS 
BEGIN
    OPEN employees_result FOR SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE;
END;

--4.2 Stored Procedure Input Parameters
--Task – Create a stored procedure that updates the personal information of an employee.
CREATE OR REPLACE PROCEDURE UPDATE_EMPLOYEE(EMPLOYEEID_V NUMBER, LASTNAME_V VARCHAR2, FIRSTNAME_V VARCHAR2,
    TITLE_V	VARCHAR2, REPORTSTO_V NUMBER, BIRTHDATE_V DATE, HIREDATE_V DATE, ADDRESS_V VARCHAR2, CITY_V	VARCHAR2,
    STATE_V	VARCHAR2, COUNTRY_V	VARCHAR2, POSTALCODE_V	VARCHAR2, PHONE_V VARCHAR2, FAX_V VARCHAR2, EMAIL_V	VARCHAR2)
AS
BEGIN
    UPDATE EMPLOYEE SET 
        LASTNAME = LASTNAME_V,
        FIRSTNAME = FIRSTNAME_V,
        TITLE = TITLE_V,
        REPORTSTO = REPORTSTO_V,
        BIRTHDATE = BIRTHDATE_V,
        HIREDATE = HIREDATE_V,
        ADDRESS = ADDRESS_V,
        CITY = CITY_V,
        STATE = STATE_V,
        COUNTRY = COUNTRY_V,
        POSTALCODE = POSTALCODE_V,
        PHONE = PHONE_V,
        FAX = FAX_V,
        EMAIL = EMAIL_V
    WHERE EMPLOYEEID = EMPLOYEEID_V;
END;

--Task – Create a stored procedure that returns the managers of an employee.
CREATE OR REPLACE PROCEDURE EMPLOYEES_MANAGER(EMPLOYEE_ID IN NUMBER, MANAGER_ID OUT NUMBER) 
AS
    MANAGER_NUM NUMBER;
BEGIN
    SELECT REPORTSTO INTO MANAGER_ID FROM EMPLOYEE WHERE EMPLOYEEID = EMPLOYEE_ID;
END;

--4.3 Stored Procedure Output Parameters
--Task – Create a stored procedure that returns the name and company of a customer.
CREATE OR REPLACE PROCEDURE CUSTOMER_NAME_AND_COMPANY(C_ID IN NUMBER, C_FIRSTNAME OUT VARCHAR2, C_LASTNAME OUT VARCHAR2, COM_NAME OUT VARCHAR2)
AS
BEGIN
    SELECT FIRSTNAME, LASTNAME, COMPANY INTO C_FIRSTNAME, C_LASTNAME, COM_NAME FROM CUSTOMER WHERE CUSTOMERID = C_ID;
END;

--5.0 Transactions
--Task – Create a transaction that given a invoiceId will delete that invoice (There may be constraints that rely on this, find out how to resolve them).
CREATE OR REPLACE PROCEDURE DELETE_INVOICE(I_ID IN NUMBER)  
AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE INVOICELINE DROP CONSTRAINT FK_INVOICELINEINVOICEID';
    DELETE FROM INVOICE WHERE INVOICEID = I_ID;
    DELETE FROM INVOICELINE WHERE INVOICEID = I_ID;
    EXECUTE IMMEDIATE 'ALTER TABLE INVOICELINE ADD CONSTRAINT FK_INVOICELINEINVOICEID
        FOREIGN KEY (INVOICEID)
        REFERENCES INVOICE(INVOICEID)';
    COMMIT;
END;

--EXECUTE DELETE_INVOICE(321);

--6.0 Triggers

--6.1 AFTER/FOR
--Task - Create an after insert trigger on the employee table fired after a new record is inserted into the table.
CREATE OR REPLACE TRIGGER AFTER_EMPLOYEE_INSERT
AFTER INSERT ON EMPLOYEE
DECLARE
BEGIN
    dbms_output.put_line('something inserted into EMPLOYEE');
    --Something here will happen after insert
END;

--Task – Create an after update trigger on the album table that fires after a row is inserted in the table
CREATE OR REPLACE TRIGGER AFTER_ALBUM_UPDATE
AFTER UPDATE ON ALBUM
DECLARE
BEGIN
    dbms_output.put_line('Updated ALBUM');
END;

--Task – Create an after delete trigger on the customer table that fires after a row is deleted from the table.
CREATE OR REPLACE TRIGGER AFTER_CUSTOMER_DELETE
AFTER DELETE ON CUSTOMER
DECLARE
BEGIN
    dbms_output.put_line('Deleted from CUSTOMER');
END;


--7.0 JOINS
--In this section you will be working with combining various tables through the use of joins. You will work with outer, inner, right, left, cross, and self joins.
--7.1 INNER
--Task – Create an inner join that joins customers and orders and specifies the name of the customer and the invoiceId.
SELECT LASTNAME, FIRSTNAME, INVOICEID FROM (CUSTOMER INNER JOIN INVOICE ON CUSTOMER.CUSTOMERID = INVOICE.CUSTOMERID);
--7.2 OUTER
--Task – Create an outer join that joins the customer and invoice table, specifying the CustomerId, firstname, lastname, invoiceId, and total.
SELECT CUSTOMER.CUSTOMERID, FIRSTNAME, LASTNAME, INVOICEID, TOTAL FROM (CUSTOMER FULL OUTER JOIN INVOICE on CUSTOMER.CUSTOMERID = INVOICE.CUSTOMERID);
--7.3 RIGHT
--Task – Create a right join that joins album and artist specifying artist name and title.
SELECT ARTIST.NAME, ALBUM.TITLE FROM (ALBUM RIGHT OUTER JOIN ARTIST ON ALBUM.ARTISTID = ARTIST.ARTISTID);
--7.4 CROSS
--Task – Create a cross join that joins album and artist and sorts by artist name in ascending order.
SELECT * FROM (ALBUM CROSS JOIN ARTIST) ORDER BY ARTIST.NAME ASC;
--7.5 SELF
--Task – Perform a self-join on the employee table, joining on the reportsto column.
SELECT E.LASTNAME AS LASTNAME1, E.FIRSTNAME AS FIRSTNAME1, M.LASTNAME AS LASTNAME2, M.FIRSTNAME AS FIRSTNAME2, E.REPORTSTO 
FROM (EMPLOYEE E JOIN EMPLOYEE M ON E.REPORTSTO = M.REPORTSTO);





