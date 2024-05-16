import time
import mysql.connector

host = "db_mysql"
port = 3306
user = "user"
password = "password"
database = "tqs_project"
# Connect to MySQL
conn = None

while not conn:
    try:
        conn = mysql.connector.connect(
            host=host, port=port, user=user, password=password, database=database)
        print("Connected to MySQL.")

    except mysql.connector.Error as e:
        print("Error:", e)
        print("Retrying in 5 seconds.")
        time.sleep(5)

cursor = conn.cursor()

# Drop tables
drop_tables = [
    "DROP TABLE IF EXISTS status",
    "DROP TABLE IF EXISTS `order`",
    "DROP TABLE IF EXISTS order_details",
    "DROP TABLE IF EXISTS product",
    "DROP TABLE IF EXISTS category",
    "DROP TABLE IF EXISTS customer",
    "DROP TABLE IF EXISTS staff"
]

for query in drop_tables:
    cursor.execute(query)

create_tables = [
    """
    CREATE TABLE IF NOT EXISTS category (
        category_id BIGINT PRIMARY KEY,
        name VARCHAR(255) NOT NULL
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS customer (
        customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        nmec INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        phone_number VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS staff (
        staff_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        phone_number VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        type VARCHAR(255) NOT NULL
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS product (
        product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        ingredients VARCHAR(255) NOT NULL,
        price FLOAT NOT NULL,
        category_id BIGINT,
        FOREIGN KEY (category_id) REFERENCES category(category_id)
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS order_details (
        order_details_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        customizations VARCHAR(255) NOT NULL,
        product_id BIGINT,
        FOREIGN KEY (product_id) REFERENCES product(product_id)
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS `order` (
        order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        customer_id BIGINT,
        order_details_id BIGINT,
        FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
        FOREIGN KEY (order_details_id) REFERENCES order_details(order_details_id)
    );
    """,
    """
    CREATE TABLE IF NOT EXISTS status (
        status_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        status VARCHAR(255) NOT NULL,
        time TIMESTAMP NOT NULL,
        order_id BIGINT,
        FOREIGN KEY (order_id) REFERENCES `order`(order_id)
    );
    """
]


for query in create_tables:
    print(query)
    cursor.execute(query)


# Insert initial data
insert_data =  [
    """
    INSERT INTO category (category_id, name) VALUES (1, 'Drinks'), (2, 'Foods'), (3, 'Snacks'), (4, 'Desserts'), (5, 'Pastry'), (6, 'Coffee'), (7,'Promotions');
    """,
    """
    INSERT INTO product (name, ingredients, price, category_id) VALUES ('Ham and Cheese Croissant', 'croissant, ham, cheese', 3.99, 5);
    """
]     

for query in insert_data:
    print(query)
    cursor.execute(query)

print("Tables created successfully.")

conn.commit()
cursor.close()
conn.close()
