package com.inventory_management_system.view;

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
    private final JPanel[] panels = new JPanel[7];

    public MenuView() {
        setContentPane(mainPanel);
        setTitle("Inventory Management System");
        setSize(1200,1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

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
        usersPanel = new UsersView().getUsersPanel();
    }
}
