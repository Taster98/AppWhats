package ml.luiggi.appwhats;

public class Chat {
    private String chatId;

    public Chat(String ci){
        chatId=ci;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }
}
