package Chat.Client;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ClientGuiModel {



    // Здесь будем хранить список всех участников чата
    private final Set<String> allUserNames = new TreeSet<>();

    private String newMessage;

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public Set<String> getAllUserNames() {
        return Collections.unmodifiableSet(allUserNames);
    }

    // Добавляет имя участника в хранилище
    public void addUser(String newUserName) {
        allUserNames.add(newUserName);
    }

    //Удаляет имя участника из хранилища
    public void deleteUser(String userName) {
        allUserNames.remove(userName);
    }

}
