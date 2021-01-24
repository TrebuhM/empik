package com.empik.requests;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "git_hub_request")
@Getter
@Setter
@EqualsAndHashCode
public class UsersRequestEntity {

    @Id
    private String login;
    private int requestCount;
}
