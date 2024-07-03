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
1-7 discountCard=3333 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root
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
1-7 9-9 12-10 discountCard=3334 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
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
1-7 9-9 12-100 discountCard=3333 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
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
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=1 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
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

##### 5. Отсутствие saveToFile

```
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=10000 datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
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
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
```

```
DATE: 03.07.2024
TIME: 16:57:03
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
03.07.2024;16:57:03

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
1-7 9-9 12-10 discountCard=3333 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
```

```
DATE: 03.07.2024
TIME: 16:58:54
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
03.07.2024;16:58:54

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
7;Milk;1.07$;0.75$;7.49$;
9;Packed bananas 1kg;1.10$;0.99$;9.90$;
10;Packed chicken breasts 1kg;10.75$;0$;107.50$;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
124.89$;1.74$;123.15$;
```

##### 3. Результат с дубликатом товара в запросе

```
1-7 9-2 12-10 9-1 7-1 1-2 discountCard=3333 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root"
```

```
DATE: 03.07.2024
TIME: 17:00:35
+---------+--------------------------------+----------------+--------------+--------------+
|   QTY   |          DESCRIPTION           |     PRICE      |   DISCOUNT   |     TOTAL    |
+---------+--------------------------------+----------------+--------------+--------------+
|       9 | Milk                           |           1.07 |         0.96 |         9.63 |
|       1 | Packed apples 1kg              |           2.78 |         0.11 |         2.78 |
|       3 | Packed bananas 1kg             |           1.10 |         0.13 |         3.30 |
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
|         123.21 |             5.50 |               117.71 |
+----------------+------------------+----------------------+
```

Содержание файла saveToFile.csv:

```
DATE;TIME;
03.07.2024;17:00:35

QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;
9;Milk;1.07$;0.96$;9.63$;
1;Packed apples 1kg;2.78$;0.11$;2.78$;
3;Packed bananas 1kg;1.10$;0.13$;3.30$;
10;Packed chicken breasts 1kg;10.75$;4.30$;107.50$;

DISCOUNT CARD;DISCOUNT PERCENTAGE;
3333;4%;

TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;
123.21$;5.50$;117.71$;
```