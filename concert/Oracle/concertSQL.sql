DROP TABLE ORDERS;
DROP TABLE NOTICES;
DROP TABLE SEATS;
DROP TABLE SEAT_PRICE;
DROP TABLE CONCERTS;
DROP TABLE COMMENTS;
DROP TABLE ARTISTS;
DROP TABLE MEMBERS;

CREATE TABLE MEMBERS
(
    NO NUMBER(10) CONSTRAINT PK_MEMBERS_NO NOT NULL UNIQUE,
    ID VARCHAR2(10) CONSTRAINT PK_MEMBERS_ID PRIMARY KEY,
    PW VARCHAR2(20) CONSTRAINT NN_MEMBERS_PW NOT NULL,
    NICKNAME VARCHAR2(10) CONSTRAINT NN_MEMBERS_NICKNAME NOT NULL,
    NAME VARCHAR2(30) CONSTRAINT NN_MEMBERS_NAME NOT NULL,
    BIRTHDATE DATE CONSTRAINT NN_MEMBERS_BIRTHDATE NOT NULL,
    SEX VARCHAR(5) CONSTRAINT NN_MEMBERS_SEX NOT NULL,
    ADDRESS VARCHAR2(100),
    PHONE VARCHAR2(30),
    AUTHORITY VARCHAR2(10) DEFAULT 'USER',
    WITHDRAWAL NUMBER(1)
);

CREATE TABLE ARTISTS(
    NO NUMBER(10) CONSTRAINT  NNU_ARTISTS_NO NOT NULL UNIQUE,
    NAME VARCHAR2(30) CONSTRAINT PK_ARTISTS_NAME PRIMARY KEY,
    CONTENT VARCHAR2(1000) CONSTRAINT NN_ARTISTS_CONTENT NOT NULL
);

CREATE TABLE COMMENTS(
    NO NUMBER(10) CONSTRAINT PK_COMMENTS_NO PRIMARY KEY,
    ARTIST VARCHAR2(30) CONSTRAINT NN_COMMENTS_ARTIST NOT NULL,
    ID VARCHAR2(10) CONSTRAINT NN_COMMENTS_ID NOT NULL,
    CONTENT VARCHAR2(1000) CONSTRAINT NN_COMMENTS_CONTENT NOT NULL,
    WDATE DATE CONSTRAINT NN_COMMENTS_DATE NOT NULL,
    CONSTRAINT FK_COMMENTS_ARTISTS FOREIGN KEY (ARTIST) REFERENCES ARTISTS(NAME),
    CONSTRAINT FK_COMMENTS_MEMBERS FOREIGN KEY (CONTENT) REFERENCES MEMBERS(ID)
);

CREATE TABLE CONCERTS(
    NO NUMBER(10) CONSTRAINT PK_CONCERTS_NO PRIMARY KEY,
    ARTIST VARCHAR2(30) CONSTRAINT NN_CONCERTS_ARTIST NOT NULL,
    CONTENT VARCHAR2(1000) CONSTRAINT NN_CONCERTS_CONTENT NOT NULL,
    CDATE DATE CONSTRAINT NN_CONCERTS_CDATE NOT NULL,
    CONSTRAINT FK_CONCERTS_ARTISTS FOREIGN KEY (ARTIST) REFERENCES ARTISTS(NAME)
);

CREATE TABLE SEAT_PRICE(
    CONCERT_NO NUMBER(10) CONSTRAINT NN_SEAT_PRICE_CONCERT_NO NOT NULL,
    CLASS VARCHAR(1) CONSTRAINT NN_SEAT_PRICE_CLASS NOT NULL,
    PRICE NUMBER(10) CONSTRAINT NN_SEAT_PRICE_PRICE NOT NULL,
    CONSTRAINT FK_SEAT_PRICE_CONCERTS FOREIGN KEY (CONCERT_NO) REFERENCES CONCERTS(NO)
);

CREATE TABLE SEATS(
    CONCERT_NO NUMBER(10) CONSTRAINT NN_SEATSCONCERT_NO NOT NULL,
    ID VARCHAR2(10) CONSTRAINT NN_SEATS_ID NOT NULL,
    CLASS VARCHAR2(1) CONSTRAINT NN_SEATS_CLASS NOT NULL,
    PRICE VARCHAR2(10) CONSTRAINT NN_SEATS_PRICE NOT NULL,
    NO VARCHAR2(5) CONSTRAINT NN_SEATS_NO NOT NULL,
    CONSTRAINT FK_SEATS_CONCERTS FOREIGN KEY (CONCERT_NO) REFERENCES CONCERTS(NO)
);

CREATE TABLE NOTICES(
    NO NUMBER(10) CONSTRAINT PK_NOTICES_NO PRIMARY KEY,
    TITLE VARCHAR2(30) CONSTRAINT NN_NOTICES_TITLE NOT NULL,
    CONTENT VARCHAR2(1000) CONSTRAINT NN_NOTICES_CONTENT NOT NULL,
    WDATE DATE CONSTRAINT NN_NOTICES_WDATE NOT NULL,
    WRITER VARCHAR2(10) CONSTRAINT NN_NOTICES_WRITER NOT NULL,
    VIEWS NUMBER(10) CONSTRAINT NN_NOTICES_VIEWS NOT NULL,
    CONSTRAINT FK_NOTICES_WRITER FOREIGN KEY (WRITER) REFERENCES MEMBERS(ID)
);

CREATE TABLE ORDERS(
    NO NUMBER(10) CONSTRAINT PK_ORDERS_NO PRIMARY KEY,
    ID VARCHAR2(10) CONSTRAINT NN_ORDERS_ID NOT NULL,
    CONCERTS_NO NUMBER(10) CONSTRAINT NN_ORDERS_CONCERTS_NO NOT NULL,
    SEATS_NO VARCHAR2(5) CONSTRAINT NN_ORDERS_SEATS_NO NOT NULL,
    CONSTRAINTS FK_ORDERS_MEMBERS FOREIGN KEY (ID) REFERENCES MEMBERS(ID),
    CONSTRAINTS FK_ORDERS_CONCERTS FOREIGN KEY (CONCERTS_NO) REFERENCES CONCERTS(NO)
);

-- 테스트용 계정 생성
INSERT INTO MEMBERS VALUES(0, 'test', '1234', '테스트', '홍길동', SYSDATE, 'M', '서울시', '010-1234-5678', 'tester', 0);
COMMIT;
SELECT PW FROM MEMBERS WHERE id='test';