package hau.java.swing.qlkmt.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.net.URL;

public class HomeView extends JFrameView implements ActionListener {

    private static HomeView instance;   // Biến lưu trữ đối tượng duy nhất của lớp
    Color DefaultColor, ClickedColor;
    private JPanel centerPanel, mainPanel, westPanel;
    private JButton productListButton, productButton, branchButton, importButton, exportButton, inventoryButton, statisticalButton;
    public ProductListView productListView;
    public ProductView productView;
    public BranchView branchView;
    public ImportView importView;
    public ExportView exportView;
    public InventoryView inventoryView;
    public StatisticView statisticView;

    private HomeView() throws UnsupportedLookAndFeelException {
        this.init();
        this.setTitle("Quản lý kho máy tính Apple");
        this.setSize(1370, 850);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.setLookAndFeel(new FlatLightLaf());
        URL url_img = HomeView.class.getResource("/hau/java/swing/qlkmt/image/logo.png");
        Image img = Toolkit.getDefaultToolkit().createImage(url_img);
        this.setIconImage(img);

        ProductListView tsp = new ProductListView();
        centerPanel.add(tsp).setVisible(true);

        DefaultColor = new Color(94, 125, 178);
        ClickedColor = new Color(20, 49, 100);
        productListButton.setBackground(DefaultColor);
        //productButton.setBackground(DefaultColor);
        branchButton.setBackground(DefaultColor);
        //importButton.setBackground(DefaultColor);
        //exportButton.setBackground(DefaultColor);
        inventoryButton.setBackground(DefaultColor);
        statisticalButton.setBackground(DefaultColor);
    }

    // Phương thức tĩnh để lấy đối tượng duy nhất
    public static HomeView getInstance() throws UnsupportedLookAndFeelException {
        if (instance == null) {
            instance = new HomeView(); // Tạo đối tượng nếu chưa tồn tại
        }
        return instance; // Trả về đối tượng duy nhất
    }

    private void init() {
        productListView = new ProductListView();
        branchView = new BranchView();
        importView = new ImportView();
        exportView = new ExportView();
        inventoryView = new InventoryView();
        statisticView = new StatisticView();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tạo JPanel phụ để đặt các nút ở phía tây
        westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS)); // Xếp các nút theo chiều dọc
        westPanel.setPreferredSize(new Dimension(200, 0)); // 200px chiều rộng, chiều cao tự động
        westPanel.setLayout(new GridLayout(12, 1, 20, 5)); // 5 hàng, 1 cột, khoảng cách giữa các nút là 10px
        westPanel.setBackground(new Color(94, 125, 178));

        ImageIcon imageIcon = new ImageIcon("src/hau/java/swing/qlkmt/image/logopanel.png");
        JLabel logoLabel = new JLabel(imageIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        westPanel.add(logoLabel);

        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(1000, 750));

        CardLayout cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout);
        centerPanel.add(productListView, "TrangDanhSachSP");
        //centerPanel.add(productView, "TrangSP");
        centerPanel.add(branchView, "TrangChiNhanh");
        //centerPanel.add(importView, "TrangNhapHang");
        //centerPanel.add(exportView, "TrangXuatHang");
        centerPanel.add(inventoryView, "TrangTonKho");
        centerPanel.add(statisticView, "TrangThongKe2");

        // trang danh sách sản phẩm
        productListButton = new JButton("SẢN PHẨM");
        productListButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangDanhSachSP");
            }
        });
        productListButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                productListButton.setBackground(ClickedColor);
                //productButton.setBackground(ClickedColor);
                branchButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(DefaultColor);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                productListButton.setBackground(ClickedColor);
                //productButton.setBackground(ClickedColor);
                branchButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(DefaultColor);
            }
        });

//        // trang sản phẩm
//        productButton = new JButton("SẢN PHẨM");
//        productButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        productButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
//                centerPanel.revalidate();
//                centerPanel.repaint();
//                cardLayout.show(centerPanel, "TrangSP");
//            }
//        });
//
//        productButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                productButton.setBackground(ClickedColor);
//                branchButton.setBackground(DefaultColor);
//                importButton.setBackground(DefaultColor);
//                exportButton.setBackground(DefaultColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//            }
//
//            @Override
//            public void mousePressed(MouseEvent evt) {
//                productButton.setBackground(ClickedColor);
//                branchButton.setBackground(DefaultColor);
//                importButton.setBackground(DefaultColor);
//                exportButton.setBackground(DefaultColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//            }
//        });
        // trang chi nhánh
        branchButton = new JButton("CHI NHÁNH");
        branchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        branchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangChiNhanh");
            }
        });

        branchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                branchButton.setBackground(ClickedColor);
                //productButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(DefaultColor);

            }

            @Override
            public void mousePressed(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                branchButton.setBackground(ClickedColor);
                //productButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(DefaultColor);
            }
        });

//        // trang nhập hàng
//        importButton = new JButton("NHẬP HÀNG");
//        importButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        importButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
//                centerPanel.revalidate();
//                centerPanel.repaint();
//                cardLayout.show(centerPanel, "TrangNhapHang");
//            }
//        });
//        importButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                importButton.setBackground(ClickedColor);
//                branchButton.setBackground(DefaultColor);
//                productButton.setBackground(DefaultColor);
//                exportButton.setBackground(DefaultColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent evt) {
//                importButton.setBackground(ClickedColor);
//                branchButton.setBackground(DefaultColor);
//                productButton.setBackground(DefaultColor);
//                exportButton.setBackground(DefaultColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//            }
//        });
        // trang xuất hàng
//        exportButton = new JButton("XUẤT HÀNG");
//        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        exportButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
//                centerPanel.revalidate();
//                centerPanel.repaint();
//                cardLayout.show(centerPanel, "TrangXuatHang");
//            }
//        });
//        exportButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                importButton.setBackground(DefaultColor);
//                branchButton.setBackground(DefaultColor);
//                productButton.setBackground(DefaultColor);
//                exportButton.setBackground(ClickedColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent evt) {
//                importButton.setBackground(DefaultColor);
//                branchButton.setBackground(DefaultColor);
//                productButton.setBackground(DefaultColor);
//                exportButton.setBackground(ClickedColor);
//                inventoryButton.setBackground(DefaultColor);
//                statisticalButton.setBackground(DefaultColor);
//            }
//        });
        // trang tồn kho
        inventoryButton = new JButton("TỒN KHO");
        inventoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangTonKho");
            }
        });
        inventoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                branchButton.setBackground(DefaultColor);
                //productButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(ClickedColor);
                statisticalButton.setBackground(DefaultColor);

            }

            @Override
            public void mousePressed(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                branchButton.setBackground(DefaultColor);
                //productButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(ClickedColor);
                statisticalButton.setBackground(DefaultColor);
            }
        });

        //trang thống kê
        statisticalButton = new JButton("THỐNG KÊ");
        statisticalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statisticalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                centerPanel.revalidate();
                centerPanel.repaint();
                cardLayout.show(centerPanel, "TrangThongKe2");
            }
        });
        statisticalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                branchButton.setBackground(DefaultColor);
                //productButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(ClickedColor);

            }

            @Override
            public void mousePressed(MouseEvent evt) {
                productListButton.setBackground(DefaultColor);
                //importButton.setBackground(DefaultColor);
                branchButton.setBackground(DefaultColor);
                //productButton.setBackground(DefaultColor);
                //exportButton.setBackground(DefaultColor);
                inventoryButton.setBackground(DefaultColor);
                statisticalButton.setBackground(ClickedColor);
            }
        });

        // Chỉnh kích thước nút
        menuBtn(productListButton);
        //menuBtn(productButton);
        menuBtn(branchButton);
        //menuBtn(importButton);
        //menuBtn(exportButton);
        menuBtn(inventoryButton);
        menuBtn(statisticalButton);

        westPanel.add(productListButton);
        //westPanel.add(productButton);
        westPanel.add(branchButton);
        //westPanel.add(importButton);
        //westPanel.add(exportButton);
        westPanel.add(inventoryButton);
        westPanel.add(statisticalButton);

        // Thêm JPanel phụ vào khu vực WEST của JPanel chính
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        pack();
    }

    // Phương thức chỉnh kích thước và thêm padding cho JButton
    private void menuBtn(JButton button) {
        Font btnFont2 = new Font("Arial", Font.BOLD, 18); // Font chữ đậm, cỡ 18
        button.setFont(btnFont2);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 80)); // Giới hạn bề ngang
        button.setMargin(new Insets(0, 5, 2, 5)); // Padding nhỏ hơn
        button.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 0));
        button.setFocusPainted(false); // Loại bỏ đường viền khi nhấn nút
    }

    public void reloadCenterPanel() {
        System.out.println("reload centerPanel!");
        // Cập nhật dữ liệu cho trang tương ứng (nếu cần)
        productListView.importView.updateData();
        productListView.exportView.updateData();
        inventoryView.updateData();
        statisticView.updateData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
