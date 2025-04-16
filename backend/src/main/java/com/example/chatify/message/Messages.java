package com.example.chatify.message;

import com.example.chatify.chat.Chat;
import com.example.chatify.common.BaseAuditingEntity;
import com.example.chatify.constants.MessageConstants;
import com.example.chatify.enums.MessageState;
import com.example.chatify.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
@NamedQuery(name = MessageConstants.FIND_MESSAGE_BY_CHAT_ID,
        query = "SELECT m  FROM Messages m where m.chat.id != :chatId ORDER BY m.createdDate")
@NamedQuery(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT,
        query = "UPDATE Messages m SET m.state = :newState where m.chat.id = :chatId")
public class Messages extends BaseAuditingEntity {

    @Id
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageState state;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    private String mediaFilePath;
}
