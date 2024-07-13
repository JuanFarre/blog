package pelusadev.ProyectoBlog.service;

import pelusadev.ProyectoBlog.model.UserSec;

import java.util.List;

public interface IUserService {

    public List<UserSec> findAll();

    public UserSec getById(Long id);

    public void deleteById(Long id);

    public UserSec createUserSec(UserSec userSec);

    public UserSec updateUserSec(UserSec userSec, Long id);

}

