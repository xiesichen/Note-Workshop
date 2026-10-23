package com.noteworkshop.service;

import com.noteworkshop.pojo.User;

public interface UserService {

    User login(String username, String password);
}