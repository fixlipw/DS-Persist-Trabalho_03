--------------------------------------------------------------------------------------------------
-- Script para o SQLite
-- Tabela de Usuários
create table leituras
(
    id         integer,
    authorname varchar(255),
    pages_qtd  integer,
    status     varchar(255) check (status in ('NAO_LIDO', 'LENDO', 'LIDO', 'ABANDONADO')),
    title      varchar(255) unique,
    type       varchar(255) check (type in
                                   ('LIVRO', 'EBOOK', 'PDF', 'ARTIGO', 'REVISTA', 'JORNAL', 'AUDIOBOOK', 'QUADRINHO',
                                    'BLOG', 'RELATORIO', 'TCC', 'DIARIO')),
    author_id  integer,
    usuario_id integer,
    primary key (id)
);

create table autores
(
    id         integer,
    authorname varchar(255),
    brief      varchar(255),
    primary key (id)
);

create table anotacoes
(
    id         integer not null,
    annotation varchar(255),
    date       timestamp,
    leitura_id integer,
    primary key (id)
);

create table usuarios
(
    id       integer primary key autoincrement,
    password text,
    username text
);

create unique index uk_m2dvbwfge291euvmk6vkkocao
    on usuarios (username);


create table anotacoes_seq
(
    next_val bigint
);

insert into anotacoes_seq
values (1);

INSERT INTO autores (id, authorname, brief)
VALUES (1, 'J.K. Rowling', 'Autora britânica famosa por Harry Potter'),
       (2, 'George Orwell', 'Escritor e jornalista inglês conhecido por 1984'),
       (3, 'Jane Austen', 'Romancista inglesa do século XIX'),
       (4, 'Stephen King', 'Autor de suspense e terror dos EUA'),
       (5, 'Harper Lee', 'Autora americana conhecida por "O Sol é para Todos"'),
       (6, 'Agatha Christie', 'Escritora britânica de romances policiais'),
       (7, 'Gabriel García Márquez', 'Escritor colombiano e Prêmio Nobel de Literatura'),
       (8, 'J.R.R. Tolkien', 'Autor britânico, criador de "O Senhor dos Anéis"'),
       (9, 'Margaret Atwood', 'Escritora canadense, autora de "O Conto da Aia"'),
       (10, 'F. Scott Fitzgerald', 'Escritor americano, autor de "O Grande Gatsby"');


INSERT INTO leituras (id, authorname, pages_qtd, status, title, type, author_id, usuario_id)
VALUES (1, 'J.K. Rowling', 320, 'NAO_LIDO', 'Harry Potter e a Pedra Filosofal', 'LIVRO', 1, 1),
       (2, 'George Orwell', 198, 'LENDO', '1984', 'LIVRO', 2, 1),
       (3, 'Jane Austen', 400, 'LIDO', 'Orgulho e Preconceito', 'LIVRO', 3, 1),
       (4, 'Stephen King', 560, 'ABANDONADO', 'It', 'LIVRO', 4, 1),
       (5, 'Harper Lee', 336, 'LIDO', 'O Sol é para Todos', 'LIVRO', 5, 1),
       (6, 'Agatha Christie', 272, 'LENDO', 'O Assassinato no Expresso do Oriente', 'LIVRO', 6, 1),
       (7, 'Gabriel García Márquez', 368, 'NAO_LIDO', 'Cem Anos de Solidão', 'LIVRO', 7, 1),
       (8, 'J.R.R. Tolkien', 432, 'LENDO', 'O Senhor dos Anéis: A Sociedade do Anel', 'LIVRO', 8, 1),
       (9, 'Margaret Atwood', 324, 'NAO_LIDO', 'O Conto da Aia', 'LIVRO', 9, 1),
       (10, 'F. Scott Fitzgerald', 180, 'LIDO', 'O Grande Gatsby', 'LIVRO', 10, 1);

INSERT INTO anotacoes (id, annotation, date, leitura_id)
VALUES (1, 'Ótimo início da história!', '2023-11-12 10:30:00', 1),
       (2, 'Capítulo 3 é intrigante.', '2023-11-12 11:15:00', 2),
       (3, 'Final surpreendente.', '2023-11-12 12:00:00', 3),
       (4, 'Personagens assustadores.', '2023-11-12 13:45:00', 4),
       (5, 'Desenvolvimento do enredo é envolvente.', '2023-11-13 14:30:00', 1),
       (6, 'Personagens bem desenvolvidos.', '2023-11-13 15:45:00', 2),
       (7, 'A trama está ficando mais interessante.', '2023-11-13 16:30:00', 3),
       (8, 'Recomendaria este livro a outros.', '2023-11-13 17:15:00', 4),
       (9, 'Esperava mais do final.', '2023-11-14 09:30:00', 1),
       (10, 'Aprendi muito com este livro.', '2023-11-14 10:45:00', 2);

--
--------------------------------------------------------------------------------------------------
-- Script do PostgreSQL
-- Tabela de Usuarios
CREATE TABLE leituras
(
    id         SERIAL PRIMARY KEY,
    authorname VARCHAR(255),
    pages_qtd  INTEGER,
    status     VARCHAR(255) CHECK (status IN ('NAO_LIDO', 'LENDO', 'LIDO', 'ABANDONADO')),
    title      VARCHAR(255) UNIQUE,
    type       VARCHAR(255) CHECK (type IN
                                   ('LIVRO', 'EBOOK', 'PDF', 'ARTIGO', 'REVISTA', 'JORNAL', 'AUDIOBOOK', 'QUADRINHO',
                                    'BLOG', 'RELATORIO', 'TCC', 'DIARIO')),
    author_id  INTEGER,
    usuario_id INTEGER
);

CREATE TABLE autores
(
    id         SERIAL PRIMARY KEY,
    authorname VARCHAR(255),
    brief      VARCHAR(255)
);

CREATE TABLE anotacoes
(
    id         SERIAL NOT NULL,
    annotation VARCHAR(255),
    date       TIMESTAMP,
    leitura_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (leitura_id) REFERENCES leituras (id)
);

CREATE TABLE usuarios
(
    id       SERIAL PRIMARY KEY,
    password TEXT,
    username TEXT
);

CREATE UNIQUE INDEX uk_m2dvbwfge291euvmk6vkkocao
    ON usuarios (username);

INSERT INTO anotacoes_seq
VALUES (1);

INSERT INTO autores (id, authorname, brief)
VALUES (1, 'J.K. Rowling', 'Autora britânica famosa por Harry Potter'),
       (2, 'George Orwell', 'Escritor e jornalista inglês conhecido por 1984'),
       (3, 'Jane Austen', 'Romancista inglesa do século XIX'),
       (4, 'Stephen King', 'Autor de suspense e terror dos EUA'),
       (5, 'Harper Lee', 'Autora americana conhecida por "O Sol é para Todos"'),
       (6, 'Agatha Christie', 'Escritora britânica de romances policiais'),
       (7, 'Gabriel García Márquez', 'Escritor colombiano e Prêmio Nobel de Literatura'),
       (8, 'J.R.R. Tolkien', 'Autor britânico, criador de "O Senhor dos Anéis"'),
       (9, 'Margaret Atwood', 'Escritora canadense, autora de "O Conto da Aia"'),
       (10, 'F. Scott Fitzgerald', 'Escritor americano, autor de "O Grande Gatsby"');

INSERT INTO leituras (id, authorname, pages_qtd, status, title, type, author_id, usuario_id)
VALUES (1, 'J.K. Rowling', 320, 'NAO_LIDO', 'Harry Potter e a Pedra Filosofal', 'LIVRO', 1, 1),
       (2, 'George Orwell', 198, 'LENDO', '1984', 'LIVRO', 2, 1),
       (3, 'Jane Austen', 400, 'LIDO', 'Orgulho e Preconceito', 'LIVRO', 3, 1),
       (4, 'Stephen King', 560, 'ABANDONADO', 'It', 'LIVRO', 4, 1),
       (5, 'Harper Lee', 336, 'LIDO', 'O Sol é para Todos', 'LIVRO', 5, 1),
       (6, 'Agatha Christie', 272, 'LENDO', 'O Assassinato no Expresso do Oriente', 'LIVRO', 6, 1),
       (7, 'Gabriel García Márquez', 368, 'NAO_LIDO', 'Cem Anos de Solidão', 'LIVRO', 7, 1),
       (8, 'J.R.R. Tolkien', 432, 'LENDO', 'O Senhor dos Anéis: A Sociedade do Anel', 'LIVRO', 8, 1),
       (9, 'Margaret Atwood', 324, 'NAO_LIDO', 'O Conto da Aia', 'LIVRO', 9, 1),
       (10, 'F. Scott Fitzgerald', 180, 'LIDO', 'O Grande Gatsby', 'LIVRO', 10, 1);

INSERT INTO anotacoes (id, annotation, date, leitura_id)
VALUES (1, 'Ótimo início da história!', '2023-11-12 10:30:00', 1),
       (2, 'Capítulo 3 é intrigante.', '2023-11-12 11:15:00', 2),
       (3, 'Final surpreendente.', '2023-11-12 12:00:00', 3),
       (4, 'Personagens assustadores.', '2023-11-12 13:45:00', 4),
       (5, 'Desenvolvimento do enredo é envolvente.', '2023-11-13 14:30:00', 1),
       (6, 'Personagens bem desenvolvidos.', '2023-11-13 15:45:00', 2),
       (7, 'A trama está ficando mais interessante.', '2023-11-13 16:30:00', 3),
       (8, 'Recomendaria este livro a outros.', '2023-11-13 17:15:00', 4),
       (9, 'Esperava mais do final.', '2023-11-14 09:30:00', 1),
       (10, 'Aprendi muito com este livro.', '2023-11-14 10:45:00', 2);

--------------------------------------------------------------------------------------------------