package ru.kstovoservice;

import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static final int PORT = 13539; // прописан проброс порта на 82.208.70.88 на комп
    public static final String HOST_FOR_CLIENT = "82.208.70.88";//


    public static Socket createSocket() {

        try {
            // У клиента создаем сокет к серверу, указывая адрес сервера лицензий и порт сервера лицензий
            Socket client_socket = new Socket(HOST_FOR_CLIENT, PORT);
            return client_socket;
            // на сервере создаем специальный метод сокета, который умеет слушать порт и активизируется (возвращает сокет) только когда есть запрос на входящее подключение
            ServerSocket server_socket = new ServerSocket(PORT);
            Socket socket = server_socket.accept();
            return socket;
        } catch (Exception e) {

        }
        Socket socket = new Socket();
        return socket;
    }

    public static void main(String[] args) {

        Socket socket = createSocket(); // возвращает созданный сокет


    }
}
