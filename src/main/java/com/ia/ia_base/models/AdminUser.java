package com.ia.ia_base.models;

public class AdminUser extends User{
    public AdminUser() {
        super();
    }

    public AdminUser(int id, String name, String passwordHash, String email, Role role) {
        super(id, name, passwordHash, email, role);
    }

    @Override
    public boolean canManageUsers() {
        return false;
    }

    @Override
    public boolean canManageCategories() {
        return false;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
