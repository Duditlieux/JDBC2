/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingwithhsqldb;

/**
 *
 * @author pedago
 */
public class Product {
    private String name;
    private int price;
    public Product(String name, int price){
        System.out.println(name);
        this.name=name;
        this.price=price;
    }
    
    
    public String getName(){
        return name;
    }
    
    public int getPrice(){
        return price;
    }
}
