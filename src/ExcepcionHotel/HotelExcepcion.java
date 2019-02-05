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
                message = red + "\n< Wrong identifier for worker  >" + resett;
                break;
            case 2:
                message = red + "\n< Wrong Parameter >" + resett;
                break;
            case 3:
                message = red + "\n< Incorrect option >" + resett;
                break;
            case 4:
                message = red + "\n< No ROOM 13 can be added >" + resett;
                break;
            case 5:
                message = red + "\n< Wrong number of Parameter >" + resett;
                break;
            case 6:
                message = red + "\n< Wrong Worker >" + resett;
                break;
            case 7:
                message = red + "\n< Wrong identifier for reservation >" + resett;
                break;
            case 8:
                message = red + "\n< Wrong Customer >" + resett;
                break;
            case 9:
                message = red + "\n< This Customer is pending Requests>" + resett;
                break;
        }
        return message;
    }

}
