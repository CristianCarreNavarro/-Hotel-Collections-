/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Manager.Manager_Hotel.statusRoom;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author CRISTIAN
 */
public class Room {

    private int numberRoom;
    private int capacity;
    private HashSet service;
    private statusRoom status;
    private String customer;
    private HashMap workersAsign;

    public Room(int numberRoom, int capacity, HashSet service, statusRoom status,String customer,HashMap workerAsign) {
        this.numberRoom = numberRoom;
        this.capacity = capacity;
        this.service = service;
        this.status = status;
        this.customer = customer;
        this.workersAsign = workerAsign;

    }

    public HashMap getWorkerAsign() {
        return workersAsign;
    }

    public void setWorkerAsign(HashMap workerAsign) {
        this.workersAsign = workerAsign;
    }

    public Room() {

    }

    public Room(int numberRoom, int capacity, statusRoom status,String customer) {
        this.numberRoom = numberRoom;
        this.capacity = capacity;
        this.status = status;
                this.customer = customer;

    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public statusRoom getStatus() {
        return status;
    }

    public void setStatus(statusRoom status) {
        this.status = status;
    }

    public HashSet getService() {
        return service;
    }

    public void setService(HashSet service) {
        this.service = service;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    
    
    
}
