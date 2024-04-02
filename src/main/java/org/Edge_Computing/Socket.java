package org.Edge_Computing;

import com.google.gson.Gson;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Socket {
    private final ZContext context;
    private final ZMQ.Socket socket;

    public Socket(String direccion) {
        this.context = new ZContext();
        this.socket = context.createSocket(SocketType.PUSH);
        this.socket.connect(direccion);
    }

    public void enviarMensaje(String tipo, double valor, boolean alerta, LocalDateTime tiempo) {
        Map<String, Object> paquete = crearPaquete(tipo, valor, alerta, tiempo);

        try {
            String envio = serializar(paquete);
            socket.send(envio.getBytes(ZMQ.CHARSET), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> crearPaquete(String tipo, double valor, boolean alerta, LocalDateTime tiempo) {
        Map<String, Object> paquete = new HashMap<>();
        paquete.put("Tipo_Sensor", tipo);
        paquete.put("Valor_datos", valor);
        paquete.put("Alerta", alerta);
        paquete.put("Tiempo_mensaje", tiempo.toString());
        return paquete;
    }

    private String serializar(Map<String, Object> mensaje) {
        Gson gson = new Gson();
        return gson.toJson(mensaje);
    }

    public void apagar() {
        socket.close();
        context.close();
    }
}
