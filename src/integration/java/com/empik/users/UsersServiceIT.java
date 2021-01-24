package com.empik.users;

import com.empik.BaseIT;
import com.empik.github.users.GitHubUserDTO;
import com.empik.requests.UsersRequestEntity;
import com.empik.requests.UsersRequestRepository;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
class UsersServiceIT extends BaseIT {

    private static final String LOGIN = "test";

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRequestRepository usersRequestRepository;

    @Before
    public void setUp() {
        usersRequestRepository.deleteAll();
    }

    @After
    public void tearDown() {
        usersRequestRepository.deleteAll();
    }

    @Test
    void shouldCreateNewRequestEntity() {
        // given
        val gitHubUserDTO = new GitHubUserDTO();
        gitHubUserDTO.setLogin(LOGIN);

        // when
        usersService.saveUsersRequestEntity(gitHubUserDTO);

        // then
        val usersRequestOptional = usersRequestRepository.findById(LOGIN);
        assertThat(usersRequestOptional).isPresent();
        val usersRequest = usersRequestOptional.get();
        assertThat(usersRequest.getLogin()).isEqualTo(LOGIN);
        assertThat(usersRequest.getRequestCount()).isEqualTo(1);
    }

    @Test
    void shouldUpdateRequestEntity() {
        // given
        val gitHubUserDTO = new GitHubUserDTO();
        gitHubUserDTO.setLogin(LOGIN);
        val requestEntity = new UsersRequestEntity();
        requestEntity.setLogin(LOGIN);
        requestEntity.setRequestCount(3);
        usersRequestRepository.saveAndFlush(requestEntity);

        // when
        usersService.saveUsersRequestEntity(gitHubUserDTO);

        // then
        val result = usersRequestRepository.findById(LOGIN);
        assertThat(result).isPresent();
        val updatedUsersRequest = result.get();
        assertThat(updatedUsersRequest.getLogin()).isEqualTo(LOGIN);
        assertThat(updatedUsersRequest.getRequestCount()).isEqualTo(4);
    }
}