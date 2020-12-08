package com.rama.app.data;

import java.util.Objects;

public class Employee {
    private String name;
    private String role;

    Employee() {
    }

    Employee(String name, String role) {

        this.name = name;
        this.role = role;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}

