package com.example.ProjectV1.Token;

import com.example.ProjectV1.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @MongoId
    private String token_Id;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private String user_Token;

}
