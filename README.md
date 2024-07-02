# clever_backend_check_2024

## Задача 2

### Запуск приложения в консоли:

    ```
    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 1-7 9-9 12-10 discountCard=3333 balanceDebitCard=1000 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv 
    ```

### Реализация

Для удобства файл с данными о товарах был размещен в папке resources, также как и saveToFile.

1. Реализовано считывание списка товаров из внешнего файла.
2. Реализовано сохранение данных чека в указанный CSV-файл.
3. Данные по картам хранятся в resources/discountCards.csv.
4. Итоговый чек сохраняется в result.csv.
5. Формат ввода:

    ```
    id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=XXXX saveToFile=xxxx
    ```

   где:
    - id - идентификатор товара;
    - quantity - количество товара;
    - discountCard=xxxx - название и номер дисконтной карты;
    - balanceDebitCard=xxxx - баланс на дебетовой карте;
    - pathToFile - файл, откуда читаются данные о продуктах;
    - saveToFile - файл, куда созраняется чек.

6. Если в чеке есть оптовые товары и количество оптового товара >= 5, то на каждый такой товар налагается скидка в
   размере 10%.
7. Если присутсвует дисконтная карта, на все товары, кроме оптовых, которых >= 5, налагается скидка, соответствующая
   карте.

### Тестирование

#### Некорректные данные

##### 1. Отсутсвие баланса в запросе

```
1-7 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

```
1-7 9-9 12-100 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

```
1-7 9-9 12-100 discountCard=3333 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

Сообщение:

```
BAD REQUEST
```

##### 2. Неизвестная дисконтная карта

```
1-7 9-9 12-100 discountCard=3334 balanceDebitCard=100 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

Сообщение:

```
BAD REQUEST
```

##### 3. Неизвестный продукт

```
1-7 9-9 120-100 discountCard=3333 balanceDebitCard=100 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

Сообщение:

```
BAD REQUEST
```

##### 4. Нехватка средств на счету

```
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=100 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

Сообщение:

```
NOT ENOUGH MONEY
```

##### 5. Отсутствие pathToFile

```
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=100 saveToFile=src/main/resources/saveToFile.csv
```

Сообщение:

```
BAD REQUEST
```

Содержание файла saveToFile.csv:

```
ERROR;
BAD REQUEST;
```

##### 6. Отсутствие saveToFile

```
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=100 pathToFile=src/main/resources/products.csv
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

##### 7. Отсутствие pathToFile и saveToFile

```
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=100
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

#### Корректные данные

##### 1. Результат с дисконтной картой

```
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=1000 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

```
DATE: 03.07.2024
TIME: 00:26:26
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

Содержание файла saveToFile.csv:

```
DATE;TIME;
03.07.2024;00:26:26

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
7;Milk;1.07$;0.75$;7.49$;
9;Packed bananas 1kg;1.10$;0.99$;9.90$;
10;Packed chicken breasts 1kg;10.75$;4.30$;107.50$;

DISCOUNT CARD;DISCOUNT PERCENTAGE;
3333;4%;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
124.89$;6.04$;118.85$;
```

##### 2. Результат без дисконтной карты

```
1-7 9-9 12-10 balanceDebitCard=1000 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/saveToFile.csv
```

```
DATE: 03.07.2024
TIME: 00:28:44
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

Содержание файла saveToFile.csv:

```
DATE;TIME;
03.07.2024;00:28:44

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
7;Milk;1.07$;0.75$;7.49$;
9;Packed bananas 1kg;1.10$;0.99$;9.90$;
10;Packed chicken breasts 1kg;10.75$;0$;107.50$;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
124.89$;1.74$;123.15$;
```