DROP TABLE IF EXISTS "discount_card";
DROP SEQUENCE IF EXISTS discount_card_id_seq;
CREATE SEQUENCE discount_card_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."discount_card"
(
    "id"     bigint DEFAULT nextval('discount_card_id_seq') NOT NULL,
    "number" integer UNIQUE,
    "amount" smallint,
    CONSTRAINT "discount_card_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE TABLE "public"."discount_card"
(
    "id"     bigint DEFAULT nextval('discount_card_id_seq') NOT NULL,
    "number" integer,
    "amount" smallint,
    CONSTRAINT "discount_card_number_key" UNIQUE ("number"),
    CONSTRAINT "discount_card_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

DROP TABLE IF EXISTS "product";
DROP SEQUENCE IF EXISTS product_id_seq;
CREATE SEQUENCE product_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."product"
(
    "id"                bigint DEFAULT nextval('product_id_seq') NOT NULL,
    "description"       character varying(50),
    "price"             numeric(10, 2),
    "quantity_in_stock" integer,
    "wholesale_product" boolean,
    CONSTRAINT "product_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

TRUNCATE "product";
INSERT INTO "product" ("id", "description", "price", "quantity_in_stock", "wholesale_product")
VALUES (2, 'Cream 400g ', 2.71, 20, 't'),
       (3, 'Yogurt 400g ', 2.10, 7, 't'),
       (9, 'Packed bananas 1kg', 1.10, 25, 't'),
       (13, 'Baguette 360g', 1.30, 10, 't'),
       (17, 'Chocolate Ritter sport 100g', 1.10, 50, 't'),
       (4, 'Packed potatoes 1kg', 1.47, 30, 'f'),
       (5, 'Packed cabbage 1kg', 1.19, 15, 'f'),
       (6, 'Packed tomatoes 350g', 1.60, 50, 'f'),
       (7, 'Packed apples 1kg', 2.78, 18, 'f'),
       (8, 'Packed oranges 1kg', 3.20, 12, 'f'),
       (10, 'Packed beef fillet 1kg', 12.80, 7, 'f'),
       (11, 'Packed pork fillet 1kg', 8.52, 14, 'f'),
       (12, 'Packed chicken breasts 1kg', 10.75, 18, 'f'),
       (14, 'Drinking water 1,5l', 0.80, 100, 'f'),
       (15, 'Olive oil 500ml', 5.30, 16, 'f'),
       (16, 'Sunflower oil 1l', 1.20, 12, 'f'),
       (18, 'Paulaner 0,5l', 1.10, 100, 'f'),
       (19, 'Whiskey Jim Beam 1l', 13.99, 30, 'f'),
       (20, 'Whiskey Jack Daniels 1l', 17.19, 20, 'f'),
       (1, 'Milk', 1.07, 10, 't');

ALTER TABLE discount_card
    ADD CHECK (amount >= 0);
ALTER TABLE discount_card
    ADD CHECK (amount <= 100);
ALTER TABLE product ALTER COLUMN price TYPE NUMERIC(10, 2);