package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(value="Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @Id
    @MongoId
    private String role_Id ;
    @NotNull(message="the role name must not be null")
    @NotEmpty(message="the role name must not be empty")
    private String role_Name;

}

