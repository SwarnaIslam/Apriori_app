import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QTableWidget, QTableWidgetItem, QPushButton, QHBoxLayout, QVBoxLayout, QWidget, QSpinBox, QLabel

import sqlite3

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        self.tableWidget = QTableWidget()
        self.setCentralWidget(self.tableWidget)

        self.buttonLayout = QHBoxLayout()

        self.addToCartButton = QPushButton("Add to Cart")
        self.buttonLayout.addWidget(self.addToCartButton)

        self.addToCartButton.clicked.connect(self.showCart)

        self.buttonWidget = QWidget()
        self.buttonWidget.setLayout(self.buttonLayout)

        self.mainLayout = QVBoxLayout()
        self.mainLayout.addWidget(self.tableWidget)
        self.mainLayout.addWidget(self.buttonWidget)

        self.mainWidget = QWidget()
        self.mainWidget.setLayout(self.mainLayout)

        self.setCentralWidget(self.mainWidget)

        conn = sqlite3.connect('Food.db')
        c = conn.cursor()

        c.execute('SELECT item_name, price FROM menu')
        data = c.fetchall()

        self.tableWidget.setRowCount(len(data))
        self.tableWidget.setColumnCount(3)

        for row in range(len(data)):
            for column in range(2):
                item = QTableWidgetItem(str(data[row][column]))
                self.tableWidget.setItem(row, column, item)

            spinBox = QSpinBox()
            spinBox.setMinimum(0)
            spinBox.setMaximum(999)
            self.tableWidget.setCellWidget(row, 2, spinBox)

        self.tableWidget.setHorizontalHeaderLabels(['Name', 'Price', 'Quantity'])

        conn.close()

    def showCart(self):
        cartWindow = CartWindow()

        for row in range(self.tableWidget.rowCount()):
            quantity = self.tableWidget.cellWidget(row, 2).value()
            if quantity > 0:
                name = self.tableWidget.item(row, 0).text()
                price = self.tableWidget.item(row, 1).text()
                cartWindow.addItem(name, price, quantity)

        self.cartWindow = cartWindow 
        cartWindow.show()

class CartWindow(QWidget):
    def __init__(self):
        super().__init__()

        self.tableWidget = QTableWidget()
        self.setFixedSize(500, 500)

        self.mainLayout = QVBoxLayout()
        self.mainLayout.addWidget(self.tableWidget)

        self.setLayout(self.mainLayout)

        self.tableWidget.setRowCount(0)
        self.tableWidget.setColumnCount(4)
        self.tableWidget.setHorizontalHeaderLabels(['Name', 'Price', 'Quantity', 'Total'])

    def addItem(self, name, price, quantity):
        rowPosition = self.tableWidget.rowCount()
        self.tableWidget.insertRow(rowPosition)
        self.tableWidget.setItem(rowPosition , 0, QTableWidgetItem(name))
        self.tableWidget.setItem(rowPosition , 1, QTableWidgetItem(price))
        self.tableWidget.setItem(rowPosition , 2, QTableWidgetItem(str(quantity)))
        total = int(price) * quantity
        self.tableWidget.setItem(rowPosition , 3, QTableWidgetItem(str(total)))

if __name__ == '__main__':
    app = QApplication(sys.argv)
    mainWindow = MainWindow()
    mainWindow.show()
    sys.exit(app.exec_())
