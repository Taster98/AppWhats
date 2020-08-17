package ml.luiggi.appwhats;

public class Message {
    String messageId,senderId,text;

    public Message(String mId, String sId, String txt){
        messageId=mId;
        senderId=sId;
        text=txt;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }
}
