package hau.java.swing.qlkmt.view;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginView extends JFrameView {

    private JPasswordField passwordField;
    private JTextField usernameField;

    public LoginView() throws UnsupportedLookAndFeelException {
        // Cấu hình JFrame (cửa sổ chính)
        setTitle("Đăng Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        UIManager.setLookAndFeel(new FlatLightLaf());
        setResizable(false);
        setLocationRelativeTo(null); // Căn giữa màn hình
        URL url_img = HomeView.class.getResource("/hau/java/swing/qlkmt/image/logo.png");
        Image img = Toolkit.getDefaultToolkit().createImage(url_img);
        this.setIconImage(img);

        // Tạo panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 49, 100)); // Màu nền xanh

        // Tạo tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);

        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(38, 78, 145));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Tạo panel trung tâm chứa form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần

        // Tài khoản
        JLabel usernameLabel = new JLabel("Tài Khoản:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(new Color(38, 78, 145));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Mật khẩu
        JLabel passwordLabel = new JLabel("Mật Khẩu:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(38, 78, 145));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Tạo panel chứa nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        JButton loginButton = new JButton("Đăng Nhập");
        loginButton.setMnemonic(KeyEvent.VK_ENTER);
        loginButton.setPreferredSize(new Dimension(150, 35));

        loginButton.setBackground(new Color(94, 125, 178));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Lắng nghe sự kiện nhấn nút "Đăng Nhập"
        loginButton.addActionListener(e -> {
            String userName = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(userName, password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

                // Hiển thị HomeView
                SwingUtilities.invokeLater(() -> {
                    try {
                        HomeView trangChu = HomeView.getInstance();
                        trangChu.setVisible(true); // Hiển thị HomeView

                    } catch (UnsupportedLookAndFeelException ex) {
                    }
                });
                dispose(); // Đóng cửa sổ Login
            } else if (userName.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản hoặc mật khẩu!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Bạn đã nhập sai tài khoản hoặc mật khẩu!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Thêm panel chính vào frame
        add(mainPanel);
        setVisible(true);
    }

    // Phương thức xác thực thông tin đăng nhập
    private boolean authenticate(String userName, String password) {
        return "123".equals(userName) && "123".equals(password);
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new LoginView().setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
