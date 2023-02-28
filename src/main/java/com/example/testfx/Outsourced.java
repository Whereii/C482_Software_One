package com.example.testfx;

/**
 * <h1>Outsourced</h1>
 * This class is an extension of the Part class and is useful for creating similar Part objects
 * with slight differences
 * @author Joshua McCausey
 */
public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName(){
        return companyName;
    }
}
