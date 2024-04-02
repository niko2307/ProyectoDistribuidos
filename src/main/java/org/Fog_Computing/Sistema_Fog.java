package org.Fog_Computing;

import java.util.Map;

public class Sistema_Fog {
    public static void calcularTemp(Map<String, Double> Temperatura){
        double promedioTemp,tempTotal = 0;
        int tamTemp = Temperatura.size();

        for (Map.Entry<String, Double> entry : Temperatura.entrySet()) {
            tempTotal += entry.getValue();
        }
        promedioTemp = tempTotal/tamTemp;
        System.out.println("Promedio de las mediciones de temperatura: " + promedioTemp);
        System.out.println("Fecha mediciones    :");
        for (Map.Entry<String, Double> entry : Temperatura.entrySet()) {
            System.out.println(entry.getKey());
        }
        if(promedioTemp > 29.4){
            System.out.println("Al calcular el promedio de temperatura de \nlas mediciones de los sensores \nse encontro que supera el promedio");
        }
    }

    public static void calcularHumedad (Map<String, Double> mapaHumedad){
        double promedioHum,humTotal = 0;

        for (Map.Entry<String, Double> entry : mapaHumedad.entrySet()) {
            humTotal += entry.getValue();
        }
        promedioHum = humTotal/mapaHumedad.size();
        System.out.println("Pormedio de las mediciones de humedad " + promedioHum);
    }
}
