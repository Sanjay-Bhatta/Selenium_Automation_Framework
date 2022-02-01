package com.seleniumdemo.framework.pom.objects;

import com.seleniumdemo.framework.pom.utils.JacksonUtils;

import java.io.IOException;

public class Product {
    private int id;
    private String name;

    public Product(int id) throws IOException {
        Product[] products = JacksonUtils.deserializeJson("Products.json", Product[].class);
        for(Product product : products){
            if(product.id == id){
                this.id = id;
                this.name = product.getName();
            }
        }
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
