/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Manager.Enums.StatusRoom;

import java.util.HashSet;

/**
 *
 * @author CRISTIAN
 */
public class Room implements Comparable<Room>{

    private int numberRoom;
    private int capacity;
    private HashSet service;
    private StatusRoom status;
    private HashSet workersAsign;

    public Room() {

    }

    public Room(int numberRoom, int capacity, HashSet service, StatusRoom status, HashSet workerAsign) {
        this.numberRoom = numberRoom;
        this.capacity = capacity;
        this.service = service;
        this.status = status;
        this.workersAsign = workerAsign;

    }

    public HashSet getWorkerAsign() {
        return workersAsign;
    }

    public void setWorkerAsign(HashSet workerAsign) {
        this.workersAsign = workerAsign;
    }

    public Room(int numberRoom, int capacity, StatusRoom status, String customer) {
        this.numberRoom = numberRoom;
        this.capacity = capacity;
        this.status = status;
      

    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public StatusRoom getStatus() {
        return status;
    }

    public void setStatus(StatusRoom status) {
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

    @Override
    public int compareTo(Room object) {
        return this.capacity-object.getCapacity();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.numberRoom != other.numberRoom) {
            return false;
        }
        return true;
    }

  

    
}
