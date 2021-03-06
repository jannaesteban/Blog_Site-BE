INSERT INTO public.role (id, name) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', 'ADMIN');
INSERT INTO public.role (id, name) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', 'USER');
INSERT INTO public.role (id, name) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', 'SUPERVISOR');
INSERT INTO public.authority (id, name) VALUES ('90678825-af11-48ec-9ae8-ca1f4596230a', 'READ_OWN');
INSERT INTO public.authority (id, name) VALUES ('8bc470e9-308e-4e34-907e-b17e01a729ba', 'READ_ALL');
INSERT INTO public.authority (id, name) VALUES ('5b2d3ebe-f382-4bab-89f3-a2b4c7727791', 'DELETE_OWN');
INSERT INTO public.authority (id, name) VALUES ('adbbbcd7-942e-4dcc-856d-df17404c8bad', 'DELETE_OTHERS');
INSERT INTO public.authority (id, name) VALUES ('9ab874e1-94f3-4f04-bd65-773396fcef9a', 'UPDATE_OWN');
INSERT INTO public.authority (id, name) VALUES ('1521610a-7f79-4a16-a3dd-6d609827329d', 'UPDATE_ALL');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '90678825-af11-48ec-9ae8-ca1f4596230a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '5b2d3ebe-f382-4bab-89f3-a2b4c7727791');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '9ab874e1-94f3-4f04-bd65-773396fcef9a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', '90678825-af11-48ec-9ae8-ca1f4596230a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', '8bc470e9-308e-4e34-907e-b17e01a729ba');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', '9ab874e1-94f3-4f04-bd65-773396fcef9a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '90678825-af11-48ec-9ae8-ca1f4596230a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '8bc470e9-308e-4e34-907e-b17e01a729ba');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '5b2d3ebe-f382-4bab-89f3-a2b4c7727791');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', 'adbbbcd7-942e-4dcc-856d-df17404c8bad');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '9ab874e1-94f3-4f04-bd65-773396fcef9a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '1521610a-7f79-4a16-a3dd-6d609827329d');
INSERT INTO public.users (id, email, password, username) VALUES ('aaf19905-4ce3-4319-86e7-29852d82174e', 'james.bond@mi6.com', '$2a$10$jI2SebWPa4ztK.VZsEH0RuJRpyY1iRSRoE9HGjzz5JC9qSKZ4g9qy', 'james');/*Password: bond*/
INSERT INTO public.users (id, email, password, username) VALUES ('9b548bc5-1730-4294-be58-6fe48a04cb57', 'luca.widmer@gmail.com', '$2a$10$1AlU90K9u5EqmkobEdp18O.nVm8P1iYSd6AMR.QM2NL4jVDZ8PkOK', 'luca');/*Password: widmer*/
INSERT INTO public.users (id, email, password, username) VALUES ('54ac527e-60fd-4eb2-af38-48c737921a87', 'lena.antonelli@gmail.com', '$2a$10$C31NUNirz8BdFk1QvkPPSee/EdF7LlJrpyep5JdOO.99FHxCPOj8.', 'lena');/*Password: antonelli*/
INSERT INTO public.users (id, email, password, username) VALUES ('cef35c01-6fae-4911-8b56-7c7b090c9b43', 'martin.meier@gmail.com', '$2a$10$vFqAFPglRZFCnz5z1NWMUOJDcnvucrkN80HU.PvEOSNbDoB/LsbXC', 'meier');/*Password: martin*/
INSERT INTO public.users (id, email, password, username) VALUES ('50c99d44-e81a-46f3-a9ef-e44f78c42755', 'maria.dillard@gmail.com', '$2a$10$RvWQkZfU1ffvWH6bK/Vx2uPaUSpzsSeR.hWA2r9a1HidtOU1AW8/a', 'maria');/*Password: dillard*/
INSERT INTO public.users_roles (user_id, role_id) VALUES ('aaf19905-4ce3-4319-86e7-29852d82174e', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('9b548bc5-1730-4294-be58-6fe48a04cb57', '58110d15-0128-40f1-a39b-ac61519af86e');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('54ac527e-60fd-4eb2-af38-48c737921a87', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('50c99d44-e81a-46f3-a9ef-e44f78c42755', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('cef35c01-6fae-4911-8b56-7c7b090c9b43', '03336c98-b9a2-4e51-865f-91dd93ce6542');