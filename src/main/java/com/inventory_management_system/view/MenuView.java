package com.inventory_management_system.view;

import com.inventory_management_system.model.User;

import javax.swing.*;

public class MenuView extends JFrame {
    private JPanel mainPanel;
    private JButton inventoryButton;
    private JButton purchasesButton;
    private JButton salesButton;
    private JButton productsButton;
    private JButton suppliersButton;
    private JButton customersButton;
    private JButton usersButton;
    private JPanel inventoryPanel;
    private JPanel purchasesPanel;
    private JPanel salesPanel;
    private JPanel productsPanel;
    private JPanel suppliersPanel;
    private JPanel customersPanel;
    private JPanel usersPanel;

    private UsersView usersView;
    private final JPanel[] panels = new JPanel[7];
    private final User user;

    public MenuView(User user) {
        setContentPane(mainPanel);
        setTitle("Inventory Management System");
        setSize(800,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        this.user = user;
        usersView.setUser(user);
        usersView.showAddUserPanel();
        panels[0] = inventoryPanel;
        panels[1] = purchasesPanel;
        panels[2] = salesPanel;
        panels[3] = productsPanel;
        panels[4] = suppliersPanel;
        panels[5] = customersPanel;
        panels[6] = usersPanel;

        inventoryButton.addActionListener(e -> switchPanel(inventoryPanel));

        purchasesButton.addActionListener(e -> switchPanel(purchasesPanel));

        salesButton.addActionListener(e -> switchPanel(salesPanel));

        productsButton.addActionListener(e -> switchPanel(productsPanel));

        suppliersButton.addActionListener(e -> switchPanel(suppliersPanel));

        customersButton.addActionListener(e -> switchPanel(customersPanel));

        usersButton.addActionListener(e -> switchPanel(usersPanel));
    }

    private void switchPanel(JPanel newPanel) {
        for (JPanel panel : panels) {
            if (panel.isVisible() && panel != newPanel){
                panel.setVisible(false);
            } else if (!panel.isVisible() && panel == newPanel) {
                panel.setVisible(true);
            }
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        inventoryPanel = new InventoryView().getInventoryPanel();
        purchasesPanel = new PurchasesView().getPurchasesPanel();
        salesPanel = new SalesView().getSalesPanel();
        productsPanel = new ProductsView().getProductsPanel();
        suppliersPanel = new SuppliersView().getSuppliersPanel();
        customersPanel = new CustomersView().getCustomersPanel();
        usersView = new UsersView();
        usersPanel = usersView.getUsersPanel();
    }
}
