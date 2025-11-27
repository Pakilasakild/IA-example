package com.ia.ia_base.models;

public class RegisteredUser extends User {

    public RegisteredUser() {
        super();
    }

    public RegisteredUser(int id, String name, String passwordHash, String email, Role role) {
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

    public boolean isAdmin(){
        return false;
    }
}
