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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import model.Customer;
import model.Room;
import model.Worker;

/**
 *
 * @author CRISTIAN
 */
public class Manager_Hotel {

    String red = "\033[31m";
    String resett = "\u001B[0m";
    String green = "\033[32m";
    String blue = "\033[34m";
    String yellow = "\033[33m";
    int money = 1000;
    int days = 0;

    Map businessWorkers;

    HashMap<Integer, Room> listRooms = new HashMap<>();
    HashMap<Integer, Room> listRoomsCLEAN = new HashMap<>();

    HashMap<Integer, Customer> listCustomers = new HashMap<>();
    HashMap<Integer, Worker> listWorkers = new HashMap<>();

    public enum statusRoom {
        CLEAN, UNCLEAN, BROKEN, RESERVED;
    }

    public enum serviceRooms {
        TV, BALCON, CAMADOBLE, JACUZZI, MINIBAR, TELEFONO, SATELITE, SWEET;
    }

    public enum skillsWorkers {
        MANTENIMIENTO, LIMPIEZA, PISCINA, SPA, BAR, COMIDA, LAVANDERIA;
    }

    public void readOrdersHotel(File file) throws HotelExcepcion, IOException {

        days++;
        String linea = "";
        System.out.println(yellow + "=========================================" + resett);
        System.out.println(yellow + "DAY: " + days + "  ===   Money: " + money + resett);
        System.out.println(yellow + "=========================================" + resett);
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                try {
                    String frase = linea.substring(0);
                    toDoWithParameters(frase);
                } catch (HotelExcepcion ex) {
                    System.out.println(ex.getMessage());
                }
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
    }

    public void toDoWithParameters(String frase) throws HotelExcepcion {

        String datos_separados[] = frase.split(" ");
        try {
            switch (datos_separados[0].toUpperCase()) {

                case "SPEED":
                    if (2 != datos_separados.length) {
                        throw new HotelExcepcion(2);
                    }
                    break;

                case "ROOM":

                    roomCreate(datos_separados);
                    break;

                case "WORKER":

                    insertWorker(datos_separados);
                    break;

                case "RESERVATION":

                    insertReservation(datos_separados);
                    break;
                case "HOTEL":

                    showHotel();
                    break;
                case "PROBLEM":

                    resolveProblem(datos_separados);
                    break;
                default:
                    throw new HotelExcepcion(3);
            }

        } catch (HotelExcepcion ex) {
            throw new HotelExcepcion(2);

        }

    }

    public void roomCreate(String datos_separados[]) {

        HashSet<String> listservices = new HashSet<>();

        if (datos_separados.length == 4) {

            listservices = getListofServicesorSkills(datos_separados[3]);

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices, statusRoom.CLEAN, "EMPTY");

            listRooms.put(newRoom.getNumberRoom(), newRoom);
            System.out.println("ROOM " + datos_separados[1] + " " + datos_separados[2] + " " + datos_separados[3]);

        } else {

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), statusRoom.CLEAN, "EMPTY");

            listRooms.put(newRoom.getNumberRoom(), newRoom);
            System.out.println("ROOM " + datos_separados[1] + " " + datos_separados[2]);

        }

        System.out.println(green + "--> new Room added " + datos_separados[1] + " <--" + resett);
    }

    public void insertWorker(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listSkills = new HashSet<>();

        switch (datos_separados.length) {

            case 4:
                listSkills = getListofServicesorSkills(datos_separados[3]);

                Worker newWorker = new Worker(Integer.parseInt(datos_separados[1]), datos_separados[2], listSkills);
                listWorkers.put(newWorker.getiDWorker(), newWorker);
                System.out.println("WORKER " + datos_separados[1] + " " + datos_separados[2] + " " + datos_separados[3]);
                System.out.println(green + "--> new Worker added " + datos_separados[2] + " <--" + resett);
                break;
            case 3:
                newWorker = new Worker(Integer.parseInt(datos_separados[1]), datos_separados[2]);
                listWorkers.put(newWorker.getiDWorker(), newWorker);
                System.out.println("WORKER " + datos_separados[1] + " " + datos_separados[2]);
                System.out.println(green + "--> new Worker added " + datos_separados[2] + " <--" + resett);
                break;
            case 2:
                newWorker = new Worker(Integer.parseInt(datos_separados[1]));
                listWorkers.put(newWorker.getiDWorker(), newWorker);
                System.out.println("WORKER " + datos_separados[1]);
                System.out.println(green + "--> new Worker added " + datos_separados[1] + " <--" + resett);
                break;

            default:
                throw new HotelExcepcion(3);

        }

    }

    public void insertReservation(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();

        switch (datos_separados.length) {

            case 4:

                listservices = getListofServicesorSkills(datos_separados[3]);

                Customer newCustomer = new Customer(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices);
                listCustomers.put(newCustomer.getIdCustomer(), newCustomer);
                System.out.println("RESERVATION " + datos_separados[1] + " " + datos_separados[2] + " " + datos_separados[3]);

                Room roomAsigned = asignTheBestRoom(newCustomer);
                if (roomAsigned != null) {
                    System.out.println(green + "--> Assigned " + datos_separados[1] + " to Room " + roomAsigned.getNumberRoom() + " <--" + resett);
                }
                break;
            case 3:
                newCustomer = new Customer(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]));
                listCustomers.put(newCustomer.getIdCustomer(), newCustomer);
                System.out.println("RESERVATION " + datos_separados[1] + " " + datos_separados[2]);

                roomAsigned = asignTheBestRoom(newCustomer);
                if (roomAsigned != null) {
                    System.out.println(green + "-->Assigned " + datos_separados[1] + " to Room " + roomAsigned.getNumberRoom() + " <--" + resett);
                }
                break;

            default:
                throw new HotelExcepcion(3);

        }

    }

    public HashSet getListofServicesorSkills(String datos) {
        HashSet<String> list = new HashSet<>();
        String services = datos;
        String listString[] = services.split(",");

        for (String string : listString) {
            list.add(string);
        }
        return list;
    }

    public Room asignTheBestRoom(Customer customer) {
        Room roomAsign = new Room();
        boolean sameCapacity = false;
        boolean asigned = false;

        for (Integer room1 : listRooms.keySet()) {

            if (listRooms.get(room1).getStatus() == statusRoom.CLEAN) {

                listRoomsCLEAN.put(listRooms.get(room1).getNumberRoom(), listRooms.get(room1));
            }
        }

        for (Integer room1 : listRoomsCLEAN.keySet()) {

            if (listRooms.get(room1).getCapacity() == customer.getCapacityCustomer()) {

                if (haveALLwantCustomer(listRooms.get(room1), customer)) {

                    listRooms.get(room1).setCustomer(Integer.toString(customer.getIdCustomer()));

                    listRooms.get(room1).setStatus(statusRoom.RESERVED);
                    listRoomsCLEAN.remove(room1);
                    sameCapacity = true;
                    roomAsign = listRooms.get(room1);
                    asigned = true;
                    break;
                }

            }

        }
        if (sameCapacity == false) {
            for (Integer room1 : listRoomsCLEAN.keySet()) {
                if (listRooms.get(room1).getCapacity() > customer.getCapacityCustomer()) {

                    if (haveALLwantCustomer(listRooms.get(room1), customer)) {

                        listRooms.get(room1).setCustomer(Integer.toString(customer.getIdCustomer()));
                        listRooms.get(room1).setStatus(statusRoom.RESERVED);
                        listRoomsCLEAN.remove(room1);
                        roomAsign = listRooms.get(room1);
                        asigned = true;
                        break;
                    }
                }

            }
        }

        if (asigned == false) {
            System.out.println(red + "--> Customer not asigned. You loose 100E <--" + resett);
            money -= 100;
            roomAsign = null;
        }

        return roomAsign;
    }

    public boolean haveALLwantCustomer(Room room1, Customer customer) {

        HashSet<String> listservicesofRoom = new HashSet<>();

        boolean roomHaveAll = false;
        listservicesofRoom = room1.getService();

        if ((listservicesofRoom != null) && (customer.getServicesCustomer() != null)) {

            if (listservicesofRoom.containsAll(customer.getServicesCustomer())) {

                roomHaveAll = true;
            }
        }
        return roomHaveAll;
    }

    public void showHotel() {
        System.out.println(red + "\nHOTEL");
        System.out.println(red + "====================================");

        for (Integer room1 : listRooms.keySet()) {
            System.out.println(red + "==  ROOM:" + listRooms.get(room1).getNumberRoom() + "   " + listRooms.get(room1).getCustomer() + " ==");
        }

        System.out.println(red + "====================================" + resett);

    }

    public void resolveProblem(String datos_separados[]) throws HotelExcepcion {
        Room romBroken = new Room();
        Customer customerTochange = new Customer();

        if (datos_separados.length == 2) {

            System.out.println(datos_separados[0] + " " + datos_separados[1]);

            romBroken = listRooms.get(Integer.parseInt(datos_separados[1]));

            romBroken.setStatus(statusRoom.BROKEN);
            String idCustomer = romBroken.getCustomer();

            for (Integer id : listCustomers.keySet()) {
                String idString = Integer.toString(id);
                if (idString.equalsIgnoreCase(idCustomer)) {
                    customerTochange = listCustomers.get(id);
                }
            }

            if (customerTochange != null) {
                System.out.println("nombre: " + customerTochange.getIdCustomer());
                System.out.println("tamaño customer " + customerTochange.getCapacityCustomer());
                System.out.println("nombre customer " + customerTochange.getIdCustomer());
                System.out.println("services customer " + customerTochange.getServicesCustomer());

                asignTheBestRoom(customerTochange);

            }
            System.out.println(red + "--> Room number: " + datos_separados[1] + " set as BROKEN <--");

        } else {
            throw new HotelExcepcion(2);
        }
    }

}
