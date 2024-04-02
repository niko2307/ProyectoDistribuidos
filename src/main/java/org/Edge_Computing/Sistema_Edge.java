package org.Edge_Computing;

import org.Edge_Computing.Sensor.Humo;
import org.Edge_Computing.Sensor.Humedad;
import org.Edge_Computing.Sensor.Temperatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema_Edge {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);;

        String direccion = "tcp://10.43.101.19:5555";

        List<Humo> sensoresHumo = new ArrayList<>();
        List<Temperatura> sensoresTemperatura = new ArrayList<>();
        List<Humedad> sensoresHumedad = new ArrayList<>();

        System.out.print("Ingrese el nombre del archivo de configuración de los sensores de Humo: ");
        String confHumo = scanner.nextLine();
        System.out.print("Ingrese el nombre del archivo de configuración de los sensores de Humedad: ");
        String confHumedad = scanner.nextLine();
        System.out.print("Ingrese el nombre del archivo de configuración de los sensores de Temperatura: ");
        String confTemp = scanner.nextLine();
        String opc;
        do {
            System.out.print("Como desea inicializar los sensores?");
            System.out.print("1) Cantidad Predeterminada");
            System.out.print("2) Cantidad Personalizada");
            opc = scanner.nextLine();
            switch (opc) {
                case "1":
                    crearSensoresHumo(10, confHumo, direccion, sensoresHumo);
                    crearSensoresTemperatura(10, confTemp, direccion, sensoresTemperatura);
                    crearSensoresHumedad(10, confHumedad, direccion, sensoresHumedad);
                    break;
                case "2":
                    System.out.print("Ingrese la cantidad de sensores de Humo: ");
                    int cantHumo = scanner.nextInt();
                    System.out.print("Ingrese la cantidad de sensores de Humedad: ");
                    int cantHumedad = scanner.nextInt();
                    System.out.print("Ingrese la cantidad de sensores de Temperatura: ");
                    int cantTemp = scanner.nextInt();
                    crearSensoresHumo(cantHumo, confHumo, direccion, sensoresHumo);
                    crearSensoresTemperatura(cantTemp, confTemp, direccion, sensoresTemperatura);
                    crearSensoresHumedad(cantHumedad, confHumedad, direccion, sensoresHumedad);
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }
        } while (!opc.equals("1") && !opc.equals("2"));


        Thread.currentThread().join();
    }
    public static void crearSensoresHumo(int cantidadSensores, String archivoConfig, String direccion, List<Humo> sensoresHumo) {

        for (int i = 0; i < cantidadSensores; i++) {
            Humo sensorDeHumo = new Humo("Sensor Humo #" + (i + 1), archivoConfig, new Actuador_Aspersor(i + 1), new Socket(direccion));
            sensoresHumo.add(sensorDeHumo);
            System.out.println("Sensor Humo #" + (i + 1) + " inicializado correctamente");
            sensorDeHumo.start();
        }
    }
    public static void crearSensoresTemperatura(int cantidadSensores, String archivoConfig, String direccion, List<Temperatura> sensoresTemperatura) {

        for (int i = 0; i < cantidadSensores; i++) {
            Temperatura sensorDeTemperatura = new Temperatura("Sensor Temperatura #" + (i + 1), archivoConfig, new Actuador_Aspersor(i + 1), new Socket(direccion));
            sensoresTemperatura.add(sensorDeTemperatura);
            System.out.println("Sensor Temperatura #" + (i + 1) + " inicializado correctamente");
            sensorDeTemperatura.start();
        }
    }
    public static void crearSensoresHumedad(int cantidadSensores, String archivoConfig, String direccion, List<Humedad> sensoresHumedad) {

        for (int i = 0; i < cantidadSensores; i++) {
            Humedad sensorDeHumedad = new Humedad("Sensor Humedad #" + (i + 1), archivoConfig, new Actuador_Aspersor(i + 1), new Socket(direccion));
            sensoresHumedad.add(sensorDeHumedad);
            System.out.println("Sensor Humeda #" + (i + 1) + " inicializado correctamente");
            sensorDeHumedad.start();
        }
    }

}
