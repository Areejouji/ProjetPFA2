package com.example.ProjectV1.Service;

import com.example.ProjectV1.Config.JWTService;
import com.example.ProjectV1.Repositry.TokenRepositry;
import com.example.ProjectV1.Token.Token;
import com.example.ProjectV1.Token.TokenType;
import com.example.ProjectV1.authentication.AuthenticationResponse;
import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.DoctorRepositry;
import com.example.ProjectV1.Repositry.RoleRepositry;
import com.example.ProjectV1.Repositry.UserRepositry;
import com.example.ProjectV1.Validator.ObjectValidator;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Role;
import com.example.ProjectV1.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepositry UserRepo;
    @Autowired
    private RoleRepositry RoleRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private DoctorRepositry DoctorRepo;
    @Autowired
    private DoctorService Doctorservice;
    @Autowired
    private ObjectValidator<User> userObjectValidator;
    @Autowired
    private  JWTService jwtService;
    @Autowired
    private TokenRepositry tokenRepositry;
    @Override
    public AuthenticationResponse saveUserAdmin(User user) throws ObjectCollectionException {
        userObjectValidator.validate(user);
        Optional<User> UserOptional = Optional.ofNullable(UserRepo.findByUsername(user.getUsername()));
        if (UserOptional.isPresent()) {
            throw new ObjectCollectionException(ObjectCollectionException.UserAlreadyExists());}
        else{
            log.info("Saving new user {} to the database",user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role1=RoleRepo.findByRolename("USER");
            Role role2=RoleRepo.findByRolename("ADMIN");
            if(role2!=null){
                Collections.addAll(user.getRoles(),role1.getRole_Name(),role2.getRole_Name());
                 User saveduser=UserRepo.save(user);
                var jwtToken= jwtService.generateToken(user);
                var token=new Token();
                token.setUser_Token(saveduser.getUser_Id());
                token.setToken(jwtToken);
                token.setTokenType(TokenType.BEARER);
                token.setExpired(false);
                token.setRevoked(false);
                tokenRepositry.save(token);
                saveduser.getTokens().add(token);
                UserRepo.save(saveduser);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();

            }
            else {
                throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(role2.getRole_Id()));
            }
        }

    }
    @Override
    public AuthenticationResponse saveUserDoctor(User user,Doctor doctor) throws ObjectCollectionException {
        Optional<User> UserOptional = Optional.ofNullable(UserRepo.findByUsername(user.getUsername()));
        if (UserOptional.isPresent()) {
            throw new ObjectCollectionException(ObjectCollectionException.UserAlreadyExists());}
        else{
            log.info("Saving new user {} to the database",user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role1=RoleRepo.findByRolename("USER");
            Role role2=RoleRepo.findByRolename("DOCTOR");
            Collections.addAll(user.getRoles(),role1.getRole_Name(),role2.getRole_Name());
            User savedUser= UserRepo.save(user);
            doctor.setUser_Id_Doctor(savedUser.getUser_Id());
            Doctorservice.saveDoctor(doctor);
            var jwtToken= jwtService.generateToken(user);
            var token=new Token();
            token.setUser_Token(savedUser.getUser_Id());
            token.setToken(jwtToken);
            token.setTokenType(TokenType.BEARER);
            token.setExpired(false);
            token.setRevoked(false);
            tokenRepositry.save(token);
            savedUser.getTokens().add(token);
            UserRepo.save(savedUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();


        }

    }



    @Override
    public Role saveRole(Role role) throws ObjectCollectionException {
        Optional<Role> RoleOptional = Optional.ofNullable(RoleRepo.findByRolename(role.getRole_Name()));
        if (RoleOptional.isPresent()) {
            throw new ObjectCollectionException(ObjectCollectionException.RoleAlreadyExists());}
        else{
            log.info("Saving new role {} to the database",role.getRole_Name());
            return RoleRepo.save(role);}
    }

    @Override
    public List<User> GetUserByRole(String Rolename) {
        Role admin= RoleRepo.findByRolename(Rolename);
        List<User> users=UserRepo.findAll();
        ArrayList<User> admins=new ArrayList<>();
        if(users!=null){
            for(User user:users)
            {  if(user.getRoles().contains(admin.getRole_Name()))
            {
                admins.add(user);
            }
            } }
        else{
            return new ArrayList<>();
        }
        return admins;
    }



    @Override
    public User addRoleToUser(String username,String rolename) throws ObjectCollectionException {
        log.info("Adding role {} to user {}",rolename,username);
        User user = UserRepo.findByUsername(username);
        Role role= RoleRepo.findByRolename(rolename);
        if(!(role==null)){
            if (!(user.getRoles().contains(role)) )
            {  user.getRoles().add(role.getRole_Name());
                return UserRepo.save(user) ;}
            else {
                throw new ObjectCollectionException(ObjectCollectionException.RoleAlreadyExists());}
        }
        else{
            throw  new ObjectCollectionException(ObjectCollectionException.NotFoundException(rolename));
        }


    }

    @Override
    public User getUser(String username) throws ObjectCollectionException {
        log.info("Fetching user {}",username);
        User user=UserRepo.findByUsername(username);
        if(user!=null){
            return user;
        }
        else
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(username));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users=UserRepo.findAll();
        if(users!=null)
            return users;
        else return new ArrayList<>();
    }

    @Override
    public void updateUserDoctor(String id,User user,Doctor doctor) throws ObjectCollectionException {
        Optional<User> userWithId = UserRepo.findById(id);
        Optional<User> userWithSameName = Optional.ofNullable(UserRepo.findByUsername(user.getUsername()));
        if(userWithId.isPresent())
        {
            if (userWithSameName.isPresent() && !userWithSameName.get().getUser_Id().equals(id)) {
                throw new ObjectCollectionException(ObjectCollectionException.UserAlreadyExists());
            }
            String doctorId=DoctorRepo.findDoctorByuserId(userWithId.get().getUser_Id()).getDoctor_Id();
            Doctorservice.updateDoctor(doctorId,doctor);
            User userUpdate = userWithId.get();
            userUpdate.setUsername(user.getUsername());
            userUpdate.setPassword(user.getPassword());
            userUpdate.setRoles(user.getRoles());
            UserRepo.save(userUpdate);

        } else
        {
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void updateUser(String id, User user) throws ObjectCollectionException {
        Optional<User> userOptional=UserRepo.findById(id);
        if(!userOptional.isPresent())
        {
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        } else {
            User Userupdate=userOptional.get();
            Userupdate.setUsername(user.getUsername());
            Userupdate.setPassword(user.getPassword());
            Userupdate.setRoles(user.getRoles());
            UserRepo.save(Userupdate);
        }
    }

    @Override
    public void deleteUserDoctorById(String id) throws ObjectCollectionException {

        Optional<User> userOptional=UserRepo.findById(id);
        if(!userOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        } else {
            Doctor doctor=DoctorRepo.findDoctorByuserId(id);
            if(doctor!=null)
                Doctorservice.deleteDoctor(doctor.getDoctor_Id());
            UserRepo.deleteById(id);

        }
    }

    @Override
    public void deleteUserDoctorByUsername(String username) throws ObjectCollectionException {
        Optional<User> userOptional= Optional.ofNullable(UserRepo.findByUsername(username));
        if(!userOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(username));
        } else {
            Doctor doctor=DoctorRepo.findDoctorByuserId(userOptional.get().getUser_Id());
            if(doctor!=null)
            {Doctorservice.deleteDoctor(doctor.getDoctor_Id());}
            UserRepo.deleteByUsername(username);

        }



    }

    @Override
    public void deleteUserById(String id) throws ObjectCollectionException {
        Optional<User> userOptional= UserRepo.findById(id);
        if(!userOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        } else {
            UserRepo.deleteById(id);

        }}

    @Override
    public void deleteUserByUsername(String username) throws ObjectCollectionException {
        Optional<User> userOptional= Optional.ofNullable(UserRepo.findByUsername(username));
        if(!userOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(username));
        } else {
            UserRepo.findByUsername(username);}
    }


}
