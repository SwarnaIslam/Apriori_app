import sqlite3

# connect to the database and execute the query
conn = sqlite3.connect("Food.db")
c = conn.cursor()
c.execute("SELECT * FROM Invoice")

# fetch all the results and display them
results = c.fetchall()
print("All purchases:")
for result in results:
    print(f"{result[1]}")

# close the connection
conn.close()
