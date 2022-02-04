package com.digitalmusdi.digitalmaintenance.model;

public class LoginModel {
    String id_user,departmentId,departmentName,roleId,roleName,name,email,phone,token;

    public LoginModel(String id_user, String departmentId, String departmentName, String roleId, String roleName, String name, String email, String phone, String token) {
        this.id_user = id_user;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.token = "Bearer "+token;
    }

    public String getId_user() {
        return id_user;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }
}
