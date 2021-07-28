create sequence user_id_seq start with 1 increment by 1;

CREATE TABLE USERS (
        ID BIGINT DEFAULT nextval('user_id_seq') NOT NULL,
        NAME VARCHAR(255) NOT NULL,
        email VARCHAR(50) NOT NULL,
        password VARCHAR(25) NOT NULL,
        PRIMARY KEY (id)
    );

create sequence transaction_id_seq start with 1 increment by 1;

CREATE TABLE TRANSACTIONS (
        ID BIGINT DEFAULT nextval('transaction_id_seq') NOT NULL,
        TXN_TYPE VARCHAR(50),
        AMOUNT DECIMAL,
        DESCRIPTION VARCHAR(255),
        CREATED_ON DATE,
        CREATED_BY DATE,
        PRIMARY KEY (id)
    );
