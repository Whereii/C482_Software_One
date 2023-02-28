package com.example.testfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <h1>Inventory Class</h1>
 * This class is used to store data and manage it
 * @author Joshua McCausey
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the newPart to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * @param newProduct the newProduct to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to search for a part
     * @param partId the partId to lookup
     * Having two methods of the same name and both taking different data types was more difficult to work with than
     * I previously expected. The key was simply breaking down the problem into more subparts and individually checking
     * each component so that I could handel it in the best way possible.
     *@return Part matching partId
     */
    public static Part lookupPart(int partId){
        InHouse ihHolder = new InHouse(0, "fail", 0, 0, 0, 0, 0);
        Outsourced osHolder = new Outsourced(0, "fail", 0, 0, 0, 0, "");
        allParts.forEach(part -> {
            if(part.getId() == partId){
                if(part.getClass() == Outsourced.class){
                    Outsourced dowcastpart = (Outsourced) part;
                    osHolder.setId(part.getId());
                    osHolder.setName(part.getName());
                    osHolder.setPrice(part.getPrice());
                    osHolder.setStock(part.getStock());
                    osHolder.setMin(part.getMin());
                    osHolder.setMax(part.getMax());
                    osHolder.setCompanyName(dowcastpart.getCompanyName());
                } else{
                    InHouse dowcastpart = (InHouse) part;
                    ihHolder.setId(part.getId());
                    ihHolder.setName(part.getName());
                    ihHolder.setPrice(part.getPrice());
                    ihHolder.setStock(part.getStock());
                    ihHolder.setMin(part.getMin());
                    ihHolder.setMax(part.getMax());
                    ihHolder.setMachineId(dowcastpart.getMachineId());
                }
            }
        });
        if(ihHolder.getName() != "fail"){
            return ihHolder;
        } else{
            return osHolder;
        }
    }
    /**
     * * <h1>RUNTIME ERROR</h1>
     * @param productId the productId to lookup
     * Having two methods of the same name and both taking different data types was more difficult to work with than
     * I previously expected. The key was simply breaking down the problem into more subparts and individually checking
     * each component so that I could handel it in the best way possible.
     *@return Product matching productId
     */
    public static Product lookupProduct(int productId){
        Product holder = new Product(0, "fail", 0, 0, 0, 0);
        allProducts.forEach(product -> {
            if(product.getId() == productId){
                holder.setId(product.getId());
                holder.setName(product.getName());
                holder.setPrice(product.getPrice());
                holder.setStock(product.getStock());
                holder.setMin(product.getMin());
                holder.setMax(product.getMax());
                product.getAllAssociatedParts().forEach(part-> {
                    holder.addAssociatedPart((Part) part);
                });
            }
        });
        return holder;
    }

    /**
     * @param partName the partName to return
     * This method is used to search for possible matches for the input given.
     * @return List containing possible matches
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> holder = FXCollections.observableArrayList();
        allParts.forEach(part -> {
            if(part.getName().contains(partName)){
                holder.add(part);
            }
        });
        return holder;
    }

    /**
     * @param productName the productName to return
     * This method is used to search for possible matches for the input given.
     * @return List containing possible matches
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> holder = FXCollections.observableArrayList();
        allProducts.forEach(product -> {
            if(product.getName().contains(productName)){
                holder.add(product);
            }
        });
        return holder;
    }

    /**
     *  <h1>RUNTIME ERROR</h1>
     * @param index the index to search for
     * @param selectedPart the selectedPart to insert
     *This method is used to replace a pre-existing part with a new one.
     * For some reason the built-in list method indexOf was not functioning as expected so I had
     * multiple errors leading to this one function due to the index. I fixed that in the other areas of the code
     * so that the index recieved the proper value for the set method.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);

    }

    /**
     * * <h1>RUNTIME ERROR</h1>
     * @param index the index to search for
     * @param newProduct the newProduct to insert
     * For some reason the built-in list method indexOf was not functioning as expected so I had
     * multiple errors leading to this one function due to the index. I fixed that in the other areas of the code
     * so that the index recieved the proper value for the set method.
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart the selectedPart to delete
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * @param selectedProduct the selectedProduct to delete
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
