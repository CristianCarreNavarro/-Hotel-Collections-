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
public class Worker {

    private int idWorker;
    private String nameWorker;
    private HashSet skills;

    public Worker(int iDWorker, String nameWorker, HashSet skills) {
        this.idWorker = iDWorker;
        this.nameWorker = nameWorker;
        this.skills = skills;
    }

    public Worker() {

    }

    public Worker(int iDWorker) {
        this.idWorker = iDWorker;
    }

    public Worker(int iDWorker, String nameWorker) {
        this.idWorker = iDWorker;
        this.nameWorker = nameWorker;

    }

    public int getiDWorker() {
        return idWorker;
    }

    public void setiDWorker(int iDWorker) {
        this.idWorker = iDWorker;
    }

    public String getNameWorker() {
        return nameWorker;
    }

    public void setNameWorker(String nameWorker) {
        this.nameWorker = nameWorker;
    }

    public HashSet getSkills() {
        return skills;
    }

    public void setSkills(HashSet skills) {
        this.skills = skills;
    }

}
