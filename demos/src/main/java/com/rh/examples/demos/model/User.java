package com.rh.examples.demos.model;

/**
 * description:
 * author: Ruh
 * time: 2018/11/8.
 */
public class User {

    private Integer id;
    private String name;
    private String add;
    private String old;

    public User(Integer id, String name, String add, String old) {
        this.id = id;
        this.name = name;
        this.add = add;
        this.old = old;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }
}
