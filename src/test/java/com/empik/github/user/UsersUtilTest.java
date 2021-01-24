package com.empik.github.user;

import com.empik.github.users.GitHubUserDTO;
import com.empik.users.UserDTO;
import com.empik.users.UsersUtil;
import java.util.Date;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsersUtilTest {

    @Test
    void shouldCreateUserDto() {
        // given
        final GitHubUserDTO gitHubUserDTO = getGitHubUser(2, 3);

        // when
        val result = UsersUtil.createUserDTO(gitHubUserDTO);

        // then
        assertUserDTO(gitHubUserDTO, result, 15);
    }

    @Test
    void shouldCreateUserDtoWhenGitHubUserFollowersIsZero() {
        // given
        final GitHubUserDTO gitHubUserDTO = getGitHubUser(0, 3);

        // when
        val result = UsersUtil.createUserDTO(gitHubUserDTO);

        // then
        assertUserDTO(gitHubUserDTO, result, 0);
    }

    private GitHubUserDTO getGitHubUser(final int followers, final int publicRepos) {
        val gitHubUser = new GitHubUserDTO();
        gitHubUser.setId(0);
        gitHubUser.setLogin("test");
        gitHubUser.setName("name");
        gitHubUser.setType("type");
        gitHubUser.setAvatarUrl("https://test-url.com/avatar.jpg");
        gitHubUser.setCreatedAt(new Date());
        gitHubUser.setFollowers(followers);
        gitHubUser.setPublicRepos(publicRepos);
        return gitHubUser;
    }

    private void assertUserDTO(final GitHubUserDTO gitHubUserDTO, final UserDTO result, final int calculations) {
        assertThat(result.getId()).isEqualTo(gitHubUserDTO.getId());
        assertThat(result.getLogin()).isEqualTo(gitHubUserDTO.getLogin());
        assertThat(result.getName()).isEqualTo(gitHubUserDTO.getName());
        assertThat(result.getType()).isEqualTo(gitHubUserDTO.getType());
        assertThat(result.getAvatarUrl()).isEqualTo(gitHubUserDTO.getAvatarUrl());
        assertThat(result.getCreatedAt()).isEqualTo(gitHubUserDTO.getCreatedAt());
        assertThat(result.getCalculations()).isEqualTo(calculations);
    }
}