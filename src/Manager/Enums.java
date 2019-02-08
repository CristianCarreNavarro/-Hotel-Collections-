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
public class Enums {
    
    
    public enum StatusRoom {
        CLEAN, UNCLEAN, BROKEN, RESERVED;
    }

    public enum ServiceRooms {
        TV, BALCON, CAMADOBLE, JACUZZI, MINIBAR, TELEFONO, SATELITE, SWEET;

        public  ServiceRooms getServiceRoom(String stringofService) {

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
    
        public  SkillsWorkers getSkillsWorkers(String stringofSkill) {

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
}
    

