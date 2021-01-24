package com.empik.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRequestRepository extends JpaRepository<UsersRequestEntity, String> {

    @Modifying
    @Query(value = "INSERT INTO git_hub_request (login, request_count) VALUES (:login, 1) ON CONFLICT (login) "
        + "DO UPDATE SET request_count = git_hub_request.request_count + 1",
           nativeQuery = true)
    void insertOrUpdateRequestCount(@Param("login") final String login);
}
