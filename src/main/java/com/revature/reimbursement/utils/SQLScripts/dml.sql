insert into ers_user_roles (role_id, role) values ('b06a2afc-702c-11ed-a1eb-0242ac120002', 'User');
insert into ers_user_roles (role_id, role) values ('b06a2afc-702c-11ed-a1eb-0242ac120003', 'Admin');
insert into ers_user_roles (role_id, role) values ('b06a2afc-702c-11ed-a1eb-0242ac120004', 'FinanceManager');


insert into ers_reimbursement_types (type_id, type) values ('f05813a6-71b0-11ed-a1eb-0242ac120002', 'LODGING');
insert into ers_reimbursement_types (type_id, type) values ('f05813a6-71b0-11ed-a1eb-0242ac120003', 'TRAVEL');
insert into ers_reimbursement_types (type_id, type) values ('f05813a6-71b0-11ed-a1eb-0242ac120004', 'FOOD');
insert into ers_reimbursement_types (type_id, type) values ('f05813a6-71b0-11ed-a1eb-0242ac120005', 'OTHER');

insert into ers_reimbursement_statuses (status_id, status) values ('ca684fbc-71ba-11ed-a1eb-0242ac120002', 'PENDING');
insert into ers_reimbursement_statuses (status_id, status) values ('ca684fbc-71ba-11ed-a1eb-0242ac120003', 'APPROVED');
insert into ers_reimbursement_statuses (status_id, status) values ('ca684fbc-71ba-11ed-a1eb-0242ac120004', 'DENIED');


insert into ers_users (user_id, username, email, "password" , given_name, surname, is_active, role_id)
							values ('e8b582cc-712a-11ed-a1eb-0242ac120002', 'BigBoss', 'big@boss.com', 'biggestb0ss',
									'big', 'boss', true, 'b06a2afc-702c-11ed-a1eb-0242ac120003');

insert into ers_users (user_id, username, email, "password" , given_name, surname, is_active, role_id)
							values ('48baa374-7206-11ed-a1eb-0242ac120002', 'The_Manager', 'the@manager.com', 'Manager1',
									'Mana', 'ger', true, 'b06a2afc-702c-11ed-a1eb-0242ac120004');


insert into ers_users (user_id, username, email, "password" , given_name, surname, is_active, role_id)
							values ('09879a64-746c-11ed-a1eb-0242ac120002', 'Tester01', 'Test@test.com', 'Passw0rd',
									'Testy', 'Test', true, 'b06a2afc-702c-11ed-a1eb-0242ac120002');

insert into ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id,
								resolver_id, status_id, type_id) values ('3102e878-746c-11ed-a1eb-0242ac120002',
								'250', CURRENT_TIMESTAMP, null, 'Lunch', null, null, '09879a64-746c-11ed-a1eb-0242ac120002',
								null, 'ca684fbc-71ba-11ed-a1eb-0242ac120002', 'f05813a6-71b0-11ed-a1eb-0242ac120004');

insert into ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id,
								resolver_id, status_id, type_id) values ('3102e878-746c-11ed-a1eb-0242ac120003',
								'340.15', CURRENT_TIMESTAMP, null, 'Party', null, null, '09879a64-746c-11ed-a1eb-0242ac120002',
								null, 'ca684fbc-71ba-11ed-a1eb-0242ac120002', 'f05813a6-71b0-11ed-a1eb-0242ac120005');