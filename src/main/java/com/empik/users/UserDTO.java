package com.empik.users;

import java.util.Date;
import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Date createdAt;
    private double calculations;
}
