package com.example.ProjectV1.Service;

import com.example.ProjectV1.authentication.AuthenticationResponse;
import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Role;
import com.example.ProjectV1.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    public AuthenticationResponse saveUserDoctor(User user, Doctor doctor)throws ObjectCollectionException;
    public  AuthenticationResponse saveUserAdmin(User user ) throws ObjectCollectionException;
    public  User addRoleToUser(String username, String rolename) throws ObjectCollectionException;
    public User getUser(String username) throws ObjectCollectionException;
    public List<User> getAllUsers();
    public void updateUserDoctor(String id , User user, Doctor doctor) throws ObjectCollectionException;
    public void updateUser(String id , User user) throws ObjectCollectionException;
    public void deleteUserDoctorById(String id) throws ObjectCollectionException;
    public void deleteUserDoctorByUsername(String username) throws ObjectCollectionException;
    public void deleteUserById(String id) throws ObjectCollectionException;
    public void deleteUserByUsername(String username) throws ObjectCollectionException;
    public Role saveRole(Role role) throws ObjectCollectionException;

    public List<User> GetUserByRole(String rolename);
}



