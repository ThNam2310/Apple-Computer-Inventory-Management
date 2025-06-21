/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author thanh
 */
public class Invoice {

    private String invoiceId;
    private Timestamp createTime ;
    private ArrayList<InvoiceDetail> invoiceDetail;
    private double totalPrice;

    public Invoice() {
    }

    public Invoice(String invoiceId, Timestamp createTime, ArrayList<InvoiceDetail> invoiceDetail, double totalPrice) {
        this.invoiceId = invoiceId;
        this.createTime = createTime;
        this.invoiceDetail = invoiceDetail;
        this.totalPrice = totalPrice;
    }

    public Invoice(String invoiceId, Timestamp createTime, double totalPrice) {
        this.invoiceId = invoiceId;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public ArrayList<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(ArrayList<InvoiceDetail> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
        final Invoice other = (Invoice) obj;
        if (Double.doubleToLongBits(this.totalPrice) != Double.doubleToLongBits(other.totalPrice)) {
            return false;
        }
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        if (!Objects.equals(this.createTime, other.createTime)) {
            return false;
        }
        return Objects.equals(this.invoiceDetail, other.invoiceDetail);
    }

//    @Override
//    public String toString() {
//        return "Phieu{" + "maPhieu=" + invoiceId + ", thoigianTao=" + createTime + ", CTPhieu=" + invoiceDetail + ", tongTien=" + totalPrice + '}';
//    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + invoiceId + ", createTime=" + createTime + ", invoiceDetail=" + invoiceDetail + ", totalPrice=" + totalPrice + '}';
    }
    
    

}
