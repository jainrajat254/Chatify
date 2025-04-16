package com.example.chatify.user;

import com.example.chatify.constants.UserConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    @Query(name = UserConstants.FIND_USER_BY_USERNAME)
    Optional<Users> findByUsername(@Param("username") String username);

    @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
    List<Users> findAllUsersExceptSelf(@Param("publicId") String publicId);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<Users> findByPublicId(@Param("publicId") String senderId);
}
