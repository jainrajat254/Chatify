package com.example.chatify.user;

import com.example.chatify.common.BaseAuditingEntity;
import com.example.chatify.constants.UserConstants;
import com.example.chatify.chat.Chat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = UserConstants.FIND_USER_BY_USERNAME,
        query = "SELECT u  FROM Users u where u.username = :username")
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF,
query = "SELECT u  FROM Users u where u.id != :publicId")
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID,
        query = "SELECT u  FROM Users u where u.id = :publicId")
public class Users extends BaseAuditingEntity {

    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatAsSender;

    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatAsRecipient;

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }

}
