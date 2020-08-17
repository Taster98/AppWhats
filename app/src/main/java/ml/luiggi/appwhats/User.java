package ml.luiggi.appwhats;

public class User {
    private String uid,name,phoneNum;
    public User(String u,String n, String pN){
        name=n;
        phoneNum=pN;
        uid=u;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName(){
        return name;
    }
    public String getPhoneNum(){
        return phoneNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
