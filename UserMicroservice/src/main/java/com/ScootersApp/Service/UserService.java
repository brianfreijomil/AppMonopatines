package com.ScootersApp.Service;


import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.Service.DTOs.userAccount.request.UserAccountRequestDTO;
import com.ScootersApp.Service.exception.ConflictExistException;
import com.ScootersApp.Service.exception.NotFoundException;
import com.ScootersApp.domain.Account;
import com.ScootersApp.domain.User;
import com.ScootersApp.domain.UserAccount;
import com.ScootersApp.domain.UserAccountID;
import com.ScootersApp.repository.AccountRepository;
import com.ScootersApp.repository.UserAccountRepository;
import com.ScootersApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("UserService")
public class UserService {

    UserRepository repository;
    AccountRepository accountRepository;
    UserAccountRepository userAccountRepository;

    public UserService(UserAccountRepository userAccountRepository, UserRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.userAccountRepository = userAccountRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        List<User> users = this.repository.findAll();
        return users.stream().map(s1-> new UserResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseEntity save(UserRequest user) throws Exception {
        if(this.repository.findByMail(user.getMail())==null){
            User newUser= this.repository.save(new User(user.getName(), user.getSurname(),
                                    user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getRoles()));
            return new ResponseEntity(newUser.getID(), HttpStatus.CREATED);
        }
        throw new ConflictExistException("User", "mail", user.getMail());
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDTO findByMailAndPassword(String mail, String pass) {
        System.out.println(pass);
        System.out.println(mail);
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);
    }

    public void deleteUser(Long id) {
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }
        else
            throw new NotFoundException("User","ID",id);
    }

    public ResponseEntity updateUser(UserRequest userRequest, Long id) {
        if(this.repository.existsById(id)){
            User user = this.repository.findByID(id);
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setMail(userRequest.getMail());
            user.setPassword(userRequest.getPassword());
            user.setRoles(userRequest.getRoles());

            return new ResponseEntity(user.getID(), HttpStatus.ACCEPTED);
        }
        else
            throw new NotFoundException("User","ID",id);
     }
  
    public UserLoginResponseDTO findByMail(String mail) {
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);

    }

    public ResponseEntity saveNewUserAccount(UserAccountRequestDTO uar, Long idUser) {
        if(this.repository.existsById(idUser)){
            User u = this.repository.findByID(idUser);
            if(this.accountRepository.existsById(uar.getAccountId())){
                Account a = this.accountRepository.findById(uar.getAccountId()).get();
                UserAccountID userAccountID = new UserAccountID(u, a);
                UserAccount userAccount = new UserAccount(userAccountID);

                this.userAccountRepository.save(userAccount);
                return new ResponseEntity(userAccount.getId(), HttpStatus.CREATED);
            }
            else
                throw new NotFoundException("Account", "ID", uar.getAccountId());
        }
        else
            throw new ConflictExistException("User", "ID", idUser);
    }
}
