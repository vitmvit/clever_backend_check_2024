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

```http request
200
```

Ошибка:

```http request
500
```

#### DiscountCardController

##### 1. Печать чека (POST) с дисконтной картой

Запрос:

```http request
http://localhost:8080/check
```

Тело запроса:

```json
{
   "products": [
      {
         "id": 2,
         "quantity": 15
      },
      {
         "id": 1,
         "quantity": 5
      }
   ],
   "discountCard": 1111,
   "balanceDebitCard": 10000
}
```

Результат (содержимое файла):

```
DATE;TIME;
04.07.2024;19:50:50

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
5;Milk;1.07$;0.54$;5.35$;
15;Cream 400g ;2.71$;4.07$;40.65$;

DISCOUNT CARD;DISCOUNT PERCENTAGE;
1111;3%;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
46.00$;4.61$;41.39$;
```

Ошибка:

```http request
500
```

##### 2. Печать чека (POST) без дисконтной карты

Запрос:

```http request
http://localhost:8080/check
```

Тело запроса:

```json
{
   "products": [
      {
         "id": 9,
         "quantity": 1
      },
      {
         "id": 3,
         "quantity": 1
      }
   ],
   "balanceDebitCard": 10000
}
```

Результат (содержимое файла):

```
DATE;TIME;
05.07.2024;01:21:29

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
1;Yogurt 400g ;2.10$;0$;2.10$;
1;Packed bananas 1kg;1.10$;0$;1.10$;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
3.20$;0$;3.20$;
```

Ошибка:

```http request
500
```


