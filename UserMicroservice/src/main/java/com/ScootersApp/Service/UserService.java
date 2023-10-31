package com.ScootersApp.Service;

import com.ScootersApp.Service.DTOs.Role.request.RoleRequest;
import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.Service.DTOs.userAccount.response.UserAccountResponseDTO;
import com.ScootersApp.Service.exception.ConflictExistException;
import com.ScootersApp.Service.exception.ConflictWithStatusException;
import com.ScootersApp.Service.exception.NotFoundException;
import com.ScootersApp.domain.*;
import com.ScootersApp.repository.AccountRepository;
import com.ScootersApp.repository.RoleRepository;
import com.ScootersApp.repository.UserAccountRepository;
import com.ScootersApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("UserService")
public class UserService {

    UserRepository repository;
    AccountRepository accountRepository;
    UserAccountRepository userAccountRepository;
    RoleRepository roleRepository;

    public UserService(UserAccountRepository userAccountRepository, UserRepository repository, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.repository = repository;
        this.userAccountRepository = userAccountRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO>findAll() {
        List<User> users = this.repository.findAll();
        return users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity save(UserRequest user){
        if(!this.repository.existsByMail(user.getMail())){
            User newUser= new User(user);
            List<Role> roles = new ArrayList<>();
            for(String s: user.getRoles()){
                Role r = this.roleRepository.findById(s).get();
                if(r != null){
                    roles.add(r);
                }
            }
            newUser.setRoles(roles);
            this.repository.save(newUser);
            System.out.println(newUser);
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
    @Transactional
    public void deleteUser(Long id) {
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }
        else
            throw new NotFoundException("User","ID",id);
    }
    @Transactional
    public ResponseEntity<Long> updateUser(UserRequest userRequest, Long id) {
        if(this.repository.existsById(id)){
            User user = this.repository.findById(id).get();
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setMail(userRequest.getMail());
            user.setPassword(userRequest.getPassword());
            List<Role> roles = new ArrayList<>();
            for(String s: userRequest.getRoles()){
                Role r = this.roleRepository.findById(s).get();
                if(r != null){
                    roles.add(r);
                }
            }
            user.setRoles(roles);
            return new ResponseEntity(user.getID(), HttpStatus.ACCEPTED);
        }
        else{
            throw new NotFoundException("User","ID_User(Long)",id);
        }
     }
    @Transactional(readOnly = true)
    public ResponseEntity<UserLoginResponseDTO> findByMail(String mail) {
        User u = this.repository.findByMail(mail);
        return new ResponseEntity(new UserLoginResponseDTO(u), HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<UserAccountResponseDTO> saveNewUserAccount(Long idUser, Long idAccount) {
        if(this.repository.existsById(idUser)){
            User u = this.repository.findById(idUser).get();
            if(this.accountRepository.existsById(idAccount)){
                Account a = this.accountRepository.findById(idAccount).get();
                UserAccountID userAccountID = new UserAccountID(u, a);
                if(!this.userAccountRepository.existsById(userAccountID)){
                    UserAccount userAccount = new UserAccount(userAccountID);
                    this.userAccountRepository.save(userAccount);
                    return new ResponseEntity(userAccount.getId(), HttpStatus.CREATED);
                }
                else {
                    throw new ConflictExistException("UserAccount", "ID", userAccountID);
                }
            }
            else{
                throw new NotFoundException("Account", "ID_Account(Long)", idAccount);
            }
        }
        else{
            throw new NotFoundException("User", "ID_User(Long)", idUser);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDTO> findByID(Long id){
        if(this.repository.existsById(id)){
            User u = this.repository.findById(id).get();
            System.out.println(u);
            return new ResponseEntity(new UserResponseDTO(u), HttpStatus.OK);
        }
        else{
            throw new NotFoundException("User", "ID_User(Long)", id);
        }
    }

    @Transactional
    public ResponseEntity<String> disableUser(String mail) {
        User u = this.repository.findByMail(mail);
        if(u.getAvailable()==1){
            u.setAvailable(0);
            return new ResponseEntity(u.getMail(), HttpStatus.OK);
        }
        else{
            throw new ConflictWithStatusException("User", "user.available", u.getAvailable());
        }
    }

    @Transactional
    public ResponseEntity<String> enableUser(String mail) {
        User u = this.repository.findByMail(mail);
        if(u.getAvailable()==0){
            u.setAvailable(1);
            return new ResponseEntity(u.getMail(), HttpStatus.OK);
        }
        else{
            throw new ConflictWithStatusException("User", "user.available", u.getAvailable());
        }
    }

    public List<UserAccountResponseDTO> getUserAccountByUserId(Long id) {
        if(this.repository.existsById(id)){
            List<UserAccount> accounts = this.userAccountRepository.findAllById_User(id);
            if(!accounts.isEmpty()){
                return accounts.stream().map(a1->new UserAccountResponseDTO(a1)).collect(Collectors.toList());
            }
            else {
                throw new NotFoundException("UserAccount", "User_id in userAccount_id(userAccountId)", id);
            }
        }
        else {
            throw new NotFoundException("User", "User_id(Long)", id);
        }
    }

    public List<UserAccountResponseDTO> getAllUserAccount() {
        List<UserAccount> userAccounts = this.userAccountRepository.findAll();
        return userAccounts.stream().map(ua1-> new UserAccountResponseDTO(ua1)).collect(Collectors.toList());
    }

    public void deleteUserAccount(Long id, Long idAccount) {
        if(this.repository.existsById(id)){
            User u = this.repository.findById(id).get();
            if(this.accountRepository.existsById(idAccount)){
                Account a = this.accountRepository.findById(idAccount).get();
                UserAccountID userAccountID = new UserAccountID(u, a);
                this.userAccountRepository.deleteById(userAccountID);
            }
            else {
                throw new NotFoundException("Account", "Account.id", idAccount);
            }
        }
        else {
            throw new NotFoundException("User", "User.id", id);
        }
    }
}
