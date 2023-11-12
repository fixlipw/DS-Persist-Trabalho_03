--------------------------------------------------------------------------------------------------
-- Script para o SQLite
-- Tabela de Usuários
CREATE TABLE Usuarios (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          user VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL
);

-- Tabela de Autores
CREATE TABLE Autores (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         authorName VARCHAR(255) NOT NULL,
                         brief TEXT
);

-- Tabela de Leituras
CREATE TABLE Leituras (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          title VARCHAR(255) NOT NULL,
                          pagesQtd INT,
                          type VARCHAR(255) NOT NULL,
                          status VARCHAR(255) NOT NULL,
                          usuario_id INT,
                          FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);

-- Tabela de Anotações
CREATE TABLE Anotacoes (
                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                           date DATETIME NOT NULL,
                           annotation TEXT,
                           leitura_id INT,
                           FOREIGN KEY (leitura_id) REFERENCES Leituras(id)
);

-- Tabela de Junção para Leituras e Autores (Muitos para Muitos)
CREATE TABLE LeiturasAutores (
                                 leitura_id INT,
                                 autor_id INT,
                                 PRIMARY KEY (leitura_id, autor_id),
                                 FOREIGN KEY (leitura_id) REFERENCES Leituras(id),
                                 FOREIGN KEY (autor_id) REFERENCES Autores(id)
);
--
--------------------------------------------------------------------------------------------------
-- Script do PostgreSQL
-- Tabela de Usuarios
CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          "user" VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL
);

-- Tabela de Autores
CREATE TABLE autores (
                         id SERIAL PRIMARY KEY,
                         authorName VARCHAR(255) NOT NULL,
                         brief TEXT
);

-- Tabela de Leituras
CREATE TABLE leituras (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          pagesQtd INT,
                          type VARCHAR(255) NOT NULL,
                          status VARCHAR(255) NOT NULL,
                          usuario_id INT,
                          FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Anotacoes
CREATE TABLE anotacoes (
                           id SERIAL PRIMARY KEY,
                           date TIMESTAMP NOT NULL,
                           annotation TEXT,
                           leitura_id INT,
                           FOREIGN KEY (leitura_id) REFERENCES leituras(id)
);

-- Tabela de Juncao para Leituras e Autores (Muitos para Muitos)
CREATE TABLE leiturasautores (
                                  leitura_id INT,
                                  autor_id INT,
                                  PRIMARY KEY (leitura_id, autor_id),
                                  FOREIGN KEY (leitura_id) REFERENCES leituras(id),
                                  FOREIGN KEY (autor_id) REFERENCES autores(id)
);
--------------------------------------------------------------------------------------------------