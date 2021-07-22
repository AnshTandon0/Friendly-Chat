package com.example.friendlychat.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonInfo {

    private String name = "";
    private String email = "";
    private List<FriendsInfo> friends = new ArrayList<>();
    private String imageUrl = "";

    public PersonInfo ()
    {

    }

    public PersonInfo(String name, String email, List<FriendsInfo> friends, String imageUrl) {
        this.name = name;
        this.email = email;
        this.friends = friends;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<FriendsInfo> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsInfo> friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
