# clever_backend_check_2024

## Задача 1

### Запуск приложения в консоли:

    ```
    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 1-7 9-9 12-10 discountCard=3333 balanceDebitCard=1000
    ```

### Реализация

1. Данные по товарам хранятся в resources/products.csv.
2. Данные по картам хранятся в resources/discountCards.csv.
3. Итоговый чек сохраняется в result.csv.
4. Формат ввода:

    ```
    id-quantity discountCard=xxxx balanceDebitCard=xxxx
    ```

   где:
    - id - идентификатор товара;
    - quantity - количество товара;
    - discountCard=xxxx - название и номер дисконтной карты;
    - balanceDebitCard=xxxx - баланс на дебетовой карте;

5. Реализован паттерн Фабричный метод (смотреть Reader.java).
6. Если в чеке есть оптовые товары и количество оптового товара >= 5, то на каждый такой товар налагается скидка в
   размере 10%.
7. Если присутсвует дисконтная карта, на все товары, кроме оптовых, которых >= 5, налагается скидка, соответствующая
   карте.

### Тестирование

#### Некорректные данные

##### 1. Отсутсвие баланса в запросе

```
1-7
```

```
1-7 9-9 12-100
```

```
1-7 9-9 12-100 discountCard=3333
```

Сообщение:

```
BAD REQUEST
```

Содержание файла result.csv:

```
ERROR;
BAD REQUEST;
```

##### 2. Неизвестная дисконтная карта

```
1-7 9-9 12-100 discountCard=3334 balanceDebitCard=100
```

Сообщение:

```
BAD REQUEST
```

Содержание файла result.csv:

```
ERROR;
BAD REQUEST;
```

##### 3. Неизвестный продукт

```
1-7 9-9 120-100 discountCard=3333 balanceDebitCard=100
```

Сообщение:

```
BAD REQUEST
```

Содержание файла result.csv:

```
ERROR;
BAD REQUEST;
```

##### 4. Нехватка средств на счету

```
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=100
```

Сообщение:

```
NOT ENOUGH MONEY
```

Содержание файла result.csv:

```
ERROR;
NOT ENOUGH MONEY;
```

#### Корректные данные

##### 1. Результат с дисконтной картой

```
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=1000
```

```
DATE: 02.07.2024
TIME: 16:06:52
+---------+--------------------------------+----------------+--------------+--------------+
|   QTY   |          DESCRIPTION           |     PRICE      |   DISCOUNT   |     TOTAL    |
+---------+--------------------------------+----------------+--------------+--------------+
|       7 | Milk                           |           1.07 |         0.75 |         7.49 |
|       9 | Packed bananas 1kg             |           1.10 |         0.99 |         9.90 |
|      10 | Packed chicken breasts 1kg     |          10.75 |         4.30 |       107.50 |
+---------+-------+------------------------+-------+--------+--------------+--------------+
+-----------------+--------------------------------+
|  DISCOUNT CARD  | DISCOUNT PERCENTAGE            |
+-----------------+--------------------------------+
|            3333 | 4                              |
+-----------------+--------------------------------+
+----------------+------------------+----------------------+
|    TOTAL PRICE |  TOTAL DISCOUNT  |  TOTAL WITH DISCOUNT |
+----------------+------------------+----------------------+
|         124.89 |             6.04 |               118.85 |
+----------------+------------------+----------------------+

```

##### 2. Результат без дисконтной карты

```
1-7 9-9 12-10 balanceDebitCard=1000
```

```
DATE: 02.07.2024
TIME: 16:08:31
+---------+--------------------------------+----------------+--------------+--------------+
|   QTY   |          DESCRIPTION           |     PRICE      |   DISCOUNT   |     TOTAL    |
+---------+--------------------------------+----------------+--------------+--------------+
|       7 | Milk                           |           1.07 |         0.75 |         7.49 |
|       9 | Packed bananas 1kg             |           1.10 |         0.99 |         9.90 |
|      10 | Packed chicken breasts 1kg     |          10.75 |            0 |       107.50 |
+---------+-------+------------------------+-------+--------+--------------+--------------+
+----------------+------------------+----------------------+
|    TOTAL PRICE |  TOTAL DISCOUNT  |  TOTAL WITH DISCOUNT |
+----------------+------------------+----------------------+
|         124.89 |             1.74 |               123.15 |
+----------------+------------------+----------------------+

```

##### 3. Результат с дубликатом товара в запросе

```
1-7 9-1 1-1 9-3 7-1 9-1 discountCard=3333 balanceDebitCard=1000
```

```
DATE: 03.07.2024
TIME: 17:17:38
+---------+--------------------------------+----------------+--------------+--------------+
|   QTY   |          DESCRIPTION           |     PRICE      |   DISCOUNT   |     TOTAL    |
+---------+--------------------------------+----------------+--------------+--------------+
|       8 | Milk                           |           1.07 |         0.86 |         8.56 |
|       1 | Packed apples 1kg              |           2.78 |         0.11 |         2.78 |
|       5 | Packed bananas 1kg             |           1.10 |         0.55 |         5.50 |
+---------+-------+------------------------+-------+--------+--------------+--------------+
+-----------------+--------------------------------+
|  DISCOUNT CARD  | DISCOUNT PERCENTAGE            |
+-----------------+--------------------------------+
|            3333 | 4                              |
+-----------------+--------------------------------+
+----------------+------------------+----------------------+
|    TOTAL PRICE |  TOTAL DISCOUNT  |  TOTAL WITH DISCOUNT |
+----------------+------------------+----------------------+
|          16.84 |             1.52 |                15.32 |
+----------------+------------------+----------------------+
```