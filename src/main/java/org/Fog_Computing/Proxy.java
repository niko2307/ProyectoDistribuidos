package org.Fog_Computing;

import com.google.gson.Gson;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Proxy {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(SocketType.PULL);
        socket.bind("tcp://localhost:5555");
        String tipo, tiempo;
        Double dato;
        Map<String, Double> Temperatura = new HashMap<>();
        Map<String, Double> Humedad = new HashMap<>();

        Thread processingThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    if (Humedad.size() == 10) {
                        Sistema_Fog.calcularHumedad(Humedad);
                        Humedad.clear();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        processingThread.start();
        while (true) {
            byte[] mensajeSensor = socket.recv();
            Map<String, String> paquete = deserialize(mensajeSensor);
            tipo = paquete.get("Tipo_Sensor");
            tiempo = paquete.get("Tiempo_mensaje");
            String valorString = paquete.get("valor");
            if (valorString != null) {
                dato = Double.valueOf(valorString.trim());
            } else {
                // Manejar la situación cuando el valor es nulo
                System.err.println("Error: el valor recibido es nulo");
                continue; // Otra acción apropiada según tu lógica
            }
            if(dato < 0){
                System.out.println("Error en el sensor" + tipo + ", Al generar la medición" + dato);

                if(tipo.contains("Temperatura")){
                    Temperatura.clear();
                }
                if(tipo.contains("Humedad") && Humedad.size()<10){
                    Humedad.clear();
                }
            }
            if(tipo.contains("Temperatura") && dato > 0){
                Temperatura.put(tiempo, dato);
            }
            if(tipo.contains("Humedad") && dato > 0 && Humedad.size()<10){
                Humedad.put(tipo, dato);
            }
            if(Temperatura.size() == 10){
                Sistema_Fog.calcularTemp(Temperatura);
                Temperatura.clear();
            }
        }
    }
    private static Map deserialize(byte[] data) {

        return new Gson().fromJson(new String(data), Map.class);
    }
}
