/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import ExcepcionHotel.HotelExcepcion;
import Manager.Enums.ServiceRooms;
import Manager.Enums.SkillsWorkers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import model.Customer;
import model.Room;
import model.Worker;
import java.util.Queue;

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
    boolean noMoney = false;
    int money = 1000;
    int days = 0;
    int speed = 0;

    HashMap<Room, Customer> listRooms = new HashMap<>();
    HashSet<Worker> listWorkers = new HashSet<>();
    HashSet<Worker> listWorkersBussy = new HashSet<>();
    HashSet<Customer> listCustomers = new HashSet<>();
    HashMap<Room, Customer> listPendingRequest = new HashMap<>();

    /**
     * Función que recibe un File como parametro<br>
     * y lo va leyendo linea a linea<br>
     * con cada linea llama a otra función(toDoWithPhrase) <br>
     * con la cual al devolver la variable noMoney en true tambien dejara de
     * leer el File
     *
     * @param file
     * @throws HotelExcepcion
     * @throws IOException
     */
    public void readOrdersHotel(File file) throws HotelExcepcion, IOException {

        String linea = "";

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null && noMoney == false) {
                try {
                    String frase = linea.substring(0);
                    noMoney = toDoWithPhrase(frase);
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

    public int getMoney() {
        return money;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Función que realiza una opcion determinada<br>
     * según el primer parametro de un array de String creado mediante el String
     * frase pasado
     *
     * @param frase
     * @return noMoney boolean
     * @throws HotelExcepcion
     */
    public boolean toDoWithPhrase(String frase) throws HotelExcepcion {

        String datos_separados[] = frase.split(" ");

        for (String palabra : datos_separados) {
            System.out.print(palabra + " ");
        }

        try {
            switch (datos_separados[0].toUpperCase()) {

                case "SPEED":
                    if (2 != datos_separados.length) {
                        throw new HotelExcepcion(2);
                    }
                    speed = Integer.parseInt(datos_separados[1]);
                    System.out.println("\n");
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

                    noMoney = leaveOfRoom(datos_separados);
                    break;
                case "REQUEST":

                    checkRequestOfRoom(datos_separados);
                    break;
                default:
                    throw new HotelExcepcion(2);
            }

        } catch (HotelExcepcion ex) {
            System.out.println(ex.getMessage());

        }
        return noMoney;
    }

    //----------------------------------------------------------------------ROOM------------------------------------------------------------------------------------------------------------------------------
    //*********************************************************************************************************
    /**
     * Función que crea una Room y la añade a una Coleccion de Habitaciones<br>
     * comprobando antes que largura tienen los paramteros pasados <br>
     * además MUY importante comprueba que no sea la numero 13
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
    public void createRoom(String datos_separados[]) throws HotelExcepcion {

        HashSet<String> listservices = new HashSet<>();
        Customer customer = new Customer();

        if (datos_separados[1].contains("13")) {
            throw new HotelExcepcion(4);
        }
        if (datos_separados.length == 4) {
            listservices = getListofServices(datos_separados[3]);
            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), listservices, Enums.StatusRoom.CLEAN, null);
            if (listRooms.containsKey(newRoom)) {
                throw new HotelExcepcion(11);
            }
            listRooms.put(newRoom, customer);
        } else {
            Room newRoom = new Room(Integer.parseInt(datos_separados[1]), Integer.parseInt(datos_separados[2]), Enums.StatusRoom.CLEAN, "EMPTY");
            if (listRooms.containsKey(newRoom)) {
                throw new HotelExcepcion(11);
            }
            listRooms.put(newRoom, customer);
        }

        System.out.println(green + "\n--> new Room added " + datos_separados[1] + " <--" + resett);
    }

    /**
     * Función que crea un Customer y lo ingresa en la listaCustomers <br>
     * y luego llama a la funcion asignThe BestRoom donde le asignará la más
     * adecuada a sus parametros
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
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
                listservices = getListofServices(datos_separados[3]);
                Customer newCustomer = new Customer(datos_separados[1], Integer.parseInt(datos_separados[2]), listservices);
                newCustomer.setIdCustomer(datos_separados[1]);
                listCustomers.add(newCustomer);
                asignTheBestRoom(newCustomer);
                break;
            case 3:
                newCustomer = new Customer(datos_separados[1], Integer.parseInt(datos_separados[2]));
                newCustomer.setIdCustomer(datos_separados[1]);
                listCustomers.add(newCustomer);
                asignTheBestRoom(newCustomer);
                break;
            default:
                throw new HotelExcepcion(3);

        }

    }

    /**
     * Esta Función recibe un Customer en el cual se le asignará la habitación
     * más adecuada a sus parametros<br>
     * Si la capcidad del Customer no es igual a la de la habiatacion se le
     * sumara 1 a su capacidad<br>
     * hasta asignarle la habitación con esa capacidad<br>
     * Una vez tenga la misma capacidad se le mirara si esta tiene los servivios
     * pedidos<br>
     * Luego llamará a otra función que mostrará el resultado según los
     * parametros conseguidos en esta.
     *
     * @param customer
     * @return
     * @throws HotelExcepcion
     */
    // TO DO : ORDENANDO LA LISTA ROOM ME AHORRARIA MUCHO CODIGO
    public Room asignTheBestRoom(Customer customer) throws HotelExcepcion {
        Room roomAsign = new Room();
        boolean sameCapacity = false;
        boolean asigned = false;
        int contador = 0;

        int capaCustomer = customer.getCapacityCustomer();

        for (Room room : listRooms.keySet()) {
            if (listRooms.get(room).getIdCustomer() != null) {
                if (listRooms.get(room).getIdCustomer().equals(customer.getIdCustomer())) {
                    //NO ENTIENDO PORQUE ENTRA EL USER DE LA ROOM1 AL DECIR PROBLEM 001
                    throw new HotelExcepcion(8);
                }
            }
        }
        while ((sameCapacity == false) && (contador != listRooms.size())) {

            for (Room room1 : listRooms.keySet()) {

                if (room1.getStatus() == Enums.StatusRoom.CLEAN) {

                    if (room1.getCapacity() == customer.getCapacityCustomer()) {

                        if (haveALLwantCustomer(room1, customer)) {
                            room1.setStatus(Enums.StatusRoom.RESERVED);
                            listRooms.put(room1, customer);
                            roomAsign = room1;
                            sameCapacity = true;
                            asigned = true;
                            break;
                        }
                    }
                }
            }
            contador++;
            if (sameCapacity == false) {
                customer.setCapacityCustomer(customer.getCapacityCustomer() + 1);
            }
        }
        customer.setCapacityCustomer(capaCustomer);
        resultOfAsignedonRoom(asigned, roomAsign, customer);
        return roomAsign;
    }

    /**
     * Función que te mostrara un mensaje u otro según el boolean pasado como
     * parametro<br>
     * en el cual te indicas si esra asignado o no.
     *
     * @param asigned
     * @param roomAsign
     * @param customer
     */
    public void resultOfAsignedonRoom(boolean asigned, Room roomAsign, Customer customer) {

        if (asigned == false) {
            System.out.println("\n" + purple + "--> Customer " + customer.getIdCustomer() + " not asigned. You loose 100€ <--" + resett);
            money -= 100;

        } else {
            System.out.println("\n" + green + "--> Assigned " + customer.getIdCustomer() + " to Room " + roomAsign.getNumberRoom() + " <--" + resett);
        }
    }

    /**
     * Función que comprueba prepara todos los datos a pasar a la función
     * asignWorkerOnRoom
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
    public void checkRequestOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room room = new Room();
        Customer customer = new Customer();
        Queue<SkillsWorkers> listofSkillsWantCustomer = new LinkedList<>();

        room = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));
        customer = checkCustomerinRoom(room);

        if (datos_separados.length != 3) {
            throw new HotelExcepcion(5);
        }
        if (listPendingRequest.containsValue(customer)) {
            throw new HotelExcepcion(9);
        }
        if (listRooms.get(room).getIdCustomer() == null) {
            throw new HotelExcepcion(10);
        }

        listofSkillsWantCustomer = getListofSkills(datos_separados[2]);

        asignWorkerOnRoom(room, listofSkillsWantCustomer, customer);

    }

    /**
     * Función que comprueba todos los datos pasados como parametros<br>
     * si es correcto todo, modifica el status de la habitación y llama a la
     * función <br>
     * resultOfCustomer(la cual eliminar el Customer de las listas de Customer)
     *
     * @param datos_separados
     * @return boolean
     * @throws HotelExcepcion
     */
    public boolean leaveOfRoom(String datos_separados[]) throws HotelExcepcion {

        Room roomFree = new Room();
        Customer customerToLeave = new Customer();
        HashSet<Worker> workersInRoom = new HashSet<>();

        if (datos_separados.length != 3) {
            throw new HotelExcepcion(2);
        }
        roomFree = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));
        customerToLeave = checkCustomerinRoom(roomFree);
        if (customerToLeave.getIdCustomer() == null) {
            throw new HotelExcepcion(10);
        }
        if (customerToLeave == null) {
            throw new HotelExcepcion(5);
        }
        String moneyOfCustomer = datos_separados[2].substring(0, datos_separados[2].length() - 1);
        workersInRoom = roomFree.getWorkerAsign();

        if (workersInRoom != null) {
            for (Worker worker : workersInRoom) {
                System.out.println("\n" + yellow + "--> Worker " + worker.getNameWorker() + " freed <--" + resett);
                listWorkersBussy.remove(worker);
            }
        }
        roomFree.setStatus(Enums.StatusRoom.CLEAN);
        System.out.println("\n" + purple + "--> Room  " + datos_separados[1] + " free and set to UNCLEAN <--" + resett);
        noMoney = resultOfCustomer(moneyOfCustomer, roomFree, customerToLeave);
        return noMoney;
    }



    //----------------------------------------------------------------------CUSTOMER------------------------------------------------------------------------------------------------------------------------------
    // ***********************************************************************************************************************************
    /**
     * Función que comprueba si la Room pasada como parametro tiene todos los
     * Servicios que quiere el Customer también pasado como parametro
     * <br> si es así devuelve true sino false.
     *
     * @param room1
     * @param customer
     * @return boolean
     */
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

    /**
     * Función que te devuelve el Customer dentro de la habitación que tenga el
     * mismo numero que la habitación pasada como parametro
     *
     * @param room
     * @return Customer
     */
    public Customer checkCustomerinRoom(Room room) {

        Customer customerTochange = new Customer();

        for (Room room1 : listRooms.keySet()) {
            if (room1.getNumberRoom() == room.getNumberRoom()) {
                customerTochange = listRooms.get(room);
            }
        }
        return customerTochange;
    }

    /**
     * Función que devuelve true o false <br>
     * restando o sumando el dinero en la cuenta según si <br>
     * el Customer está dentro de la lista de Request pendientes muestra un
     * system out distinto en cada caso y en ambos la habitación es borrada de
     * la lista de Request pendientes
     *
     * @param moneyOfCustomer
     * @param roomFree
     * @param customerToLeave
     * @return boolean
     */
    public boolean resultOfCustomer(String moneyOfCustomer, Room roomFree, Customer customerToLeave) {

        if (listPendingRequest.containsValue(customerToLeave)) {
            int moneyHalf = Integer.parseInt(moneyOfCustomer) / 2;
            System.out.println("\n" + purple + "--> Unsatisfied clients.You loose " + moneyHalf + " €" + resett);
            listPendingRequest.remove(roomFree);
            money -= moneyHalf;
            if (money <= 0) {
                System.out.println("\n" + red + "=========================================" + resett);
                System.out.println("\n" + yellow + "YOU'VE LOST ALL YOUR MONEY " + resett);
                System.out.println("\n" + red + "=========================================" + resett);
                noMoney = true;
            }
        } else {
            System.out.println("\n" + green + "--> Customer gave you " + moneyOfCustomer + " € <--" + resett);
            listPendingRequest.remove(roomFree);
        }
        return noMoney;
    }

    //*************************************************WORKERS*******************************************************************************
    //*******************************************************************************************************************************************   
    /**
     * Función que comprueba los datos pasados para agregar un Worker
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
    public void checkWorker(String datos_separados[]) throws HotelExcepcion {

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

        }
    }

    /**
     * Función que te crea un Worker<br>
     * modifica su id para que salga con la letra <br>
     * y te lo ingresa en la lista de Workers
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
    public void insertWorker(String datos_separados[]) throws HotelExcepcion {
        checkWorker(datos_separados);
        Queue<Enums> listSkills = getListofSkills(datos_separados[3]);
        Worker newWorker = new Worker(datos_separados[1], datos_separados[2], listSkills);
        newWorker.setiDWorker(datos_separados[1]);

        listWorkers.add(newWorker);
        System.out.println(green + "\n--> new Worker added " + newWorker.getiDWorker() + " <--" + resett);

    }

    /**
     * Función que te devuelve un Worker que tenga el Skill pasado como
     * parametro<br>
     * si no devuelve NULL
     *
     * @param skill
     * @return Worker
     */
    public Worker findWorkerToThisSkill(SkillsWorkers skill) {

        for (Worker worker1 : listWorkers) {
            if (!listWorkersBussy.contains(worker1)) {
                if (worker1.getSkills().contains(skill)) {
                    listWorkersBussy.add(worker1);
                    return worker1;
                }
            }
        }
        return null;
    }

    /**
     * Función que asigna a una Room un HashSet de Workers con las Skills
     * pedidas por el Customer<br>
     * si no hay ninguno con los Skills pedidos el customer con su Room es
     * añadido a la lista de Request pendientes<br>
     * Tambien busca si esa habitacion ya tenia Workers asignados<br>
     * para que si esa habitación volviera ha hacer una Request introduciria los
     * que ya estaban(listofWorkerswithThisSkillsAsignedBefore) más los nuevos.
     *
     * @param room
     * @param listofSkillsWantCustomer
     * @param customer
     */
    public void asignWorkerOnRoom(Room room, Queue<SkillsWorkers> listofSkillsWantCustomer, Customer customer) {

        HashSet<Worker> listofWorkerswithThisSkills = new HashSet<>();
        HashSet<Worker> listofWorkerswithThisSkillsAsignedBefore = new HashSet<>();
        Worker worker = new Worker();
        listofWorkerswithThisSkillsAsignedBefore = room.getWorkerAsign();

        for (SkillsWorkers skill : listofSkillsWantCustomer) {

            worker = findWorkerToThisSkill(skill);

            if (worker != null) {
                listofWorkerswithThisSkills.add(worker);
                System.out.println("\n" + green + "--> Worker " + worker.getNameWorker() + " assigned to Room " + room.getNumberRoom() + "<--" + resett);
            } else {
                listPendingRequest.put(room, customer);
                System.out.println("\n" + purple + "--> No Worker avaliable for " + yellow + skill + purple + " Service. Added to Customer pending Requests <--" + resett);
            }
        }
        if (listofWorkerswithThisSkillsAsignedBefore != null) {
            for (Worker worker1 : listofWorkerswithThisSkillsAsignedBefore) {
                listofWorkerswithThisSkills.add(worker1);
            }
        }
        room.setWorkerAsign(listofWorkerswithThisSkills);
    }

    //*************************************************OTHERS*******************************************************************************  
    //*****************************************************************************************************************************************
    /**
     * Función que comprueba si el String pasado por parametro es un Integer
     *
     * @param cadena
     * @return boolean
     */
    public static boolean isNumeric(String cadena) {
        boolean result;
        try {
            Integer.parseInt(cadena);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    /**
     * Función que te muestra por pantalla todas las habitaciones y te muestra
     * si hay customer o no.
     */
    public void showHotel() {

        System.out.println("\n" + red + "====================================");

        for (Room room1 : listRooms.keySet()) {
            if (listRooms.get(room1).getIdCustomer() != null) {
                System.out.println(red + "==  ROOM:" + room1.getNumberRoom() + "    CUSTOMER: " + listRooms.get(room1).getIdCustomer() + " ==");
            } else {
                System.out.println(red + "==  ROOM:" + room1.getNumberRoom() + "    EMPTY ==");
            }
        }
        System.out.println(red + "====================================" + resett);

    }

    /**
     * Función que te modifica el Status a Broken de la habitación <br>
     * comprueba que en esa habitación haya un customer y llama a otra función
     * donde será asignado a otra habitación<br>
     * más tarde remplaza la habitación rota con un customer vacio
     *
     * @param datos_separados
     * @throws HotelExcepcion
     */
    public void resolveProblem(String datos_separados[]) throws HotelExcepcion {

        Room romBroken = new Room();
        Customer customerEmpty = new Customer();
        Customer customerTochange = new Customer();

        if (datos_separados.length != 2) {
            throw new HotelExcepcion(2);
        }

        romBroken = getNumberRoomwithParametre(Integer.parseInt(datos_separados[1]));
        romBroken.setStatus(Enums.StatusRoom.BROKEN);
        customerTochange = checkCustomerinRoom(romBroken);

        if (customerTochange == null) {
            throw new HotelExcepcion(7);
        }
        asignTheBestRoom(customerTochange);
        listRooms.replace(romBroken, customerEmpty);
        /*
        listRooms.remove(romBroken);
        listRooms.put(romBroken, customerEmpty);*/

        System.out.println(purple + "--> Room number: " + datos_separados[1] + " set as BROKEN <--" + resett);

    }
/**
 * Función que te devuelve una lista de Enums de  los servicios que són iguales a los parametros pasados.
 * @param datos
 * @return HashSet
 * @throws HotelExcepcion 
 */
    public HashSet getListofServices(String datos) throws HotelExcepcion {

        HashSet<ServiceRooms> list = new HashSet<>();
        ServiceRooms service = ServiceRooms.BALCON;
        if (!datos.contains(",")) {
            list.add(service.getServiceRoom(datos));
        } else {
            String listString[] = datos.split(",");
            for (String string : listString) {
                list.add(service.getServiceRoom(string));
                list.remove(null);
            }
        }
        return list;
    }
/**
 * Función que te devuelveuna Queue con todos los Enum que sean iguales a los parametros pasados.
 * @param datos
 * @return Queue
 * @throws HotelExcepcion 
 */
    public Queue getListofSkills(String datos) throws HotelExcepcion {

        Queue<SkillsWorkers> queue = new LinkedList<>();
        SkillsWorkers skill = SkillsWorkers.BAR;

        if (!datos.contains(",")) {
            queue.add(skill.getSkillsWorkers(datos));
        } else {
            String listString[] = datos.split(",");
            for (String string : listString) {
                queue.add(skill.getSkillsWorkers(string));
            }
        }
        if (queue == null) {
            throw new HotelExcepcion(6);
        }
        return queue;
    }
        /**
     * Función que devuelve la habitación con el numero de habitacion pasado
     * como parametro
     *
     * @param number
     * @return Room
     */
    public Room getNumberRoomwithParametre(int number) {

        for (Room room : listRooms.keySet()) {
            if (room.getNumberRoom() == number) {
                return room;
            }
        }
        return null;
    }

}
