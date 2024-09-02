# clever_backend_check_2024

## Задача 4

### Запуск

- Для запуска необходим Tomcat 10.1.25;
- Создать артефакт (Output directory .../build/libs/clevertec-check-1.0.war);
- Указать артефакт в разделе deployment.

### Реализация

Реализовано RESTFUL - API (Servlet).

1. Реализовано добавление товаров и карт по REST (реализованы CRUD на эти две таблицы).
2. При формировании чека товары в БД уменьшаются на запрошенное количество.
3. Такие параметры как driver, url, username и password хранятся в application.yml.
4. DDL/DML операции хранятся в файле src/main/resources/data.sql
5. Если в чеке есть оптовые товары и количество оптового товара >= 5, то на каждый такой товар налагается скидка в
   размере 10%.
6. Если присутсвует дисконтная карта, на все товары, кроме оптовых, которых >= 5, налагается скидка, соответствующая
   карте.
7. Учтены возможные дубликаты товаров в чеке.
8. Ограничения по бд:
   - price - должна хранится до 2 знаков, сокращать по правилам математики (например: 1,20) (из задачи);
   - 0 <= discount amount <= 100 (из задачи);
   - поле number таблицы discount_card является уникальным.

### Тестирование

#### ProductController

##### 1. Поиск по id (GET)

Запрос:

```http request
http://localhost:8080/products?id=1
```

Результат:

```json
{
   "id": 1,
   "description": "Milk",
   "price": 1.07,
   "quantity": 10,
   "isWholesale": true
}
```

Ошибка:

```http request
404
```

##### 2. Добавление нового продукта (POST)

Запрос:

```http request
http://localhost:8080/products
```

Тело запроса:

```json
{
   "description": "Milk Chocolate",
   "price": 1.06,
   "quantity": 100,
   "isWholesale": true
}
```

Результат:

```json
{
   "id": 21,
   "description": "Milk Chocolate",
   "price": 1.06,
   "quantity": 100,
   "isWholesale": true
}
```

Ошибка:

```http request
500
```

##### 3. Обновление продукта (PUT)

Запрос:

```http request
http://localhost:8080/products?id=21
```

Тело запроса:

```json
{
   "id": 21,
   "description": "Milk Chocolate",
   "price": 1.21,
   "quantity": 100,
   "isWholesale": true
}
```

Результат:

```json
{
   "id": 21,
   "description": "Milk Chocolate",
   "price": 1.21,
   "quantity": 100,
   "isWholesale": true
}
```

Ошибка:

```http request
500
```

##### 4. Удаление продукта (DELETE)

Запрос:

```http request
http://localhost:8080/products?id=21
```

Результат:

```http request
200
```

Ошибка:

```http request
500
```

#### DiscountCardController

##### 1. Поиск по id (GET)

Запрос:

```http request
http://localhost:8080/discountcards?id=1
```

Результат:

```json
{
   "id": 1,
   "discountCard": 1111,
   "discountAmount": 3
}
```

Ошибка:

```http request
404
```

##### 2. Добавление новой карты (POST)

Запрос:

```http request
http://localhost:8080/discountcards
```

Тело запроса:

```json
{
   "discountCard": 6786,
   "discountAmount": 3
}
```

Результат:

```json
{
   "id": 5,
   "discountCard": 6786,
   "discountAmount": 6786
}
```

Ошибка:

```http request
500
```

##### 3. Обновление карты (PUT)

Запрос:

```http request
http://localhost:8080/discountcards?id=5
```

Тело запроса:

```json
{
   "id": 5,
   "discountCard": 6666,
   "discountAmount": 3
}
```

Результат:

```json
{
   "id": 5,
   "discountCard": 6666,
   "discountAmount": 3
}
```

Ошибка:

```http request
500
```

##### 4. Удаление карты (DELETE)

Запрос:

```http request
http://localhost:8080/discountcards?id=5
```

Результат:

### [Задача 1 ветка: feature/entry-core](https://github.com/vitmvit/clever_backend_check_2024/tree/feature/entry-core)

### [Задача 2 ветка: feature/entry-file](https://github.com/vitmvit/clever_backend_check_2024/tree/feature/entry-file)

### Задача 3 ветка: feature/entry-database

### Задача 4 ветка: feature/entry-rest

### Задача 5 ветка: feature/custom