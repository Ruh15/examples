package com.rh.examples.demos.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description:
 * author: Ruh
 * time: 2018/11/8.
 */
public class Word {

    private String a;
    private int b;
    private boolean c;
    private String d;
    private String e;
    private String f;
    private Date date;
    private Map<String , Object> map;
    private List<User> list;

    public Word(String a, int b, boolean c, String d, String e, String f, Date date, Map<String, Object> map, List<User> list) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.date = date;
        this.map = map;
        this.list = list;
    }

    public Word() {
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
