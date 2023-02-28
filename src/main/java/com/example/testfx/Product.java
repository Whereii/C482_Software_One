package com.example.testfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <h1>Product Class</h1>
 * This class is used to create product objects that are useful for creating
 * representations of real world products and what parts they contain
 * @author Joshua McCausey
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId(){
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return the price
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * @return the stock
     */
    public int getStock(){
        return this.stock;
    }

    /**
     * @return the min
     */
    public int getMin(){
        return  this.min;
    }

    /**
     * @return the max
     */
    public int getMax(){
        return this.max;
    }

    /**
     * @param part the part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * @param selectedPart the part to delete
     */
    public boolean deleteAssociatedPart(Part selectedPart){
        return associatedParts.remove(selectedPart);
    }

    /**
     * @return the associatedParts
     */
    public ObservableList getAllAssociatedParts(){
        return associatedParts;
    }
}
