package com.inventory_management_system.view;

import javax.swing.*;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JButton inventoryButton;
    private JButton purchasesButton;
    private JButton salesButton;
    private JButton productsButton;
    private JButton suppliersButton;
    private JButton customersButton;
    private JButton usersButton;
    private JPanel currentPanel;

    public MainView() {
        setContentPane(mainPanel);
        setTitle("Inventory Management System");
        setSize(1200,1000);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        inventoryButton.addActionListener(e -> {
            InventoryView inventoryView = new InventoryView();
            JPanel inventoryPanel = inventoryView.getInventoryPanel();
            switchPanel(inventoryPanel);
        });
    }

    private void switchPanel(JPanel newPanel) {
        getContentPane().remove(currentPanel);
        getContentPane().add(newPanel);
        revalidate();
        repaint();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
