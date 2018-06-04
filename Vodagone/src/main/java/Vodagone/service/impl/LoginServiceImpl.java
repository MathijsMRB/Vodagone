package Vodagone.service.impl;

import Vodagone.dataaccesslayer.dao.LoginDao;
import Vodagone.dataaccesslayer.models.LoginResponse;
import Vodagone.service.LoginService;
import Vodagone.service.TokenGenerator;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

public class LoginServiceImpl implements LoginService {

    @Inject
    private LoginDao loginDao;

    @Override
    public LoginResponse loginUser(String user, String password) {
        if (user == null || password == null || "".equals(user) || "".equals(password))
            throw new BadRequestException("The given body of data is empty");

        if(loginDao.checkValidLogin(user, password)){
            String token = TokenGenerator.generateToken();
            loginDao.addToken(token, user);
            return loginDao.getGebruiker(user);
        }
        else{
            throw new BadRequestException("Invalid login");
        }
    }
}
