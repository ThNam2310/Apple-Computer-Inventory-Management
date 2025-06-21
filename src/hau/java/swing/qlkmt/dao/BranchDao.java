/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hau.java.swing.qlkmt.dao;

import java.sql.PreparedStatement;
import hau.java.swing.qlkmt.model.Branch;
import hau.java.swing.qlkmt.database.ConnectionDatabase;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thanh
 */
public class BranchDao implements DAOInterface<Branch> {

    public static BranchDao getInstance() {
        return new BranchDao();
    }

    @Override
    public int insert(Branch t) {
        int result = 0;
        try {
            java.sql.Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO ChiNhanhCuaHang (maChiNhanhCuaHang, tenChiNhanh, Sdt, diaChi) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getBranchId());
            ps.setString(2, t.getBranchName());
            ps.setString(3, t.getPhoneNumber());
            ps.setString(4, t.getAddress());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thêm được chi nhánh" + t.getBranchId(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public int update(Branch t) {
        int result = 0;
        try {
            java.sql.Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE ChiNhanhCuaHang SET maChiNhanhCuaHang = ?, tenChiNhanh = ?, Sdt=?, diaChi = ? WHERE maChiNhanhCuaHang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getBranchId());
            ps.setString(2, t.getBranchName());
            ps.setString(3, t.getPhoneNumber());
            ps.setString(4, t.getAddress());
            ps.setString(5, t.getBranchId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int delete(Branch t) {
        int result = 0;
        try {
            java.sql.Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM ChiNhanhCuaHang WHERE maChiNhanhCuaHang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getBranchId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<Branch> selectAll() {
        ArrayList<Branch> result = new ArrayList<>();
        try {
            java.sql.Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM ChiNhanhCuaHang";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String branchId = rs.getString("maChiNhanhCuaHang");
                String branchName = rs.getString("tenChiNhanh");
                String phoneNumber = rs.getString("Sdt");
                String address = rs.getString("diaChi");
                Branch cn = new Branch(branchId, branchName, phoneNumber, address);
                result.add(cn);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public Branch selectById(String t) {
        Branch result = null;
        try {
            java.sql.Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM ChiNhanhCuaHang WHERE maChiNhanhCuaHang = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String branchId = rs.getString("maChiNhanhCuaHang");
                String branchName = rs.getString("tenChiNhanh");
                String phoneNumber = rs.getString("Sdt");
                String address = rs.getString("diaChi");
                result = new Branch(branchId, branchName, phoneNumber, address);
            }
        } catch (SQLException e) {
        }
        return result;
    }
}
