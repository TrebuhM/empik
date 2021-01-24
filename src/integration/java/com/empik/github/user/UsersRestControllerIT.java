package com.empik.github.user;

import com.empik.BaseIT;
import com.empik.github.client.GitHubClient;
import com.empik.github.client.GitHubClientException;
import com.empik.github.users.GitHubUserDTO;
import com.empik.requests.UsersRequestRepository;
import com.empik.users.UserDTO;
import com.empik.users.UsersComponent;
import com.empik.users.UsersRestController;
import com.empik.users.UsersService;
import java.util.Date;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UsersRestControllerIT extends BaseIT {

    private static final String LOGIN = "login";
    private UsersRestController usersRestController;

    @Autowired
    private UsersRequestRepository usersRequestRepository;

    @MockBean
    private GitHubClient gitHubClient;

    @BeforeEach
    void setUp() {
        val userService = new UsersService(usersRequestRepository);
        val userComponent = new UsersComponent(userService, gitHubClient);
        usersRestController = new UsersRestController(userComponent);
        usersRequestRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        usersRequestRepository.deleteAll();
    }

    @Test
    @Transactional
    void shouldReturnUserDTO() {
        // given
        val gitHubUser = createGitHubUser();
        Mockito.when(gitHubClient.getUserByLogin(LOGIN)).thenReturn(gitHubUser);

        // when
        val result = usersRestController.getUserByLogin(LOGIN);

        // then
        val requestsEntityOptional = usersRequestRepository.findById(LOGIN);
        assertThat(requestsEntityOptional).isPresent();
        val requestEntity = requestsEntityOptional.get();
        assertThat(requestEntity.getLogin()).isEqualTo(LOGIN);
        assertThat(requestEntity.getRequestCount()).isEqualTo(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertUserDTO(gitHubUser, result);
    }

    @Test
    void shouldHandleErrorWhenRequestingGitHubApi() {
        // given
        Mockito.when(gitHubClient.getUserByLogin(LOGIN))
            .thenThrow(new GitHubClientException("User not found.", HttpStatus.NOT_FOUND));

        // when
        val result = assertThrows(ResponseStatusException.class, () -> usersRestController.getUserByLogin(LOGIN));

        // then
        val requestsEntity = usersRequestRepository.findById(LOGIN);
        assertThat(requestsEntity).isEmpty();
        assertThat(result.getMessage()).isEqualTo("404 NOT_FOUND \"User not found.\"");
        assertThat(result.getRawStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldHandleErrorWhenGitHubResponseIsNull() {
        // given
        Mockito.when(gitHubClient.getUserByLogin(LOGIN)).thenReturn(null);

        // when
        val result = assertThrows(ResponseStatusException.class, () -> usersRestController.getUserByLogin(LOGIN));

        // then
        val requestsEntity = usersRequestRepository.findById(LOGIN);
        assertThat(requestsEntity).isEmpty();
        assertThat(result.getMessage()).isEqualTo("404 NOT_FOUND \"Cannot get user from the GitHub API.\"");
        assertThat(result.getRawStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private GitHubUserDTO createGitHubUser() {
        val gitHubUser = new GitHubUserDTO();
        gitHubUser.setId(0);
        gitHubUser.setLogin(LOGIN);
        gitHubUser.setName("name");
        gitHubUser.setType("type");
        gitHubUser.setAvatarUrl("https://test-url.com/avatar.jpg");
        gitHubUser.setCreatedAt(new Date());
        gitHubUser.setFollowers(2);
        gitHubUser.setPublicRepos(3);
        return gitHubUser;
    }

    private void assertUserDTO(final GitHubUserDTO gitHubUserDTO, final ResponseEntity<UserDTO> result) {
        val userDTO = result.getBody();
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(gitHubUserDTO.getId());
        assertThat(userDTO.getLogin()).isEqualTo(gitHubUserDTO.getLogin());
        assertThat(userDTO.getName()).isEqualTo(gitHubUserDTO.getName());
        assertThat(userDTO.getType()).isEqualTo(gitHubUserDTO.getType());
        assertThat(userDTO.getAvatarUrl()).isEqualTo(gitHubUserDTO.getAvatarUrl());
        assertThat(userDTO.getCreatedAt()).isEqualTo(gitHubUserDTO.getCreatedAt());
        assertThat(userDTO.getCalculations()).isEqualTo(15);
    }
}