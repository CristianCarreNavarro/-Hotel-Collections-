/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcepcionHotel;

/**
 *
 * @author CRISTIAN
 */
public class HotelExcepcion extends Exception {

    private int errorCode;
    String red = "\033[31m";
    String resett = "\u001B[0m";
    String green = "\033[32m";

    public HotelExcepcion(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public String getMessage() {

        String message = "";

        switch (errorCode) {
            case 1:
                message = red + "< Error 1 >" + resett;
                break;
            case 2:
                message = red + "< Incorrect Parameter >" + resett;
                break;
            case 3:
                message = red + "< Incorrect option >" + resett;
                break;
        }
        return message;
    }

}
