/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

public class Materials {

    private String MaterialName;
    private double MaterialPrice;
    private String FileName = "Materials.txt";

    public void SetMaterial(String Name, double price) {
        this.MaterialName = Name;
        this.MaterialPrice = price;

    }

    public String getMaterialData() {
        return (this.MaterialName + "&" + this.MaterialPrice + "&");
    }

    public String getMaterialname() {
        return this.MaterialName;
    }

    public double getMaterialPrice() {
        return this.MaterialPrice;
    }

}
