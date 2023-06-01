package com.inventory_management_system;

import com.inventory_management_system.controller.UserController;
import com.inventory_management_system.model.User;
import com.inventory_management_system.view.LoginView;

public class Test {
    public static void main(String[] args) {
        UserController userController = new UserController();
        if (userController.getUserByRole("director") == null){
            userController.addUser(new User(0, "director", "12345678", "director"));
            System.out.println("A director was created");
        }
        new LoginView();
    }
}
