package com.empik.users;

import com.empik.github.users.GitHubUserDTO;
import com.empik.requests.UsersRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRequestRepository usersRequestRepository;

    @Transactional
    public void saveUsersRequestEntity(final GitHubUserDTO gitHubUserDTO) {
        usersRequestRepository.insertOrUpdateRequestCount(gitHubUserDTO.getLogin());
    }
}
