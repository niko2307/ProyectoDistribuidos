package org.Edge_Computing.Sensor;

import org.Edge_Computing.Socket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class Humedad extends Thread {

    private final String tipo;
    private final String config;
    private double medicion;
    private final Socket socket;

    public Humedad(String tipo, String config, Socket socket, Socket socket1) {
        this.tipo = tipo;
        this.config = config;
        this.socket = socket1;
    }

    @Override
    public void run() {
        double[] probabilidades = leerConfiguracion(config);
        while (true) {
            try {
                Thread.sleep(5000);
                medicion = generarMediciones(probabilidades[0], probabilidades[1]);
                LocalDateTime hora = LocalDateTime.now();
                socket.enviarMensaje(tipo, medicion, false, hora);
                if (medicion < 0.7 || medicion > 1) {
                    System.out.println("Error:Rango de humedad por fuera de los parametros de calidad " + medicion);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double getValor() {
        return medicion;
    }

    private double generarMediciones(double probabilidadRangoValido, double probabilidadRangoInvalido) {

        double valorAleatorio = Math.random();

        if (valorAleatorio < probabilidadRangoValido) {
            return Math.random() * (100 - 70) + 70;
        } else if (valorAleatorio < (probabilidadRangoValido + probabilidadRangoInvalido)) {
            return  Math.random() * (69 - 0) + 0;

        } else {
            return -1;
        }
    }

    public static double[] leerConfiguracion(String config) {
        double[] probabilidades = new double[2];

        try (BufferedReader br = new BufferedReader(new FileReader(config))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null && index < 2) {
                probabilidades[index++] = Double.parseDouble(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return probabilidades;
    }
}
