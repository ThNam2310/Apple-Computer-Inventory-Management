package hau.java.swing.qlkmt.model;

import java.util.Objects;

public class IMac extends Computer {

    private double motitorSize ;
    private String power;

    public IMac(double motitorSize, String power, String productId, String productName, int quantity, double price, String cpu, String ram, String rom,
            int status, byte[] image, String color) {
        super(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
        this.motitorSize = motitorSize;
        this.power = power;
    }

    public IMac() {

    }

    public double getMonitorSize() {
        return motitorSize;
    }

    public void setMonitorSize(double motitorSize) {
        this.motitorSize = motitorSize;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

//    @Override
//    public String toString() {
//        return "IMac{" + "kichThuocMan=" + motitorSize + ", congSuatNguon=" + power + '}';
//    }

    @Override
    public String toString() {
        return "IMac{" + "motitorSize=" + motitorSize + ", power=" + power + '}';
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
        final IMac other = (IMac) obj;
        if (!Objects.equals(this.motitorSize, other.motitorSize)) {
            return false;
        }
        return Objects.equals(this.power, other.power);
    }
}
