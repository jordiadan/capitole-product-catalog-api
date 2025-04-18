CREATE TYPE category_enum AS ENUM (
  'ACCESSORIES',
  'CLOTHING',
  'ELECTRONICS',
  'FOOTWEAR',
  'HOME_AND_KITCHEN',
  'HOME_APPLIANCES',
  'MUSICAL_INSTRUMENTS',
  'SPORTS',
  'STATIONERY',
  'TOYS_AND_GAMES'
);

CREATE TABLE products
(
    sku         VARCHAR(20) PRIMARY KEY,
    description VARCHAR(255)  NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    category    category_enum NOT NULL
);