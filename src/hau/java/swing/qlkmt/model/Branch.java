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
public class Branch {

    private String branchId;
    private String branchName;
    private String phoneNumber;
    private String address;

    public Branch() {
    }

    public Branch(String branchId, String branchName, String phoneNumber, String address) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        final Branch other = (Branch) obj;
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.branchName, other.branchName)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return Objects.equals(this.address, other.address);
    }

//    @Override
//    public String toString() {
//        return "ChiNhanh{" + "maChiNhanhCuaHang=" + branchId + ", tenChiNhanh=" + branchName + ", sdt=" + phoneNumber + ", diaChi=" + address + '}';
//    }

    @Override
    public String toString() {
        return "Branch{" + "branchId=" + branchId + ", branchName=" + branchName + ", phoneNumber=" + phoneNumber + ", address=" + address + '}';
    }
}
