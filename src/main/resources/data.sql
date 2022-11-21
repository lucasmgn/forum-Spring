INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'lucas@gmail.com', '$2a$10$DDWGaOMdvTdGfxJBh4LxV..MkAUsqQLS1IaAIkcaW3yz44dS3sNs6');
INSERT INTO USUARIO(nome, email, senha) VALUES('Moderador', 'moderador@gmail.com', '$2a$10$DDWGaOMdvTdGfxJBh4LxV..MkAUsqQLS1IaAIkcaW3yz44dS3sNs6');

INSERT INTO PERFIL(nome) VALUES('ROLE_ALUNO'); -- O Spring-Security tem um padrão "ROLE_NOME"
INSERT INTO PERFIL(nome) VALUES('ROLE_MODERADOR'); -- O Spring-Security tem um padrão "ROLE_NOME"

INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES(1, 1);
INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES(2, 2);

INSERT INTO CURSO(nome, categoria) VALUES('Spring Boot', 'Programação');
INSERT INTO CURSO(nome, categoria) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida', 'Erro ao criar projeto', '2019-05-05 18:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida 2', 'Projeto não compila', '2019-05-05 19:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida 3', 'Tag HTML', '2019-05-05 20:00:00', 'NAO_RESPONDIDO', 1, 2);