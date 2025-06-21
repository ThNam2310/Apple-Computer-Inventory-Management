/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.model;

import java.util.Objects;

/**
 *
 * @author thanh
 */
public class InvoiceDetail {

    private String invoiceId;
    private String productId;
    private int quantity;
    private double price;

    public InvoiceDetail() {
    }

    public InvoiceDetail(String invoiceId, String productId, int quantity, double price) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId (String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceDetail other = (InvoiceDetail) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        return Objects.equals(this.productId, other.productId);
    }

    @Override
    public String toString() {
        return "InvoiceDetail{" + "invoiceId=" + invoiceId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
    

}
