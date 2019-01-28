/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;


/**
 *
 * @author CRISTIAN
 */
public class Customer {

    private int idCustomer;
    private int capacityCustomer;
    private HashSet servicesCustomer;

    public Customer(int idCustomer, int capacityCustomer, HashSet servicesCustomer) {
        this.idCustomer = idCustomer;
        this.capacityCustomer = capacityCustomer;
        this.servicesCustomer = servicesCustomer;

    }
    public Customer(int idCustomer, int capacityCustomer){
         this.idCustomer = idCustomer;
        this.capacityCustomer = capacityCustomer;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getCapacityCustomer() {
        return capacityCustomer;
    }

    public void setCapacityCustomer(int capacityCustomer) {
        this.capacityCustomer = capacityCustomer;
    }

    public HashSet getServicesCustomer() {
        return servicesCustomer;
    }

    public void setServicesCustomer(HashSet servicesCustomer) {
        this.servicesCustomer = servicesCustomer;
    }

  
    
    
    
    

}
