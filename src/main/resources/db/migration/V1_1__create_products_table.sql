CREATE TYPE category_enum AS ENUM (
  'ELECTRONICS',
  'HOME_AND_KITCHEN',
  'CLOTHING',
  'ACCESSORIES',
  'SPORTS',
  'STATIONERY',
  'TOYS_AND_GAMES',
  'MUSICAL_INSTRUMENTS',
  'FOOTWEAR',
  'HOME_APPLIANCES'
);

CREATE TABLE products
(
    sku         VARCHAR(20) PRIMARY KEY,
    description VARCHAR(255)  NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    category    category_enum NOT NULL
);