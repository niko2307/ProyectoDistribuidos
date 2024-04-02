package org.Edge_Computing.Sensor;

import org.Edge_Computing.Actuador_Aspersor;
import org.Edge_Computing.Socket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class Humo extends Thread {

    private final String tipo;
    private final String config;
    private int medicion;
    private final Socket socket;

    public Humo(String tipo, String config, Actuador_Aspersor socket, Socket socket1) {
        this.tipo = tipo;
        this.config = config;
        this.socket = socket1;
    }

    @Override
    public void run() {
        double[] probabilidades = leerConfiguracion(config);
        while (true) {
            try {
                Thread.sleep(3000);
                medicion = generarMediciones(probabilidades[0], probabilidades[1]) ? 1 : 0;
                LocalDateTime hora = LocalDateTime.now();

                if (medicion == 1) {
                    //Se debe activar el aspersor porque hay humo
                    System.out.println("Alerta: Humo presente " + medicion);
                    socket.enviarMensaje(tipo, medicion, true, hora);
                } else if (medicion ==0) {
                    socket.enviarMensaje(tipo, medicion, false, hora);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getValor() {
        return medicion;
    }

    private boolean generarMediciones(double probHumoP, double probHumoA) {

        double valor = Math.random();
        if (valor < probHumoP) {
            return true;
        } else if (valor < (probHumoP + probHumoA)) {
            return false;
        } else {
            return false;
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
