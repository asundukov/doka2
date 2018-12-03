truncate item_title;
INSERT INTO item_title (title, item_type, title_type, sex, min_level, max_level)
VALUES
       ('', 'TOOL', 'FIRST', 'MALE', 1, 30),
       ('', 'WEAPON', 'FIRST', 'MALE', 1, 30),
       ('', 'TOOL', 'FIRST', 'FEMALE', 1, 30),
       ('', 'WEAPON', 'FIRST', 'FEMALE', 1, 30),

       ('', 'TOOL', 'LAST', 'MALE', 1, 30),
       ('', 'WEAPON', 'LAST', 'MALE', 1, 30),
       ('', 'TOOL', 'LAST', 'FEMALE', 1, 30),
       ('', 'WEAPON', 'LAST', 'FEMALE', 1, 30),

       ('Ярости', 'TOOL', 'LAST', 'MALE', 5, 50),
       ('Ярости', 'WEAPON', 'LAST', 'MALE', 5, 50),
       ('Ярости', 'TOOL', 'LAST', 'FEMALE', 5, 50),
       ('Ярости', 'WEAPON', 'LAST', 'FEMALE', 5, 50),

       ('Обычный', 'TOOL', 'FIRST', 'MALE', 1, 30),
       ('Качественный', 'TOOL', 'FIRST', 'MALE', 1, 50),
       ('Необыкновенный', 'TOOL', 'FIRST', 'MALE', 2, 50),

       ('Обычная', 'TOOL', 'FIRST', 'FEMALE', 1, 30),
       ('Качественная', 'TOOL', 'FIRST', 'FEMALE', 1, 50),
       ('Необыкновенная', 'TOOL', 'FIRST', 'FEMALE', 2, 50),

       ('Обычный', 'WEAPON', 'FIRST', 'MALE', 1, 30),
       ('Качественный', 'WEAPON', 'FIRST', 'MALE', 1, 50),
       ('Необыкновенный', 'WEAPON', 'FIRST', 'MALE', 2, 50),

       ('Обычная', 'WEAPON', 'FIRST', 'FEMALE', 1, 30),
       ('Качественная', 'WEAPON', 'FIRST', 'FEMALE', 1, 40),
       ('Необыкновенная', 'WEAPON', 'FIRST', 'FEMALE', 2, 50),

       ('Обычное', 'WEAPON', 'FIRST', 'UNKNOWN', 1, 30),
       ('Качественное', 'WEAPON', 'FIRST', 'UNKNOWN', 1, 40),
       ('Необыкновенное', 'WEAPON', 'FIRST', 'UNKNOWN', 2, 50),
       ('Вонючее', 'WEAPON', 'FIRST', 'UNKNOWN', 1, 3),

       ('Отвертка', 'TOOL', 'MAIN', 'FEMALE', 1, 5),
       ('Стамеска', 'TOOL', 'MAIN', 'FEMALE', 2, 10),
       ('Напильник', 'TOOL', 'MAIN', 'MALE', 3, 15),
       ('Молоток', 'TOOL', 'MAIN', 'MALE', 4, 20),
       ('Плоскогубцы', 'TOOL', 'MAIN', 'FEMALE', 5, 50),

       ('Столовый нож', 'WEAPON', 'MAIN', 'MALE', 1, 2),
       ('Кухонный нож', 'WEAPON', 'MAIN', 'MALE', 2, 5),
       ('Разделочный топор', 'WEAPON', 'MAIN', 'MALE', 3, 10),
       ('Травмат', 'WEAPON', 'MAIN', 'MALE', 6, 15),
       ('Топор', 'WEAPON', 'MAIN', 'MALE', 4, 20),
       ('Пневматический пистолет', 'WEAPON', 'MAIN', 'MALE', 3, 6),
       ('Пистолет', 'WEAPON', 'MAIN', 'MALE', 10, 30),
       ('Ружье', 'WEAPON', 'MAIN', 'UNKNOWN', 15, 50),
       ('Автомат', 'WEAPON', 'MAIN', 'MALE', 35, 100)
  ;
