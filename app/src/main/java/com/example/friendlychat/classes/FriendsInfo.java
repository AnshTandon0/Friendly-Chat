package com.example.friendlychat.classes;

public class FriendsInfo {

    private String name = "";
    private String photoUrl = "";
    private String lastChat = "";
    private String chatTime = "";
    private int noUnread = 0;

    public FriendsInfo()
    {

    }

    public FriendsInfo(String name, String photoUrl, String lastChat, String chatTime, int noUnread) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.lastChat = lastChat;
        this.chatTime = chatTime;
        this.noUnread = noUnread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLastChat() {
        return lastChat;
    }

    public void setLastChat(String lastChat) {
        this.lastChat = lastChat;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getNoUnread() {
        return noUnread;
    }

    public void setNoUnread(int noUnread) {
        this.noUnread = noUnread;
    }
}
