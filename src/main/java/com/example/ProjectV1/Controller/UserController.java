package com.example.ProjectV1.Controller;


import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Service.DoctorService;
import com.example.ProjectV1.Service.UserService;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Role;
import com.example.ProjectV1.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/user/")
    public ResponseEntity<Collection<User>> getUsers()
    {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
    @PostMapping("/user/save/doctor")
    public ResponseEntity<?> SaveDoctor(@RequestBody DoctorUserSve Form) throws ObjectCollectionException {
        try {
              return new ResponseEntity<>(userService.saveUserDoctor(Form.user1,Form.doctor1), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/user/save/admin")
    public ResponseEntity<?> SaveAdmin(@RequestBody User user) throws ObjectCollectionException {
        try {

            return new ResponseEntity<>(userService.saveUserAdmin(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) throws ObjectCollectionException {
        try {
            Role role1 = userService.saveRole(role);
            return new ResponseEntity<>(role1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> AddRoleToUser(@RequestBody RoleToUserForm Form) throws ObjectCollectionException {
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/addtouser").toUriString());
        //log.info(Form.getRole_Name());
        return ResponseEntity.created(uri).body(userService.addRoleToUser(Form.username,Form.role_Name));
    }
    @GetMapping("/user/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/doctor")
    public ResponseEntity<?> getAllDoctors()
    {    try {
        ArrayList<DoctorUserForm> doctorUser = new ArrayList<>();
        Collection<User> doctors = userService.GetUserByRole("DOCTOR");
        Collection<Doctor> doctors1 = null;
        if (!doctors.isEmpty()) {
            doctors1 = doctorService.getAllDoctors();
        }
        for (Doctor doctor : doctors1) {
            for (User user : doctors) {
                if ( doctor.getUser_Id_Doctor().equals(user.getUser_Id()))
                { UserFront userdoc = new UserFront(user.getUser_Id(),user.getUsername(),user.getPassword(),user.getRoles());
                  DoctorFront doc=new DoctorFront(doctor.getDoctor_Id(),doctor.getDoctor_firstname(),doctor.getDoctor_lastname(),doctor.getConcerned_records());
                  DoctorUserForm docuser=new DoctorUserForm(userdoc,doc);
                    doctorUser.add(docuser);

                }
            }

        }
        return new ResponseEntity<>(doctorUser,HttpStatus.CREATED);

    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    }
    @GetMapping("/user/admin")
    public ResponseEntity<?> GetAllAdmins()
    {
        try{
            Collection<User> admins=userService.GetUserByRole("ADMIN");
            return new ResponseEntity<>(admins,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    /*@GetMapping("/user/doctor")
    public ResponseEntity<?> GetDoctors()
    {
        try{
            Collection<User> admins=userService.GetUserByRole("DOCTOR");
            return new ResponseEntity<>(admins,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }*/

    @PutMapping("/user/doctor/{id}")
    public ResponseEntity<?> updateDoctorById(@PathVariable("id") String id, @RequestBody DoctorUserSve Form) {
        try {
            userService.updateUserDoctor(id,Form.getUser1(),Form.getDoctor1());
            return new ResponseEntity<>("Update user with id" + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/user/admin/{id}")
    public ResponseEntity<?> updateAdminById(@PathVariable("id") String id, @RequestBody User user) {
        try {
            userService.updateUser(id,user);
            return new ResponseEntity<>("Update user with id" + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        if (id.isBlank() || id.isEmpty()) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/admin/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable("username") String username) {
        if (username.isBlank() || username.isEmpty()) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        try {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/doctor/{username}")
    public ResponseEntity<?> deleteDoctorByUsername(@PathVariable("username") String username) {
        if (username.isBlank() || username.isEmpty()) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        try {
            userService.deleteUserDoctorByUsername(username);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleToUserForm{
        private String username;

        private String role_Name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorUserForm{
        private UserFront userFront;
        private DoctorFront doctorFront;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserFront{
        private String id1;
        private String username1;
        private String password1;
        private ArrayList<String> roles1;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorFront{
        private String id2;
        private String firstname2;
        private String lastname2;
        private ArrayList<String> concernedrec;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorUserSve{
        private User user1;
        private Doctor doctor1;



}}
