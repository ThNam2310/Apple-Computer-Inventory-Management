/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.model;

import java.sql.Timestamp;
/**
 *
 * @author ACER
 */
public class DataProductImportExport {
    private Timestamp createTime;
    private int quantity;
    private double price;

    public DataProductImportExport(Timestamp createTime, int quantity, double price) {
        this.createTime = createTime;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
