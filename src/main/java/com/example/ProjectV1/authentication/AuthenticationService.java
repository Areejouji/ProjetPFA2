package com.example.ProjectV1.authentication;

import com.example.ProjectV1.authentication.AuthenticationRequest;
import com.example.ProjectV1.authentication.AuthenticationResponse;
import com.example.ProjectV1.Exception.ObjectCollectionException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request) throws ObjectCollectionException;

}
