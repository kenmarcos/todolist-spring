package br.com.kenmarcos.todolistspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.kenmarcos.todolistspring.errors.AppException;
import br.com.kenmarcos.todolistspring.models.UserModel;
import br.com.kenmarcos.todolistspring.repositories.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public UserModel create(UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            throw new AppException("Usuário já existe", HttpStatus.BAD_REQUEST);
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(userModel);
        System.out.println(userCreated);
        return userCreated;
    }

}
