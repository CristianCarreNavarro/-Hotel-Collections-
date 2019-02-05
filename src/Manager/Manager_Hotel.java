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

    HashMap<Room, Customer> listRooms = new HashMap<>();

    HashSet<Worker> listWorkers = new HashSet<>();
    HashSet<Worker> listWorkersBussy = new HashSet<>();

    HashMap<Integer, Customer> listCustomers = new HashMap<>();

    HashMap<Room, Worker> listRequest = new HashMap<>();
    HashMap<Room, Customer> listPendingRequest = new HashMap<>();

//      public static String letterOFNif(String numerosDni) {
//
//        int numeros = Integer.valueOf(numerosDni);
//
//        return String.valueOf(numerosDni) + STRING_DE_ASOCIACION_NIF.charAt(numeros % 23);
//
//    }
    public enum StatusRoom {
        CLEAN, UNCLEAN, BROKEN, RESERVED;
    }

    public enum ServiceRooms {
        TV, BALCON, CAMADOBLE, JACUZZI, MINIBAR, TELEFONO, SATELITE, SWEET;

        public static ServiceRooms getServiceRoom(String stringofService) {

            switch (stringofService.toUpperCase()) {

                case "TV":
                    return ServiceRooms.TV;

                case "BALCON":
                    return ServiceRooms.BALCON;

                case "CAMADOBLE":
                    return ServiceRooms.CAMADOBLE;

                case "JACUZZI":
                    return ServiceRooms.JACUZZI;

                case "MINIBAR":
                    return ServiceRooms.MINIBAR;

                case "TELEFONO":
                    return ServiceRooms.TELEFONO;

                case "SATELITE":
                    return ServiceRooms.SATELITE;

                case "SWEET":
                    return ServiceRooms.SWEET;

                default:
                    break;
            }
            return null;
        }

    }

    public enum SkillsWorkers {
        MANTENIMIENTO, LIMPIEZA, PISCINA, SPA, BAR, COMIDA, LAVANDERIA;

        public static SkillsWorkers getSkillsWorkers(String stringofSkill) {

            switch (stringofSkill.toUpperCase()) {

                case "MANTENIMIENTO":
                    return SkillsWorkers.MANTENIMIENTO;

                case "LIMPIEZA":
                    return SkillsWorkers.LIMPIEZA;

                case "PISCINA":
                    return SkillsWorkers.PISCINA;

                case "SPA":
                    return SkillsWorkers.SPA;

                case "BAR":
                    return SkillsWorkers.BAR;

                case "COMIDA":
                    return SkillsWorkers.COMIDA;

                case "LAVANDERIA":
                    return SkillsWorkers.LAVANDERIA;

                default:
                    break;
            }
            return null;
        }

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

        for (int i = 0; i < datos_separados.length - 1; i++) {

        }
        for (int i = 0; i < datos_separados.length; i++) {
            System.out.print(datos_separados[i] + " ");
        }

        try {
            switch (datos_separados[0].toUpperCase()) {

                case "SPEED":
                    if (2 != datos_separados.length) {
                        throw new HotelExcepcion(2);
                    }
                    break;

                case "ROOM":

                    createRoom(datos_separados);
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
                    throw new HotelExcepcion(2);
            }

        } catch (HotelExcepcion ex) {
            System.out.println(ex.getMessage());

        }

    }


    //----------------------------------------------------------------------ROOM------------------------------------------------------------------------------------------------------------------------------
    public void createRoom(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();
        Customer customer = new Customer();
        if (datos_separados[1].contains("13")) {
            throw new HotelExcepcion(4);
        }

        if (datos_separados.length == 4) {

            listservices = getListofServicesorSkills("services", datos_separados[3]);

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices, StatusRoom.CLEAN, null);

            listRooms.put(newRoom, customer);

        } else {

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), StatusRoom.CLEAN, "EMPTY");

            listRooms.put(newRoom, customer);

        }

        System.out.println(green + "\n--> new Room added " + datos_separados[1] + " <--" + resett);
    }
  public void insertReservation(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();

        boolean isDigit = isNumeric(datos_separados[1]);

        if (!isDigit) {

            throw new HotelExcepcion(7);

        } else if (datos_separados[1].length() != 8) {

            throw new HotelExcepcion(7);
        }

        switch (datos_separados.length) {

            case 4:

                listservices = getListofServicesorSkills("services", datos_separados[3]);

                Customer newCustomer = new Customer(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices);

                listCustomers.put(newCustomer.getIdCustomer(), newCustomer);

                asignTheBestRoom(newCustomer);

                break;
            case 3:
                newCustomer = new Customer(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]));
                listCustomers.put(newCustomer.getIdCustomer(), newCustomer);
                asignTheBestRoom(newCustomer);

                break;

            default:
                throw new HotelExcepcion(3);

        }

    }
    public Room asignTheBestRoom(Customer customer) throws HotelExcepcion {
        Room roomAsign = new Room();
        boolean sameCapacity = false;
        boolean asigned = false;
        int contador = 0;

        for (Room room : listRooms.keySet()) {
            if (listRooms.get(room).getIdCustomer() == customer.getIdCustomer()) {
                throw new HotelExcepcion(8);
            }
        }
        while ((sameCapacity == false) && (contador != listRooms.size())) {

            for (Room room1 : listRooms.keySet()) {
                contador++;

                if (room1.getStatus() == StatusRoom.CLEAN) {

                    if (room1.getCapacity() == customer.getCapacityCustomer()) {

                        if (haveALLwantCustomer(room1, customer)) {
                            room1.setStatus(StatusRoom.RESERVED);
                            listRooms.put(room1, customer);
                            roomAsign = room1;
                            sameCapacity = true;
                            asigned = true;
                            break;
                        }
                    }
                }
            }
            if (sameCapacity == false) {
                customer.setCapacityCustomer(customer.getCapacityCustomer() + 1);
            }
        }
        if (asigned == false) {
            System.out.println(purple + "\n--> Customer " + customer.getIdCustomer() + " not asigned. You loose 100€ <--" + resett);
            money -= 100;
            roomAsign = null;
        } else {
            System.out.println(green + "\n-->Assigned " + customer.getIdCustomer() + " to Room " + roomAsign.getNumberRoom() + " <--" + resett);
        }
        return roomAsign;
    }

    public void requestOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room rom = new Room();
        Customer customer = new Customer();
        HashSet<Enum> listofRequest = new HashSet<>();
        Worker worker = new Worker();

        if (datos_separados.length <= 2) {
            throw new HotelExcepcion(5);
        }
        if (listPendingRequest.containsValue(customer)) {
            throw new HotelExcepcion(9);
        }

        listofRequest = getListofServicesorSkills("skills", datos_separados[2]);

        for (Enum req : listofRequest) {

            worker = findWorkerToService(req);

            if (worker != null) {
                System.out.println("\n" + green + "--> Worker " + worker.getNameWorker() + " assigned to Room " + datos_separados[1] + "<--" + resett);

            } else {
                rom = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));
                customer = checkCustomerinRoom(rom);
                listPendingRequest.put(rom, customer);
                System.out.println("\n" + purple + "--> No Worker avaliable for this Service. Added to Customer pending Requests <--" + resett);

            }
        }
    }
    public Room getNumberRoomwithParametre(int number) {
        Room room = new Room();

        for (Room roomList : listRooms.keySet()) {

            if (roomList.getNumberRoom() == number) {

                room = roomList;
            }
        }

        return room;
    }
  

    //----------------------------------------------------------------------CUSTOMER------------------------------------------------------------------------------------------------------------------------------
    public boolean haveALLwantCustomer(Room room1, Customer customer) {

        HashSet<String> listservicesofRoom = new HashSet<>();

        boolean roomHaveAll = false;
        listservicesofRoom = room1.getService();

        if ((listservicesofRoom != null) && (customer.getServicesCustomer() != null)) {

            if (listservicesofRoom.containsAll(customer.getServicesCustomer())) {

                roomHaveAll = true;
            }
        } else if (customer.getServicesCustomer() == null) {
            roomHaveAll = true;
        }
        return roomHaveAll;
    }

   
 public Customer checkCustomerinRoom(Room romBroken) {

        Customer customerTochange = new Customer();
        for (Room room : listRooms.keySet()) {
            if (room.getNumberRoom() == romBroken.getNumberRoom()) {
                customerTochange = listRooms.get(room);
            }
        }
        return customerTochange;
    }



    //*************************************************WORKERS*******************************************************************************
    public HashSet<Enum> checkWorker(String datos_separados[]) throws HotelExcepcion {

        HashSet<Enum> listSkills = new HashSet<>();
        boolean isDigit = isNumeric(datos_separados[1]);

        if (!isDigit) {

            throw new HotelExcepcion(1);

        } else if (datos_separados[1].length() != 8) {

            throw new HotelExcepcion(1);

        } else if (datos_separados.length != 4) {

            throw new HotelExcepcion(5);

        } else {

            for (Worker worker : listWorkers) {

                if (worker.getNameWorker().equalsIgnoreCase(datos_separados[2])) {
                    throw new HotelExcepcion(6);

                }
            }
            listSkills = getListofServicesorSkills("skills", datos_separados[3]);
            String listString[] = datos_separados[3].split(",");

            for (String string : listString) {
                SkillsWorkers skill = SkillsWorkers.getSkillsWorkers(string);

                if (skill == null) {
                    throw new HotelExcepcion(6);
                }
            }

            return listSkills;
        }
    }

    public void insertWorker(String datos_separados[]) throws HotelExcepcion {
        HashSet<Enum> listSkills = checkWorker(datos_separados);
        Worker newWorker = new Worker(Integer.parseInt(datos_separados[1]), datos_separados[2], listSkills);
        newWorker.setiDWorker(Integer.parseInt(datos_separados[1]));
        listWorkers.add(newWorker);
        System.out.println(green + "\n--> new Worker added " + newWorker.getiDWorker() + " <--" + resett);

    }

    public Worker findWorkerToService(Enum key) {

        HashSet<Enum> listservicesWorker = new HashSet<>();

        for (Worker worker1 : listWorkersBussy) {

            listservicesWorker = worker1.getSkills();

            if (listservicesWorker != null) {

                boolean idemServices = listservicesWorker.contains(key);

                if (idemServices) {
                    listWorkersBussy.add(worker1);

                    return worker1;
                }
            }
        }

        return null;
    }
    
    
    
    
    
    
    
    //*************************************************OTHERS*******************************************************************************  
    
    
    
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    
    
     public void showHotel() {

        System.out.println("\n" + red + "====================================");

        for (Room room1 : listRooms.keySet()) {
            if (listRooms.get(room1).getIdCustomer() != 0) {
                System.out.println(red + "==  ROOM:" + room1.getNumberRoom() + "    CUSTOMER: " + listRooms.get(room1).getIdCustomer() + " ==");
            } else {
                System.out.println(red + "==  ROOM:" + room1.getNumberRoom() + "    EMPTY ==");

            }
        }
        System.out.println(red + "====================================" + resett);

    }
    
    
    public void resolveProblem(String datos_separados[]) throws HotelExcepcion {

        Room romBroken = new Room();
        Customer customerEmpty = new Customer();
        Customer customerTochange = new Customer();

        if (datos_separados.length != 2) {
            throw new HotelExcepcion(2);
        }

        romBroken = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));

        romBroken.setStatus(StatusRoom.BROKEN);

        customerTochange = checkCustomerinRoom(romBroken);

        if (customerTochange == null) {
            throw new HotelExcepcion(7);
        }

        listRooms.remove(romBroken);
        listRooms.put(romBroken, customerEmpty);

        if (customerTochange != null) {

            asignTheBestRoom(customerTochange);
        }
        System.out.println(purple + "--> Room number: " + datos_separados[1] + " set as BROKEN <--");

    }

   

    public void leaveOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room romFree = new Room();
        Customer customerToLeave = new Customer();

        if (datos_separados.length != 3) {
            throw new HotelExcepcion(2);
        }

        romFree = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));
        customerToLeave = checkCustomerinRoom(romFree);

        if (customerToLeave == null) {
            throw new HotelExcepcion(5);
        }
        String moneyOfCustomer = datos_separados[2].substring(0, datos_separados[2].length() - 1);

        for (Room room : listRequest.keySet()) {

            if (room.getNumberRoom() == romFree.getNumberRoom()) {

                System.out.println("\n" + green + "--> Worker " + listRequest.get(room).getNameWorker() + " desasigned<--" + resett);

            }
        }

        listRequest.remove(romFree);
        romFree.setStatus(StatusRoom.CLEAN);
        System.out.println("\n" + purple + "--> Room number: " + datos_separados[1] + " free and set to UNCLEAN <--" + resett);

        if (listPendingRequest.containsValue(customerToLeave)) {
            double moneyHalf = Integer.parseInt(moneyOfCustomer) / 2;
            System.out.println("\n" + purple + "--> Unsatisfied clients.You loose " + moneyHalf + " €" + resett);
        }

    }

    public HashSet getListofServicesorSkills(String option, String datos) {

        HashSet<Enum> list = new HashSet<>();

        if (option.equalsIgnoreCase("services")) {

            if (!datos.contains(",")) {
                list.add(ServiceRooms.getServiceRoom(datos));
            } else {

                String listString[] = datos.split(",");
                for (String string : listString) {
                    list.add(ServiceRooms.getServiceRoom(string));
                    list.remove(null);
                }
            }
        } else {
            if (!datos.contains(",")) {
                list.add(ServiceRooms.getServiceRoom(datos));
            } else {
                String listString[] = datos.split(",");

                for (String string : listString) {

                    list.add(SkillsWorkers.getSkillsWorkers(string));

                }
            }
        }

        return list;
    }
}
