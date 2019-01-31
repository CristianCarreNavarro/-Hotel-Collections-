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
    String purple = "\033[35m";
    int money = 1000;
    int days = 0;

    HashMap<Integer, Room> listRooms = new HashMap<>();
    HashMap<Integer, Room> listRoomsCLEAN = new HashMap<>();

    HashMap<Integer, Worker> listWorkersNoBussy = new HashMap<>();
    HashMap<Integer, Worker> listWorkers = new HashMap<>();

    HashMap<Integer, Customer> listCustomers = new HashMap<>();
    HashMap<Integer, Customer> listRequest = new HashMap<>();

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
                case "LEAVE":

                    leaveOfRoom(datos_separados);
                    break;
                case "REQUEST":

                    requestOfRoom(datos_separados);
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

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices, statusRoom.CLEAN, "EMPTY", null);

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

                asignTheBestRoom(newCustomer);

                break;
            case 3:
                newCustomer = new Customer(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]));
                listCustomers.put(newCustomer.getIdCustomer(), newCustomer);
                System.out.println("RESERVATION " + datos_separados[1] + " " + datos_separados[2]);
                asignTheBestRoom(newCustomer);

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
            System.out.println(purple + "--> Customer " + customer.getIdCustomer() + " not asigned. You loose 100€ <--" + resett);
            money -= 100;
            roomAsign = null;
        } else {
            System.out.println(green + "-->Assigned " + customer.getIdCustomer() + " to Room " + roomAsign.getNumberRoom() + " <--" + resett);

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

        if (datos_separados.length != 2) {
            throw new HotelExcepcion(2);
        }

        System.out.println(datos_separados[0] + " " + datos_separados[1]);

        romBroken = listRooms.get(Integer.parseInt(datos_separados[1]));

        romBroken.setStatus(statusRoom.BROKEN);

        customerTochange = checkCustomer(romBroken);

        if (customerTochange != null) {

            asignTheBestRoom(customerTochange);

        }
        System.out.println(purple + "--> Room number: " + datos_separados[1] + " set as BROKEN <--");

    }

    public Customer checkCustomer(Room romBroken) {
        int check = 0;
        Customer customerTochange = new Customer();
        for (Integer id : listCustomers.keySet()) {
            customerTochange = listCustomers.get(id);

            if (romBroken.getCustomer().equalsIgnoreCase(Integer.toString(customerTochange.getIdCustomer()))) {
                if (customerTochange.getServicesCustomer() == null) {
                    check++;
                } else {
                    if (romBroken.getService().containsAll(customerTochange.getServicesCustomer())) {
                        check++;
                    }
                }
                if (romBroken.getCapacity() >= customerTochange.getCapacityCustomer()) {
                    check++;
                }
                if (check == 2) {
                    return customerTochange;
                }
            }
        }
        return customerTochange;
    }

    public void leaveOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room romFree = new Room();
        HashMap<Integer, Worker> listWorkersAsigned = new HashMap<>();
        Customer customerToLeave = new Customer();

        int check = 0;
        if (datos_separados.length != 3) {
            throw new HotelExcepcion(2);
        }

        String moneyOfCustomer = datos_separados[2].substring(0, datos_separados[2].length() - 1);

        romFree = listRooms.get(Integer.parseInt(datos_separados[1]));
        customerToLeave = checkCustomer(romFree);
        romFree.setCustomer("EMPTY");

        if (romFree.getWorkerAsign() != null) {

            listWorkersAsigned = romFree.getWorkerAsign();

            for (Integer key : listWorkersAsigned.keySet()) {
                System.out.println(green + "--> Worker " + listWorkersAsigned.get(key).getNameWorker() + " desasigned<--" + resett);
            }

            romFree.setWorkerAsign(null);
        }

        romFree.setStatus(statusRoom.UNCLEAN);
        System.out.println(purple + "--> Room number: " + datos_separados[1] + " free and set to UNCLEAN <--");

        for (Integer key : listRequest.keySet()) {
            if (customerToLeave.getServicesCustomer() == null) {
                check++;
            } else if (listRequest.get(key).getServicesCustomer().equals(customerToLeave.getServicesCustomer())) {
                check++;
            }
            if (customerToLeave.getIdCustomer() == listRequest.get(key).getIdCustomer() && (customerToLeave.getCapacityCustomer() == listRequest.get(key).getCapacityCustomer())) {
                check++;
            }
            if (check == 2) {
                double moneyHalf = Integer.parseInt(moneyOfCustomer) / 2;
                System.out.println(purple + "--> Unsatisfied clients.You loose " + moneyHalf + " E" + resett);

            } else {
                check = 0;
            }
        }

    }

    public void requestOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room rom = new Room();
        Customer customer = new Customer();
        HashSet<String> listservices = new HashSet<>();

        if (datos_separados.length < 2) {
            throw new HotelExcepcion(2);
        }

        listservices = getListofServicesorSkills(datos_separados[2]);

        for (String key1 : listservices) {

            //QUIERES DEVOLVER UNA CONSTACIA DEL ENUM Y TE DEVUELVE 1
            skillsWorkers serviceCustomer = Enum.valueOf(skillsWorkers.class, key1);
            Worker worker = findWorkerToService(serviceCustomer);
            
            if (worker != null) {
                System.out.println("--> Worker " + worker.getNameWorker() + " assigned to Room " + datos_separados[1] + "<--");
            } else {
                  rom = listRooms.get(Integer.parseInt(datos_separados[1]));
              customer = checkCustomer(rom);
                listRequest.put(customer.getIdCustomer(), customer);
                System.out.println("--> No Worker avaliable for this Service. Added to Customer pending Requests <--");
            }
        }
    }

    public Worker findWorkerToService(skillsWorkers serviceCutomer) {
        Worker worker = new Worker();
        HashSet<String> listservicesWorker = new HashSet<>();

        for (Integer key2 : listWorkersNoBussy.keySet()) {

            listservicesWorker = listWorkersNoBussy.get(key2).getSkills();

            for (String key3 : listservicesWorker) {

                skillsWorkers serviceWorker = Enum.valueOf(skillsWorkers.class, key3);

                boolean idemServices = serviceCutomer.name().equalsIgnoreCase(serviceWorker.name());

                if (idemServices) {
                    listWorkersNoBussy.remove(key2);
                    worker = listWorkersNoBussy.get(key2);
                    return worker;
                }
            }

        }
        return worker;
    }
}
