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

    public void createRoom(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();

        if (datos_separados[1].contains("13")) {
            throw new HotelExcepcion(4);
        }

        if (datos_separados.length == 4) {

            listservices = getListofServicesorSkills("services", datos_separados[3]);

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices, StatusRoom.CLEAN, "EMPTY", null);

            listRooms.put(newRoom.getNumberRoom(), newRoom);

        } else {

            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), StatusRoom.CLEAN, "EMPTY");

            listRooms.put(newRoom.getNumberRoom(), newRoom);

        }

        System.out.println(green + "\n--> new Room added " + datos_separados[1] + " <--" + resett);
    }

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

            for (Integer worker : listWorkers.keySet()) {

                if (listWorkers.get(worker).getNameWorker().equalsIgnoreCase(datos_separados[2])) {
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
        listWorkers.put(newWorker.getiDWorker(), newWorker);
        listWorkersNoBussy.put(newWorker.getiDWorker(), newWorker);
        System.out.println(green + "\n--> new Worker added " + newWorker.getiDWorker() + " <--" + resett);

    }

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

    public void insertReservation(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();

        boolean isDigit = isNumeric(datos_separados[1]);

            if (!isDigit) {

            throw new HotelExcepcion(1);

        } else if (datos_separados[1].length() != 8) {

            throw new HotelExcepcion(7);}

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

    public Room asignTheBestRoom(Customer customer) {
        Room roomAsign = new Room();
        boolean sameCapacity = false;
        boolean asigned = false;

        for (Integer room1 : listRooms.keySet()) {

            if (listRooms.get(room1).getStatus() == StatusRoom.CLEAN) {

                listRoomsCLEAN.put(listRooms.get(room1).getNumberRoom(), listRooms.get(room1));
            }
        }

        for (Integer room1 : listRoomsCLEAN.keySet()) {

            if (listRooms.get(room1).getCapacity() == customer.getCapacityCustomer()) {

                if (haveALLwantCustomer(listRooms.get(room1), customer)) {

                    listRooms.get(room1).setCustomer(Integer.toString(customer.getIdCustomer()));

                    listRooms.get(room1).setStatus(StatusRoom.RESERVED);
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
                        listRooms.get(room1).setStatus(StatusRoom.RESERVED);
                        listRoomsCLEAN.remove(room1);
                        roomAsign = listRooms.get(room1);
                        asigned = true;
                        break;
                    }
                }

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

        romBroken.setStatus(StatusRoom.BROKEN);

        customerTochange = checkCustomerinRoom(romBroken);

        if (customerTochange != null) {

            asignTheBestRoom(customerTochange);

        }
        System.out.println(purple + "--> Room number: " + datos_separados[1] + " set as BROKEN <--");

    }

    public Customer checkCustomerinRoom(Room romBroken) {
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
        customerToLeave = checkCustomerinRoom(romFree);
        romFree.setCustomer("EMPTY");

        if (romFree.getWorkerAsign() != null) {

            listWorkersAsigned = romFree.getWorkerAsign();

            for (Integer key : listWorkersAsigned.keySet()) {
                System.out.println(green + "--> Worker " + listWorkersAsigned.get(key).getNameWorker() + " desasigned<--" + resett);
            }
            romFree.setWorkerAsign(null);
        }

        romFree.setStatus(StatusRoom.UNCLEAN);
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

    public void requestOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room rom = new Room();
        Customer customer = new Customer();
        HashSet<Enum> listofRequest = new HashSet<>();

        if (datos_separados.length <= 2) {
            throw new HotelExcepcion(2);
        }

        listofRequest = getListofServicesorSkills("skills", datos_separados[2]);

        for (Enum req : listofRequest) {

            Worker worker = findWorkerToService(req);

            if (worker != null) {
                System.out.println("--> Worker " + worker.getNameWorker() + " assigned to Room " + datos_separados[1] + "<--");
            } else {
                rom = listRooms.get(Integer.parseInt(datos_separados[1]));
                customer = checkCustomerinRoom(rom);
                listRequest.put(customer.getIdCustomer(), customer);
                System.out.println("--> No Worker avaliable for this Service. Added to Customer pending Requests <--");
            }
        }
    }

    public Worker findWorkerToService(Enum key) {

        Worker worker = new Worker();
        HashSet<Enum> listservicesWorker = new HashSet<>();

        for (Integer work : listWorkersNoBussy.keySet()) {

            listservicesWorker = listWorkersNoBussy.get(work).getSkills();

            if (listservicesWorker != null) {

                boolean idemServices = listservicesWorker.contains(key);

                if (idemServices) {
                    listWorkersNoBussy.remove(work);
                    worker = listWorkersNoBussy.get(work);
                    return worker;
                }
            }
        }

        return worker;
    }
}
