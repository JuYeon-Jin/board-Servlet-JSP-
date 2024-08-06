show databases;
use ebrainsoft_study;
show tables;

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);


-- INSERT INTO category (name) VALUES ('Java'), ('JavaScript'), ('C++'), ('Python'), ('Database');

CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    is_deleted boolean NOT NULL DEFAULT false,
    writer VARCHAR(255) NOT NULL,
    views int NOT NULL DEFAULT 0,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE
);
-- INSERT INTO posts (category_id, title, content, writer, password)
-- VALUES (2, '첫 번째 게시물', '첫 번째 게시물의 내용입니다.', '홍길동', 'password');
SELECT p.id, p.category_id, c.name AS category_name, p.title, p.content, p.created_at, p.updated_at, p.is_deleted, p.writer, p.views, p.password FROM posts AS p JOIN category AS c ON p.category_id = c.id;

SELECT * FROM category;
SELECT * FROM posts;
