package Chat.Client;

import Chat.Connection;
import Chat.ConsoleHelper;
import Chat.Message;
import Chat.MessageType;

import java.io.IOException;
import java.net.Socket;

// этот класс отвечает за клиентскую сторону

public class Client {

    protected Connection connection;
    private volatile boolean clientConnected = false; // будет устанавливаться в true, если клиент подсоединен к серверу



    public class SocketThread extends Thread {
        //будет отвечать за поток, устанавливающий сокетное соединение и читающий сообщения сервера

        //метод будет представлять сеpверу клиента
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) { // пока есть соединение
                Message message = connection.receive();  // получаем ответ от сервера и определяем тип ответа

                if (message.getType() == MessageType.NAME_REQUEST) { // Сервер запросил имя пользователя
                    // Запрашиваем ввод имени с консоли
                    String name = getUserName();
                    // Отправляем имя на сервер
                    connection.send(new Message(MessageType.USER_NAME, name));

                } else if (message.getType() == MessageType.NAME_ACCEPTED) { // Сервер принял имя пользователя
                    // Сообщаем главному потоку, что он может продолжить работу
                    notifyConnectionStatusChanged(true);
                    return;

                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        // Этот метод реализовывает главный цикл обработки сообщений сервера
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            // Цикл обработки сообщений сервера
            while (true) {
                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else if (MessageType.USER_ADDED == message.getType()) {
                    informAboutAddingNewUser(message.getData());
                } else if (MessageType.USER_REMOVED == message.getType()) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        //вывод сообщения в консоль
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        // вывод инфы о новом участнике
        protected void informAboutAddingNewUser (String userName) {
            ConsoleHelper.writeMessage(userName + " присоединился");
        }

        // вывод инфы что участник покинул чат
        protected void informAboutDeletingNewUser (String userName) {
            ConsoleHelper.writeMessage(userName + " покинул чат");
        }

        // метод устанавливает флаги клиенту и оповещает основной поток класса
        protected void notifyConnectionStatusChanged (boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        @Override
        public void run() {

            try {
                connection =  new Connection(new Socket(getServerAddress(), getServerPort()));

                clientHandshake();
                clientMainLoop();

            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }


        }
    }

    protected String getServerAddress() {
        //должен запросить ввод адреса сервера у пользователя и вернуть введенное значение.

        ConsoleHelper.writeMessage("Введите адрес сервера");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        //должен запрашивать ввод порта сервера и возвращать его
        ConsoleHelper.writeMessage("Введите порт сервера");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        // должен запрашивать и возвращать имя клиента
        ConsoleHelper.writeMessage("Введите ник");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {  // флаг ответа через консоль. У ботов будет false
        return true;
    }

    protected SocketThread getSocketThread() {
        // должен создавать и возвращать новый объетк класса SocketThread
        return new SocketThread();
    }

    protected void sendTextMessage(String text) {
        // создает новое текстовое сообщение и отправляет его серверу через connection

        try {
            connection.send(new Message(MessageType.TEXT, text));
        }catch (IOException e) {
            ConsoleHelper.writeMessage("Не удалось отправить сообщение");
            clientConnected = false;
        }
    }

    // метод должен создавать вспомогательный поток SocketThread, ожидать пока тот установит соединение с сервером,
    // а после этого в цикле считывать сообщения с консоли и отправлять их серверу.
    public void run() {
        SocketThread socketThread = getSocketThread();

        // помечаем поток как демон и запускаем его. Чтобы поток прекращался с остановкой программы
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента");
            return;
        }

        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        } else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента");
        }
        // проверяем введенные сообщения на exit
        while (clientConnected) {
            String text = ConsoleHelper.readString();
            if (text.equals("exit")) break;

            if (shouldSendTextFromConsole()) sendTextMessage(text);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}
