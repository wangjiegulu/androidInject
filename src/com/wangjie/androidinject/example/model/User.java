package com.wangjie.androidinject.example.model;

import com.wangjie.androidinject.annotation.annotations.orm.AIColumn;
import com.wangjie.androidinject.annotation.annotations.orm.AIPrimaryKey;
import com.wangjie.androidinject.annotation.annotations.orm.AITable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-25
 * Time: 上午10:04
 */
@AITable
public class User implements Serializable{
    @AIColumn
    @AIPrimaryKey(insertable = false)
    private int uid;
    @AIColumn("username")
    private String username;
    @AIColumn
    private String password;
    @AIColumn
    private long createmillis;
    @AIColumn
    private float height;
    @AIColumn
    private double weight;

    private String notCol;

    public User() {
    }

    public User(String username, String password, long createmillis, float height, double weight, String notCol) {
        this.username = username;
        this.password = password;
        this.createmillis = createmillis;
        this.height = height;
        this.weight = weight;
        this.notCol = notCol;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatemillis() {
        return createmillis;
    }

    public void setCreatemillis(long createmillis) {
        this.createmillis = createmillis;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getNotCol() {
        return notCol;
    }

    public void setNotCol(String notCol) {
        this.notCol = notCol;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createmillis=" + createmillis +
                ", height=" + height +
                ", weight=" + weight +
                ", notCol='" + notCol + '\'' +
                '}';
    }

}
