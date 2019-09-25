package ru.kstovoservice;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Main {

    public static final int PORT = 13539; // прописан проброс порта на 82.208.70.88 на чувик


    public static Socket createSocket() {

        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            //тут, на сервере, создаем специальный серверный сокет, который умеет слушать порт и активизируется (возвращает сокет) только когда есть запрос на входящее подключение
            serverSocket = new ServerSocket(PORT); //https://www.youtube.com/watch?v=SAsJuKuKTjE
            System.out.println("Started, waiting for connection");
            socket = serverSocket.accept(); // возвращает socket , если появилось внешнее соединение. Здесь метод accept разблокируется и возвращает обычный socket
        } catch (Exception e) {
            System.out.println("Что то не задалось с созданием сокета на сервере...");
        }
        return socket;
    }

    public static void main(String[] args) {

        try {
            Socket socket;
            // сюды мы попадем только когда появится клиент... а до этого висим в этой точке (но там, на заднем плане может уже крутиться с десяток процессов)
            socket = createSocket();
            if (socket != null) {
                System.out.println("Accepted. " + socket.getInetAddress());
                try {
                    OutputStream out = socket.getOutputStream();
                    InputStream in = socket.getInputStream();
                    byte[] buf = new byte[32 * 1024];
                    int readbytes = in.read(buf); // блокировка. ожидание ответа от клиента
                    System.out.println("Получено " + readbytes + " байт.");
                    String line = new String(buf, 0, readbytes);
                    System.out.printf("Client> %s", line);
                    System.out.println("Выше напечатано то, что пришло на сервер от клиента");
                    out.write(line.getBytes());
                    out.flush();

                } catch (SocketException e) {
                    System.out.println(e);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}