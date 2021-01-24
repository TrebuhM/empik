package com.empik.users;

import com.empik.github.users.GitHubUserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersUtil {

    public static UserDTO createUserDTO(final GitHubUserDTO gitHubUserDTO) {
        val client = new UserDTO();
        client.setId(gitHubUserDTO.getId());
        client.setLogin(gitHubUserDTO.getLogin());
        client.setName(gitHubUserDTO.getName());
        client.setType(gitHubUserDTO.getType());
        client.setAvatarUrl(gitHubUserDTO.getAvatarUrl());
        client.setCreatedAt(gitHubUserDTO.getCreatedAt());
        client.setCalculations(calculate(gitHubUserDTO));

        return client;
    }

    private static double calculate(final GitHubUserDTO gitHubUserDTO) {
        if (gitHubUserDTO.getFollowers() == 0) {
            return 0;
        }

        return (6d / gitHubUserDTO.getFollowers() * (2 + gitHubUserDTO.getPublicRepos()));
    }
}
