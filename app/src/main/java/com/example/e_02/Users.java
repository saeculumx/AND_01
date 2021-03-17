package com.example.e_02;

import java.util.ArrayList;
import java.util.List;

public class Users {
    public List<User> users = new ArrayList<>();
    //private final UserData userData = new UserData();
    public Users(){}
    public void addUser(User user)
    {
        users.add(user);
    }
    public void removeUser(String userName)
    {
        for (int i =0;i<users.size();i++)
        {
            if (users.get(i).getUsername().equals(userName))
            {
                users.remove(i);
                System.out.println("Remove Complete");
                break;
            }
            System.out.println("No user found in place: "+i);
        }
    }
    public int getLength()
    {
        return users.size();
    }
    public boolean checkUser(String userName, String password)
    {
        for (int i =0;i<users.size();i++)
        {
            if (users.get(i).getUsername().equals(userName)&&users.get(i).getPassword().equals(password))
            {
                return true;
            }
            System.out.println("No match found in place: "+i);
        }
        return false;
    }
    public List<User> getUsersList()
    {
        return users;
    }
    public void loadData()
    {
        User user1 = new User("a", "1");
        User user2 = new User("b", "2");
        User user3 = new User("c", "3");
        User user4 = new User("d", "4");
        User user5 = new User("e", "5");
        User user6 = new User("f", "6");
        User user7 = new User("g", "7");
        User user8 = new User("h", "8");
        User user9 = new User("i", "9");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
    }
    public void addUserData(User user)
    {
        for (int i =0;i<users.size();i++) {
            if (!user.getUsername().equals(users.get(i).getUsername())) {
                users.add(user);
            }
        }
    }
    public User getUser(String username)
    {
        for (int i =0;i<users.size();i++)
        {
            if (username.equals(users.get(i)))
            {
                return users.get(i);
            }
        }
        return null;
    }

}
