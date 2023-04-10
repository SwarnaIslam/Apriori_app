# IHA EXECUTE KORAR AGEY 



import random
import sqlite3

# list of food items
food_items = ["pizza", "burger", "ramen", "tacos", "waffle", "falafel", "chicken wings", "burrito", "sandwich", "coffee", "coke", "milkshake", "french fries"]

# generate 10 random transactions
transactions = []
for i in range(1000):
    num_items = random.randint(1, 5)
    items = [random.choice(food_items) for _ in range(num_items)]
    transactions.append(items)

# connect to the database and create the table
conn = sqlite3.connect("Food.db")
c = conn.cursor()
# c.execute('DROP TABLE IF EXISTS Invoice')
# c.execute("CREATE TABLE Invoice (invoice_no INTEGER PRIMARY KEY, items_purchased TEXT)")

# insert the transactions into the table
#change the invoice no to be the index of the transaction
for i, transaction in enumerate(transactions):
    items_str = ",".join(transaction)
    c.execute("INSERT INTO Invoice (invoice_no, items_purchased) VALUES (?, ?)", (i+1, items_str))

# commit the changes and close the connection
conn.commit()
conn.close()

print("Data successfully stored in the 'Purchases' table!")
