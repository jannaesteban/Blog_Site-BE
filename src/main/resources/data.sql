INSERT INTO public.role (id, name) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', 'ADMIN');
INSERT INTO public.role (id, name) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', 'USER');
INSERT INTO public.role (id, name) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', 'GUEST');
INSERT INTO public.authority (id, name) VALUES ('90678825-af11-48ec-9ae8-ca1f4596230a', 'READ');
INSERT INTO public.authority (id, name) VALUES ('78904317-e48e-493d-abdd-8f83be72f584', 'CREATE');
INSERT INTO public.authority (id, name) VALUES ('5b2d3ebe-f382-4bab-89f3-a2b4c7727791', 'DELETE');
INSERT INTO public.authority (id, name) VALUES ('9ab874e1-94f3-4f04-bd65-773396fcef9a', 'UPDATE');
INSERT INTO public.authority (id, name) VALUES ('293cb7c7-8dd7-492b-ad93-0223cf5c8e4a', 'ALL_PPRIVILEGES');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '90678825-af11-48ec-9ae8-ca1f4596230a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '78904317-e48e-493d-abdd-8f83be72f584');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '5b2d3ebe-f382-4bab-89f3-a2b4c7727791');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('3b24cce9-1bda-493e-84d3-41cb35d539de', '9ab874e1-94f3-4f04-bd65-773396fcef9a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('03336c98-b9a2-4e51-865f-91dd93ce6542', '90678825-af11-48ec-9ae8-ca1f4596230a');
INSERT INTO public.role_authorities (role_id, authority_id) VALUES ('58110d15-0128-40f1-a39b-ac61519af86e', '293cb7c7-8dd7-492b-ad93-0223cf5c8e4a');
INSERT INTO public.users (id, email, password, username) VALUES ('aaf19905-4ce3-4319-86e7-29852d82174e', 'james.bond@mi6.com', 'bond', 'james');
INSERT INTO public.users (id, email, password, username) VALUES ('9b548bc5-1730-4294-be58-6fe48a04cb57', 'luca.widmer@gmail.com', 'widmer', 'luca');
INSERT INTO public.users (id, email, password, username) VALUES ('54ac527e-60fd-4eb2-af38-48c737921a87', 'lena.antonelli@gmail.com', 'antonelli', 'lena');
INSERT INTO public.users (id, email, password, username) VALUES ('cef35c01-6fae-4911-8b56-7c7b090c9b43', 'martin.meier@gmail.com', 'martin', 'meier');
INSERT INTO public.users (id, email, password, username) VALUES ('50c99d44-e81a-46f3-a9ef-e44f78c42755', 'maria.dillard@gmail.com', 'dillard', 'maria');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('aaf19905-4ce3-4319-86e7-29852d82174e', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('9b548bc5-1730-4294-be58-6fe48a04cb57', '58110d15-0128-40f1-a39b-ac61519af86e');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('54ac527e-60fd-4eb2-af38-48c737921a87', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('50c99d44-e81a-46f3-a9ef-e44f78c42755', '3b24cce9-1bda-493e-84d3-41cb35d539de');
INSERT INTO public.users_roles (user_id, role_id) VALUES ('cef35c01-6fae-4911-8b56-7c7b090c9b43', '03336c98-b9a2-4e51-865f-91dd93ce6542');

