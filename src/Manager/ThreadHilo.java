/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;


/**
 *
 * @author CRISTIAN
 */
public class ThreadHilo implements Runnable {


    private String[] datos_separados;
    private int speed=0;

    public ThreadHilo(String[] datos, int veloc) {

        this.datos_separados = datos;
        this.speed = veloc;
    }

    @Override
    public void run() {
        
        try {
            for(String palabre : datos_separados){
                System.out.println(palabre);
                Thread.sleep(speed);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
