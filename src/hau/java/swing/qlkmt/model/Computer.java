package hau.java.swing.qlkmt.model;

import java.util.Arrays;
import java.util.Objects;

public class Computer {

    public String productId;
    public String productName;
    public int quantity;
    public String cpu;
    public String ram;
    public String rom;
    public double price;
    public int status;
    public byte[] image;
    public String color;

    public Computer() {

    }

    public Computer(String productId, String productName, int quantity, double price, String cpu, String loaiMay, int status) {

    }

    public Computer(String productId, String productName, int quantity, double price, String cpu, String ram, String rom, int status, byte[] image, String color) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.cpu = cpu;
        this.ram = ram;
        this.rom = rom;
        this.status = status;
        this.image = image;
        this.color = color;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Computer(String productId, String productName, int quantity, double price, String cpu, String ram, String rom, byte[] image, String color) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.cpu = cpu;
        this.ram = ram;
        this.rom = rom;
        this.image = image;
        this.color = color;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void ProductExport(int a) {
        this.quantity -= a;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Computer other = (Computer) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.cpu, other.cpu)) {
            return false;
        }
        if (!Objects.equals(this.ram, other.ram)) {
            return false;
        }
        if (!Objects.equals(this.rom, other.rom)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return Arrays.equals(this.image, other.image);
    }

//    @Override
//    public String toString() {
//        return "MayTinh{" + "maMay=" + productId + ", tenMay=" + productName + ", soLuong=" + quantity
//                + ", tenCpu=" + cpu + ", ram=" + ram + ", rom=" + rom + ", gia=" + price
//                + ", trangThai=" + status + ", hinhAnh=" + image + ", mauSac=" + color + '}';
//    }

    @Override
    public String toString() {
        return "Computer{" + "productId=" + productId + ", productName=" + productName + ", quantity=" + quantity + ", cpu=" + cpu + 
                ", ram=" + ram + ", rom=" + rom + ", price=" + price + ", status=" + status + ", image=" + image + ", color=" + color + '}';
    }
    
    

}
