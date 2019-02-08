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
public final class Customer {

    private String idCustomer;
    private int capacityCustomer;
    private HashSet servicesCustomer;

    public Customer(String idCustomer, int capacityCustomer, HashSet servicesCustomer) {
        setIdCustomer(idCustomer);
        this.idCustomer = idCustomer;
        this.capacityCustomer = capacityCustomer;
        this.servicesCustomer = servicesCustomer;

    }

    public Customer() {

    }

    public Customer(String idCustomer, int capacityCustomer) {
        this.idCustomer = idCustomer;
        this.capacityCustomer = capacityCustomer;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {

        String STRING_DE_ASOCIACION_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numberID = Integer.parseInt(String.valueOf(idCustomer));
        this.idCustomer = numberID + "" + STRING_DE_ASOCIACION_NIF.charAt(numberID % 23);

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
