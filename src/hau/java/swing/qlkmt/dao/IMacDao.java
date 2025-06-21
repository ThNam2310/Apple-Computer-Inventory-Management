package hau.java.swing.qlkmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import hau.java.swing.qlkmt.database.ConnectionDatabase;
import hau.java.swing.qlkmt.model.IMac;
import java.sql.SQLException;

public class IMacDao implements DAOInterface<IMac> {

    public static IMacDao getInstance() {
        return new IMacDao();
    }

    @Override
    public int insert(IMac t) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu,ram,rom,congSuatNguon"
                    + ",kichThuocMan,gia,loaiMay,trangThai,hinhAnh,mauSac)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductId());
            ps.setString(2, t.getProductName());
            ps.setInt(3, t.getQuantity());
            ps.setString(4, t.getCpu());
            ps.setString(5, t.getRam());
            ps.setString(6, t.getRom());
            ps.setString(7, t.getPower());
            ps.setDouble(8, t.getMonitorSize());
            ps.setDouble(9, t.getPrice());
            ps.setString(10, "IMac");
            ps.setInt(11, t.getStatus());
            ps.setBytes(12, t.getImage());
            ps.setString(13, t.getColor());
            result = ps.executeUpdate();
            //Connector.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Không thêm được" + t.getProductId(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public int update(IMac t) {
        // TODO Auto-generated method stub
        int result = 0;
        try { 
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE MayTinh SET maMay = ?, tenMay=?, soLuong=?, gia=?, tenCpu=?, ram=?, rom=?"
                    + ",congSuatNguon=?, kichThuocMan=?, loaiMay=?, trangThai=?, hinhAnh =?, mauSac = ? WHERE maMay =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductId());
            ps.setString(2, t.getProductName());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getPrice());
            ps.setString(5, t.getCpu());
            ps.setString(6, t.getRam());
            ps.setString(7, t.getRom());
            ps.setString(8, t.getPower());
            ps.setDouble(9, t.getMonitorSize());
            ps.setString(10, "IMac");
            ps.setInt(11, t.getStatus());
            ps.setBytes(12, t.getImage());
            ps.setString(13, t.getColor());
            ps.setString(14, t.getProductId());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int delete(IMac t) {
        // TODO Auto-generated method stub
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM MayTinh WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);

        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<IMac> selectAll() {
        // TODO Auto-generated method stub
        ArrayList<IMac> result = new ArrayList<>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM MayTinh";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                String power = rs.getString("congSuatNguon");
                double monitorSize = rs.getDouble("kichThuocMan");
                double price = rs.getDouble("gia");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                IMac mt = new IMac(monitorSize, power, productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                result.add(mt);

            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public IMac selectById(String t) {
        // TODO Auto-generated method stub
        IMac result = null;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                String power = rs.getString("congSuatNguon");
                double monitorSize = rs.getDouble("kichThuocMan");
                double price = rs.getDouble("gia");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                result = new IMac(monitorSize, power, productId, productName, quantity, price, cpu, ram, rom, status, image, color);

            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    public boolean isIMac(String id) {
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            String type = null;
            while (rs.next()) {
                type = rs.getString("loaiMay");

            }
            if (type.equals("IMac")) {
                return true;
            }
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
        }
        return false;
    }
}
