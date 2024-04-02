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
        System.out.println("Sistema Edge inicializado");
        String direccion = "tcp://localhost:5555";

        
        List<Humo> sensoresHumo = new ArrayList<>();
        List<Temperatura> sensoresTemperatura = new ArrayList<>();
        List<Humedad> sensoresHumedad = new ArrayList<>();
        String confHumo = "C:/Users/ANA MARIA/OneDrive/Escritorio/Samuel/Sistemas distribuidos/Proyecto/ProyectoDistribuidos/src/main/java/org/Edge_Computing/Sensor/humo.txt";
        String confTemp = "C:/Users/ANA MARIA/OneDrive/Escritorio/Samuel/Sistemas distribuidos/Proyecto/ProyectoDistribuidos/src/main/java/org/Edge_Computing/Sensor/temperatura.txt";
        String confHumedad = "C:/Users/ANA MARIA/OneDrive/Escritorio/Samuel/Sistemas distribuidos/Proyecto/ProyectoDistribuidos/src/main/java/org/Edge_Computing/Sensor/humedad.txt";

        crearSensoresHumo(10, confHumo, direccion, sensoresHumo);
        crearSensoresTemperatura(10, confTemp, direccion, sensoresTemperatura);
        crearSensoresHumedad(10, confHumedad, direccion, sensoresHumedad);


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
