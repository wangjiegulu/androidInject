package com.wangjie.androidinject.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-12-2
 * Time: 上午10:25
 */
public class Person implements Serializable{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


}
