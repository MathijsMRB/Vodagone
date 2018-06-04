package Vodagone.service;

import Vodagone.dataaccesslayer.models.LoginResponse;

public interface LoginService {

    LoginResponse loginUser(String user, String password);
}
