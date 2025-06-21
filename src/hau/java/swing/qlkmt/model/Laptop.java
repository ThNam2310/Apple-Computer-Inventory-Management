package hau.java.swing.qlkmt.model;

import java.util.Objects;

public class Laptop extends Computer {

    private double monitorSize;
    private String pin;

    public Laptop(double monitorSize, String pin, String productId, String productName, int quantity, double price, String cpu,
            String ram, String rom, int status, byte[] image, String color) {
        super(productId, productName, quantity, price, cpu, ram, rom, status, image, color);
        this.monitorSize = monitorSize;
        this.pin = pin;
    }

    public Laptop() {

    }

    public double getMonitorSize() {
        return monitorSize;
    }

    public void setMonitorSize(double monitorSize) {
        this.monitorSize = monitorSize;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Laptop{" + "monitorSize=" + monitorSize + ", pin=" + pin + '}';
    }

    

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
        final Laptop other = (Laptop) obj;
        if (!Objects.equals(this.monitorSize, other.monitorSize)) {
            return false;
        }
        return Objects.equals(this.pin, other.pin);
    }
}
