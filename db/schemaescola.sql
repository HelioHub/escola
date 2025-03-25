-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS escola;
USE escola;

-- Tabela ALUNO
CREATE TABLE IF NOT EXISTS aluno (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_nasc DATE
);

-- Tabela CURSO
CREATE TABLE IF NOT EXISTS curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    carga_horaria INT
);

-- Tabela PROFESSOR
CREATE TABLE IF NOT EXISTS professor (
    id_prof INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    area_atuacao VARCHAR(50)
);

-- Tabela MATRÍCULA (relacionamento ALUNO x CURSO)
CREATE TABLE IF NOT EXISTS matricula (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT,
    id_curso INT,
    data_matricula DATE DEFAULT current_timestamp,
    status ENUM('ativo', 'inativo', 'concluído') DEFAULT 'ativo',
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);

-- Tabela PROFESSOR_CURSO (relacionamento PROFESSOR x CURSO)
CREATE TABLE IF NOT EXISTS professor_curso (
    id_prof_curso INT AUTO_INCREMENT PRIMARY KEY,
    id_prof INT,
    id_curso INT,
    FOREIGN KEY (id_prof) REFERENCES professor(id_prof),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);