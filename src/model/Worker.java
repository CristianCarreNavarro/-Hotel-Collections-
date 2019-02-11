/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author CRISTIAN
 */
public final class Worker {

    private String idWorker;
    private String nameWorker;
    private Queue skills;

    public Worker(String iDWorker, String nameWorker, Queue skills) {
        setiDWorker(iDWorker);
        this.nameWorker = nameWorker;
        this.skills = skills;
    }

    public Worker() {

    }

    public Worker(String iDWorker) {
        this.idWorker = iDWorker;
    }

    public Worker(String iDWorker, String nameWorker) {
        this.idWorker = iDWorker;
        this.nameWorker = nameWorker;

    }

    public String getiDWorker() {
        return idWorker;
    }
/**
 * Método que te modifica el DNI dek Customer añadiendole su letra<br>
 * mediante un complicado algorithmo
 * @param iDWorker 
 */
    public void setiDWorker(String iDWorker) {

        String STRING_DE_ASOCIACION_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numberID = Integer.parseInt(String.valueOf(iDWorker));
        this.idWorker = numberID +""+STRING_DE_ASOCIACION_NIF.charAt(numberID % 23);


    }

    public String getNameWorker() {
        return nameWorker;
    }

    public void setNameWorker(String nameWorker) {
        this.nameWorker = nameWorker;
    }

    public Queue getSkills() {
        return skills;
    }

    public void setSkills(Queue skills) {
        this.skills = skills;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
/**
 * Método sobreescrito que devuelve true o false según si el Worker tiene el mismo id
 * @param obj
 * @return boolean
 */
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
        final Worker other = (Worker) obj;
        if (!Objects.equals(this.idWorker, other.idWorker)) {
            return false;
        }
        return true;
    }

}
