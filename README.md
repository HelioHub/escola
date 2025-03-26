# Projeto Gestão Acadêmica UNIFOR - Hélio Marques

 Desenvolver uma aplicação web responsiva para administrar alunos, professores e cursos. 

## Diagrama de Entidade e Relacionamento 

![## escola](https://github.com/HelioHub/escola/blob/main/images/DER.png)

## DB MySQL

	MySQL Installer 5.7.44
	https://dev.mysql.com/downloads/installer/
	root/wk@123
	Service Name: MySQL57
	DataBase: escola
	
## GITHub

	echo "# escola" >> README.md
	git init
	git add README.md
	git commit -m "first commit"
	git branch -M main
	git remote add origin git@github.com:HelioHub/escola.git
	git push -u origin main
	
## Schema DB

	-- Criação do banco de dados
	-- CREATE DATABASE IF NOT EXISTS escola;
	-- USE escola;

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
		data_matricula DATETIME DEFAULT current_timestamp,
		status ENUM('ativo', 'inativo', 'concluído') DEFAULT 'ativo',
		FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
		FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
	);

	-- Tabela USUÁRIO 
	CREATE TABLE IF NOT EXISTS usuario (
		id_user INT AUTO_INCREMENT PRIMARY KEY,
		email VARCHAR(100) UNIQUE NOT NULL,
		senha_hash VARCHAR(255) NOT NULL,  -- Armazenar senha criptografada (ex: bcrypt)
		tipo ENUM('admin', 'coordenador', 'professor', 'aluno') NOT NULL,
		id_relacion INT NULL,  -- Opcional: ligação com ID de aluno/professor
		data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		FOREIGN KEY (id_relacion) REFERENCES aluno(id_aluno) ON DELETE CASCADE,
		FOREIGN KEY (id_relacion) REFERENCES professor(id_prof) ON DELETE CASCADE
	);

	-- Tabela DISCIPLINA (nova)
	CREATE TABLE IF NOT EXISTS disciplina (
		id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
		nome VARCHAR(100) NOT NULL,
		carga_horaria INT NOT NULL,
		ementa TEXT,
		id_professor INT,  -- Professor responsável pela disciplina
		FOREIGN KEY (id_professor) REFERENCES professor(id_prof)
	);

	-- Tabela MATRIZ_CURRICULAR (nova) - Relaciona Curso com Disciplinas por semestre
	CREATE TABLE IF NOT EXISTS matriz_curricular (
		id_matriz INT AUTO_INCREMENT PRIMARY KEY,
		id_curso INT NOT NULL,
		id_disciplina INT NOT NULL,
		semestre INT NOT NULL,  -- 1, 2, 3... para ordenação
		obrigatoria BOOLEAN DEFAULT TRUE,
		FOREIGN KEY (id_curso) REFERENCES curso(id_curso),
		FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina)
	);

	-- Removendo a tabela professor_curso (agora o relacionamento é via disciplina)
	DROP TABLE IF EXISTS professor_curso;

	INSERT INTO aluno (nome, email, telefone, data_nasc) 
	VALUES ('João Silva', 'joao@email.com', '11999999999', '2000-05-15');

	INSERT INTO curso (nome, descricao, carga_horaria) 
	VALUES ('Programação Python', 'Curso introdutório de Python', 40);

	-- Admin (sem vínculo com outras tabelas)
	INSERT INTO usuario (email, senha_hash, tipo) 
	VALUES ('admin@escola.com', '$2a$10$hashedpassword123', 'admin');

	-- Aluno (vinculado a id_aluno = 1)
	INSERT INTO usuario (email, senha_hash, tipo, id_relacion) 
	VALUES ('joao@email.com', '$2a$10$hashedpassword456', 'aluno', 1);

	-- Exemplo de relacionamento opcional:
	-- Se id_relacion = 1 e tipo = 'aluno', vincula ao aluno de id_aluno = 1

	-- Inserindo professores básicos
	INSERT INTO professor (nome, email, telefone, area_atuacao)
	VALUES 
		('Carlos Silva', 'carlos.silva@escola.com', '(11) 9999-8888', 'Banco de Dados'),
		('Ana Oliveira', 'ana.oliveira@escola.com', '(11) 7777-6666', 'Programação'),
		('Marcos Souza', 'marcos.souza@escola.com', '(11) 5555-4444', 'Redes de Computadores');

	-- Inserindo com todos os campos (incluindo campos opcionais)
	INSERT INTO professor (nome, email, telefone, area_atuacao)
	VALUES 
		('Juliana Santos', 'juliana.santos@escola.com', '(11) 3333-2222', 'Inteligência Artificial'),
		('Roberto Ferreira', 'roberto.ferreira@escola.com', NULL, 'Matemática Aplicada');  -- Telefone opcional

	-- Inserindo vinculado a um usuário (se id_relacion estiver configurado)
	-- Primeiro insere o usuário:
	INSERT INTO usuario (email, senha_hash, tipo, id_relacion)
	VALUES 
		('carlos.silva@escola.com', '$2a$10$hashedpassword123', 'professor', 1);  -- id_relacion = id_prof

	-- Depois atualiza o professor com o id_user (opcional):
	UPDATE professor SET id_user = 1 WHERE id_prof = 1;

	-- Cadastrando uma disciplina com professor
	INSERT INTO disciplina (nome, carga_horaria, ementa, id_professor)
	VALUES ('Banco de Dados', 60, 'Fundamentos de SQL', 1);
	INSERT INTO disciplina (nome, carga_horaria, ementa, id_professor)
	VALUES ('Java', 60, 'Fundamentos de SQL', 1);

	-- Adicionando disciplina à matriz do curso de ADS (id_curso=1)
	INSERT INTO matriz_curricular (id_curso, id_disciplina, semestre)
	VALUES (1, 2, 3);  -- 3º semestre

	-- Consultando a matriz de um curso
	SELECT d.nome, p.nome AS professor, m.semestre
	FROM matriz_curricular m
	JOIN disciplina d ON m.id_disciplina = d.id_disciplina
	JOIN professor p ON d.id_professor = p.id_prof
	WHERE m.id_curso = 1
	ORDER BY m.semestre;

## Backend API REST com Quarkus para Gestão Acadêmica

	Solução seguindo os requisitos, utilizando Quarkus, MySQL, Keycloak e Docker. 
	O projeto estruturado usando Design Patterns e testes unitários.
	
	Estrutura do Projeto
	
	escola-api/
	├── src/
	│   ├── main/
	│   │   ├── java/com/escola/
	│   │   │   ├── config/
	│   │   │   ├── controller/
	│   │   │   ├── dto/
	│   │   │   ├── exception/
	│   │   │   ├── model/
	│   │   │   ├── repository/
	│   │   │   ├── service/
	│   │   │   └── security/
	│   │   └── resources/
	│   └── test/
	├── docker-compose.yml
	├── Dockerfile
	└── pom.xml	
	
	
## Configuração do Projeto

	pom.xml:
	
	<?xml version="1.0"?>
	<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	  <modelVersion>4.0.0</modelVersion>
	  <groupId>com.escola</groupId>
	  <artifactId>escola-api</artifactId>
	  <version>1.0.0</version>
	  <properties>
		<compiler-plugin.version>3.8.1</compiler-plugin.version>
		<maven.compiler.parameters>true</maven.compiler.parameters>
		<maven.compiler.source>17</maven.compiler.source>
		<maven.compiler.target>17</maven.compiler.target>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
		<quarkus.platform.group-id>io.quarkus</quarkus.platform.group-id>
		<quarkus.platform.version>3.6.4</quarkus.platform.version>
		<surefire-plugin.version>3.0.0</surefire-plugin.version>
	  </properties>
	  <dependencyManagement>
		<dependencies>
		  <dependency>
			<groupId>${quarkus.platform.group-id}</groupId>
			<artifactId>${quarkus.platform.artifact-id}</artifactId>
			<version>${quarkus.platform.version}</version>
			<type>pom</type>
			<scope>import</scope>
		  </dependency>
		</dependencies>
	  </dependencyManagement>
	  <dependencies>
		<!-- Quarkus -->
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
		</dependency>
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-hibernate-orm-panache</artifactId>
		</dependency>
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-jdbc-mysql</artifactId>
		</dependency>
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-smallrye-openapi</artifactId>
		</dependency>
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-arc</artifactId>
		</dependency>
		
		<!-- Security -->
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-oidc</artifactId>
		</dependency>
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-keycloak-authorization</artifactId>
		</dependency>
		
		<!-- Test -->
		<dependency>
		  <groupId>io.quarkus</groupId>
		  <artifactId>quarkus-junit5</artifactId>
		  <scope>test</scope>
		</dependency>
		<dependency>
		  <groupId>io.rest-assured</groupId>
		  <artifactId>rest-assured</artifactId>
		  <scope>test</scope>
		</dependency>
		<dependency>
		  <groupId>org.mockito</groupId>
		  <artifactId>mockito-core</artifactId>
		  <scope>test</scope>
		</dependency>
	  </dependencies>
	  <build>
		<plugins>
		  <plugin>
			<groupId>${quarkus.platform.group-id}</groupId>
			<artifactId>quarkus-maven-plugin</artifactId>
			<version>${quarkus.platform.version}</version>
			<executions>
			  <execution>
				<goals>
				  <goal>build</goal>
				  <goal>generate-code</goal>
				  <goal>generate-code-tests</goal>
				</goals>
			  </execution>
			</executions>
		  </plugin>
		  <plugin>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>${compiler-plugin.version}</version>
		  </plugin>
		  <plugin>
			<artifactId>maven-surefire-plugin</artifactId>
			<version>${surefire-plugin.version}</version>
			<configuration>
			  <systemPropertyVariables>
				<java.util.logging.manager>org.jboss.logmanager.LogManager</java.util.logging.manager>
				<maven.home>${maven.home}</maven.home>
			  </systemPropertyVariables>
			</configuration>
		  </plugin>
		</plugins>
	  </build>
	</project>
	
	