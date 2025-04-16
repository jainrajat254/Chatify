package com.example.chatify.message;

import com.example.chatify.constants.MessageConstants;
import com.example.chatify.enums.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {

    @Query(name = MessageConstants.FIND_MESSAGE_BY_CHAT_ID)
    List<Messages> findMessagesByChatId(@Param("chatId") String chatId);

    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
