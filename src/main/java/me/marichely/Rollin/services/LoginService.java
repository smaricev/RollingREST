package me.marichely.Rollin.services;

import me.marichely.Rollin.entity.User;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void userCheck(String userApiKey){
        if(StringUtils.isEmpty(userApiKey))throw  new RestException(HttpStatus.BAD_REQUEST,"You need to provide an api key");
        if(!StringUtils.isNumeric(userApiKey))throw new RestException(HttpStatus.BAD_REQUEST,"api key needs to be a number");
        User user = userRepository.findByApiKey(userApiKey);
        if(user == null) throw  new RestException(HttpStatus.NOT_FOUND,"Couldn't find user with provided api key");
    }
}
