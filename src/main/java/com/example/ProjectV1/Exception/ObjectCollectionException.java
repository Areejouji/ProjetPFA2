package com.example.ProjectV1.Exception;

public class ObjectCollectionException extends Exception {

    private static  final long serialVersionUID=1L;
    public ObjectCollectionException(String message)
    {
        super(message);
    }
    public static String NotFoundException(String id)
    {
        return "Object with "+ id +" not found";
    }
    public static String UserAlreadyExists(){
        return "User with given name already exists";
    }
    public static String  RoleAlreadyExists(){ return "Role with given name already exists";}
    }

