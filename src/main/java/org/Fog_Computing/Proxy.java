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
        // Crear contexto ZeroMQ
        ZMQ.Context context = ZMQ.context(1);

        // Crear socket de tipo ZMQ_REP
        ZMQ.Socket socket = context.socket(SocketType.PULL);

        // Enlazar el socket a un puerto
        socket.bind("tcp://localhost:5555");

        String tipoSensor;
        String tiempoSensor;
        Double valorSensor;

        Map<String, Double> mapaTemperatura = new HashMap<>();
        Map<String, Double> mapaHumedad = new HashMap<>();

        // Create a thread with an empty function
        Thread processingThread = new Thread(() -> {
            // Hilo que cada 5 segundos llamara la realizacion del promedio de humedad
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("SOY EL HILO LO INTENTE");
                    if (mapaHumedad.size() == 10) {
                        Sistema_Fog.humedadRelativaDelAire(mapaHumedad);
                        mapaHumedad.clear();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        processingThread.start();

        // Bucle para recibir solicitudes
        while (true) {
            // Recibir solicitud
            byte[] solicitud = socket.recv();
            //String hola = new String(solicitud);
            //System.out.println(hola);
            //System.out.println(Arrays.toString(solicitud));

            // Procesar solicitud
            Map<String, String> mensaje = deserialize(solicitud);

            tipoSensor = mensaje.get("tipo");
            tiempoSensor = mensaje.get("tiempo");
            valorSensor = Double.valueOf(mensaje.get("valor"));

            if(valorSensor < 0){
                System.out.println("A ocurrido un error en el sensor:" + tipoSensor);
                System.out.println("Con el Dato:" + valorSensor);
                //Logica de envio de mensaje


                //Si ocurre un error en alguno de los sensores de temperatura, se descartanlas mediciones de los demas
                if(tipoSensor.contains("Temperatura")){
                    mapaTemperatura.clear();
                }

                //Si ocurre un error en alguno de los sensores de humedad, se descartanlas mediciones de los demas
                //Si ya se tienen 10 muestras no se descartan
                if(tipoSensor.contains("Humedad") && mapaHumedad.size()<10){
                    mapaHumedad.clear();
                }
            }

            if(tipoSensor.contains("Temperatura") && valorSensor > 0){
                mapaTemperatura.put(tiempoSensor, valorSensor);
            }

            if(tipoSensor.contains("Humedad") && valorSensor > 0 && mapaHumedad.size()<10){
                mapaHumedad.put(tipoSensor, valorSensor);
            }

            if(mapaTemperatura.size() == 10){
                Sistema_Fog.promedioTemperatura(mapaTemperatura);
                mapaTemperatura.clear();
            }



        }

        // Cerrar el socket y el contexto
        //socket.close();
        //context.term();
    }

    private static Map deserialize(byte[] data) {
        // Uso de libreria JSON para deserializar
        return new Gson().fromJson(new String(data), Map.class);
    }
}
