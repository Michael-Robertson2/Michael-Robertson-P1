create table ers_reimbursement_statuses (
	STATUS_ID varchar primary key,
	STATUS VARCHAR UNIQUE
);

create table ERS_REIMBURSEMENT_TYPES (
	TYPE_ID VARCHAR primary key,
	type VARCHAR unique
);

create table ERS_USER_ROLES (
	ROLE_ID VARCHAR primary key,
	role VARCHAR UNIQUE
);

create table ers_users (
	USER_ID VARCHAR primary key,
	USERNAME VARCHAR unique not null,
	EMAIL VARCHAR unique not null,
	password VARCHAR not null,
	GIVEN_NAME VARCHAR not null,
	SURNAME VARCHAR not null,
	IS_ACTIVE BOOLEAN,
	ROLE_ID VARCHAR,

	constraint FK_ROLE_ID
		foreign key (ROLE_ID) references ERS_USER_ROLES(ROLE_ID)
);

CREATE TABLE ERS_REIMBURSEMENTS (
	REIMB_ID VARCHAR PRIMARY KEY,
	AMOUNT NUMERIC(6, 2) NOT NULL,
	SUBMITTED TIMESTAMP NOT NULL,
	RESOLVED TIMESTAMP,
	DESCRIPTION VARCHAR NOT NULL,
	RECEIPT BYTEA,
	PAYMENT_ID VARCHAR,
	AUTHOR_ID VARCHAR NOT null,
	RESOLVER_ID VARCHAR,
	STATUS_ID VARCHAR NOT NULL,
	TYPE_ID VARCHAR NOT null,

	constraint fk_author_id
		foreign key (AUTHOR_ID) references ERS_USERS(USER_ID),

	constraint fk_resolver_id
		foreign key (resolver_id) references ERS_USERS,

	constraint fk_status_id
		foreign key (status_id) references ERS_REIMBURSEMENT_STATUSES(STATUS_ID),

	constraint fk_type_id
		foreign key (TYPE_ID) references ers_reimbursement_types(type_id)
);