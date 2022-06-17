package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


// класс отвечает за серверную обработку данных
public class Server {

    //место хранения соединений (ключ имя клиента, значение - соединение с ним
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Чат сервер запущен.");
            while (true) {
                // Ожидаем входящее соединение и запускаем отдельный поток при его принятии
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера.");
        }
    }

    //класс для обработки событий
    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //логика метода такова : создает новое соединение, и рассылает всем клиентам сообщение о новых подключениях,
            // и закрывает подключения если ошибки или клиент вышел.  данные о подключениях вносит в главную мапу
            ConsoleHelper.writeMessage("Установлено новое соединение с " + socket.getRemoteSocketAddress());

            String userName =null;

            try {
                Connection connection = new Connection(socket); // создаем подключение
                userName = serverHandshake(connection);  // подключаем нового клиента
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName)); // отправляем инфу о новом клиентке
                notifyUsers(connection, userName); // проверяем на повтоорение в чате имени
                serverMainLoop(connection, userName); // обрабатываем внутренние пересылаемые сообщения


            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с " + socket.getRemoteSocketAddress());
            }

            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }

            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто.");
        }
        // метод для подключения нового клиента
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST)); // отправляем запрос с новым именем клиента

                Message message = connection.receive(); //  получаем ответ и классифицируем сообщение
                if (message.getType() != MessageType.USER_NAME) {    // если сервер распознал отправленное нами сообщение не как имя нового клиента ...
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу.");
                    continue;
                }

                String userName = message.getData();

                if (userName.isEmpty()) {   // если имя клиента оправили пустое
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с пустым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }

                if (connectionMap.containsKey(userName)) {  // если отправили имя, которое уже имеется в чате (в мапе)
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с уже используемым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
                connectionMap.put(userName, connection); // если прошли все проверки, то добавляем в главную мапу и отправляем сообщение клиенту что он добавился

                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return userName;
            }
        }
        //в этом методе отправляем клиенту инфу о других клиентах чата
        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String name : connectionMap.keySet()) {
                if (name.equals(userName)) // пробегаемся по мапе с клиентами и если имя повторяется, то перепрыгиваем, иначе сообщаем о новом клиенте
                    continue;
                connection.send(new Message(MessageType.USER_ADDED, name));
            }
        }
        //в этом методе будем обрабатывать сообщения клиента для чата и рассылку другим участникам
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) { // пока есть соединение
                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT) {   // проверяем на соответствие типу текста сообщения
                    String data = message.getData();
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + data));
                } else {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу.");
                }
            }
        }
    }
    // Рассылаем сообщение по всем соединениям
    public static void sendBroadcastMessage(Message message) {

        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не смогли отправить сообщение " + connection.getRemoteSocketAddress());
            }
        }
    }



}