/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import ExcepcionHotel.HotelExcepcion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author CRISTIAN
 */
public class ThreadHilo implements Runnable {

    private String[] datos_separados;
    private int speed = 0;
    private boolean noMoney = false;
    private String yellow = "\033[33m";
    private String resett = "\u001B[0m";
    int days = 0;
    private Manager_Hotel manager;
    private File file;

    public ThreadHilo(File file, int veloc, Manager_Hotel manager) {
        this.speed = veloc;
        this.file = file;
        this.manager = manager;
    }
/**
 * Función que al llamarla en su contructor recibirá un File el cual lo irá leyendo frase por frase <br>
 * con el siguiente valor (veloc) le proporcionamos el intervalo de tiempo en realizar esta acción<br>
 * tambien le pasamos una instancia de la clase Manager para que utilize su función  ToDoWithPhrase con  cada frase.
 */
    @Override
    public void run() {
        String linea = "";
        FileReader fr = null;
        BufferedReader br = null;

        try {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);

                while ((linea = br.readLine()) != null && noMoney == false) {
                    days++;
                    System.out.println(yellow + "=========================================" + resett);
                    System.out.println(yellow + "DAY: " + days + "  ===   Money: " + manager.getMoney() + resett);
                    System.out.println(yellow + "=========================================" + resett);
                    try {
                        String frase = linea.substring(0);
                        noMoney = manager.toDoWithPhrase(frase);
                    } catch (HotelExcepcion ex) {
                        System.out.println(ex.getMessage());
                    }
                    Thread.sleep(speed);

                }
            } catch (IOException e) {
                System.out.println("No existe el fichero.");
            } finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }
                } catch (IOException e2) {
                    System.out.println("No hay nada que cerrar.");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
