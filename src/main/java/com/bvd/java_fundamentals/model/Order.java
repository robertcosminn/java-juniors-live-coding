package com.bvd.java_fundamentals.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Order {
    public int orderId;
    public int customerId;
    public Date orderDate;
    public String productName;
    public String category;
    public BigDecimal unitPrice;
    public int quantity;

    public void parseCSV(String filename){
        File file = new File(filename);
        Scanner inputStream = new Scanner(filename);
        inputStream.next();
        String[] nextLine;
        while(inputStream.hasNext()){
            String data = inputStream.next();
            String[] values = data.split(",");
            System.out.println(values);
        }

        inputStream.close();



    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getProductName() {
        return productName;
    }

}
