package org.Fog_Computing;

import java.util.Map;

public class Sistema_Fog {
    public static void promedioTemperatura (Map<String, Double> mapaTemperatura){
        double suma = 0.0;
        double promedio;

        for (Map.Entry<String, Double> entry : mapaTemperatura.entrySet()) {
            suma += entry.getValue();
        }
        promedio = suma/mapaTemperatura.size();

        System.out.println("Promedio Temperatura: " + promedio);
        System.out.println("Fechas de los valores tomados:");
        for (Map.Entry<String, Double> entry : mapaTemperatura.entrySet()) {
            System.out.println(entry.getKey());
        }

        if(promedio > 29.4){
            System.out.println("El valor maximo de Temperatura se ha alcanzado");
            //Logica de envio de mensaje a cloud
        }
    }

    public static void humedadRelativaDelAire (Map<String, Double> mapaHumedad){
        double suma = 0.0;
        double promedio;

        for (Map.Entry<String, Double> entry : mapaHumedad.entrySet()) {
            suma += entry.getValue();
        }
        promedio = suma/mapaHumedad.size();

        System.out.println("Promedio Humedad: " + promedio);
        //Logica de envio de mensaje a cloud
    }
}
