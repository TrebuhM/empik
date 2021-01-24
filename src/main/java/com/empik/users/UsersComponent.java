package com.empik.users;

import com.empik.github.client.GitHubClient;
import com.empik.github.client.GitHubClientException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UsersComponent {

    private final UsersService usersService;
    private final GitHubClient gitHubClient;

    public UserDTO getUsers(final String login) {
        try {
            val gitHubUser = gitHubClient.getUserByLogin(login);
            if (gitHubUser == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot get user from the GitHub API.");
            }

            usersService.saveUsersRequestEntity(gitHubUser);
            return UsersUtil.createUserDTO(gitHubUser);
        } catch (final GitHubClientException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }
}
