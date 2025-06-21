package hau.java.swing.qlkmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import hau.java.swing.qlkmt.database.ConnectionDatabase;
import hau.java.swing.qlkmt.model.Laptop;
import java.sql.SQLException;

public class LaptopDao implements DAOInterface<Laptop> {

    public static LaptopDao getInstance() {
        return new LaptopDao();
    }

    @Override
    public int insert(Laptop t) {
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong,gia, tenCpu,ram,rom,dungLuongPin,kichThuocMan,loaiMay,trangThai,hinhAnh, mauSac) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductId());
            ps.setString(2, t.getProductName());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getPrice());
            ps.setString(5, t.getCpu());
            ps.setString(6, t.getRam());
            ps.setString(7, t.getRom());
            ps.setString(8, t.getPin());
            ps.setDouble(9, t.getMonitorSize());
            ps.setString(10, "Laptop");
            ps.setInt(11, t.getStatus());
            ps.setBytes(12, t.getImage());
            ps.setString(13, t.getColor());
            result = ps.executeUpdate();
            ConnectionDatabase.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Không thêm được" + t.getProductId(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public int update(Laptop t) {
        // TODO Auto-generated method stub
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "UPDATE MayTinh SET maMay = ?, tenMay=?, soLuong=?,gia=?,tenCpu=?,ram=?,rom=?,dungLuongPin=?,kichThuocMan=?,loaiMay=?,trangThai=?, hinhAnh=?, mauSac = ? WHERE maMay =? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getProductId());
            ps.setString(2, t.getProductName());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getPrice());
            ps.setString(5, t.getCpu());
            ps.setString(6, t.getRam());
            ps.setString(7, t.getRom());
            ps.setString(8, t.getPin());
            ps.setDouble(9, t.getMonitorSize());
            ps.setString(10, "Laptop");
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
    public int delete(Laptop t) {
        // TODO Auto-generated method stub
        int result = 0;
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "DELETE FROM MayTinh WHERE maMay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            result = ps.executeUpdate();
            //Connector.closeConnection(con);

        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public ArrayList<Laptop> selectAll() {
        // TODO Auto-generated method stub
        ArrayList<Laptop> result = new ArrayList<Laptop>();
        try {
            Connection con = ConnectionDatabase.getConnection();
            String sql = "SELECT * FROM MayTinh";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("maMay");
                String productName = rs.getString("tenMay");
                int quantity = rs.getInt("soLuong");
                double price = rs.getDouble("gia");
                String cpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                String pin = rs.getString("dungLuongPin");
                double monitorSize = rs.getDouble("kichThuocMan");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                Laptop mt = new Laptop(monitorSize, pin, productId, productName, quantity, price, cpu, ram, rom, status, image, color);
                result.add(mt);

            }
            //Connector.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public Laptop selectById(String t) {
        // TODO Auto-generated method stub
        Laptop result = null;
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
                String pin = rs.getString("dungLuongPin");
                double monitorSize = rs.getDouble("kichThuocMan");
                double price = rs.getDouble("gia");
                int status = rs.getInt("trangThai");
                byte[] image = rs.getBytes("hinhAnh");
                String color = rs.getString("mauSac");
                result = new Laptop(monitorSize, pin, productId, productName, quantity,price, cpu, ram, rom, status, image, color);

            }
            //Connector.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    public boolean isLaptop(String id) {
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
            if (type.equals("Laptop")) {
                return true;
            }
            //Connector.closeConnection(con);
        } catch (SQLException e) {
        }
        return false;
    }

}
