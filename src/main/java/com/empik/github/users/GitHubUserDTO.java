package com.empik.github.users;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubUserDTO {

    private String login;
    private int id;
    private String nodeId;
    private String avatarUrl;
    private String gravatarId;
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsRrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private boolean siteAdmin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private boolean hireable;
    private String bio;
    private String twitterUsername;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private Date createdAt;
    private Date updatedAt;
}
