package com.example.chatify.repository;

import com.example.chatify.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Messages, UUID> {

    List<Messages> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    List<Messages> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(UUID senderId, UUID receiverId, UUID receiverId1, UUID senderId1);
}
