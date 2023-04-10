import random
import sqlite3

# create a connection to the database and a cursor object
conn = sqlite3.connect("food.db")
c = conn.cursor()

food_items = ["pizza", "burger", "ramen", "tacos", "waffle", "falafel", "chicken wings", "burrito", "sandwich", "coffee", "coke", "milkshake", "french fries"]
price = [400, 200, 300, 250, 150, 100, 150, 200, 150, 80, 25, 120, 80]

# c.execute('DROP TABLE IF EXISTS Menu')

# create the 'Menu' table
# c.execute('''CREATE TABLE Menu
#              (Item_No INTEGER PRIMARY KEY,
#              Item_Name TEXT,
#              Item_Details TEXT,
#              Price NUMERIC)''')

# insert the food items into the 'Menu' table
for i, item in enumerate(food_items):
  c.execute("INSERT INTO Menu VALUES (?, ?, ?, ?)", (i+1, item, "Details about "+item.upper(), price[i]))

# commit the changes and close the connection
conn.commit()
conn.close()

print("Menu table successfully created and food items added!")
