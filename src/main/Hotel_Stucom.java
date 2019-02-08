/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ExcepcionHotel.HotelExcepcion;
import Manager.Manager_Hotel;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author CRISTIAN
 */
public class Hotel_Stucom {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Manager_Hotel manager = new Manager_Hotel();
        File document1 = new File("LoadHotel2.txt");
        File document2 = new File("inputOrders2.txt");
        try {

           manager.readOrdersHotel(document1);
           manager.readOrdersHotel(document2);
            
        
        
        } catch (HotelExcepcion ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            //PERSISTENCIA
            System.out.println(ex.getMessage());

        }
    }

}
