import 'package:flutter/material.dart';

import '../models/chat_preview.dart';
import '../service/socket_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  List<ChatPreview> chats = [];
  final socketService = SocketService();

  @override
  void initState() {
    super.initState();
    socketService.connect("af9287d4-1c2f-42a5-bbd0-303b56c501fe", handleIncomingMessage);
  }

  void handleIncomingMessage(dynamic message) {
    final senderId = message['senderId'];
    final content = message['content'];
    final timestamp = message['timestamp'];

    final existing = chats.indexWhere((chat) => chat.userId == senderId);
    if (existing != -1) {
      setState(() {
        chats[existing] = ChatPreview(
          userId: senderId,
          username: "User $senderId",
          lastMessage: content,
          timestamp: timestamp,
        );
      });
    } else {
      setState(() {
        chats.insert(
          0,
          ChatPreview(
            userId: senderId,
            username: "User $senderId",
            lastMessage: content,
            timestamp: timestamp,
          ),
        );
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(centerTitle: true, title: const Text('Chats')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: chats.isEmpty
            ? const Center(child: Text("No chats yet"))
            : ListView.builder(
          itemCount: chats.length,
          itemBuilder: (context, index) {
            final chat = chats[index];
            return ListTile(
              leading: const CircleAvatar(child: Icon(Icons.person)),
              title: Text(chat.username),
              subtitle: Text(chat.lastMessage),
              onTap: () {
                // Navigate to chat screen
              },
            );
          },
        ),
      ),
    );
  }
}
