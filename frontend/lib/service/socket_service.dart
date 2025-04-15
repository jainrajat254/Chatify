import 'dart:convert';

import 'package:stomp_dart_client/stomp_dart_client.dart';

class SocketService {
  late StompClient stompClient;

  void connect(String userId, Function(dynamic) onMessageReceived) {
    stompClient = StompClient(
      config: StompConfig(
        url: 'wss://c770-2409-40d2-1029-bda4-cdd9-ea91-2aff-28f3.ngrok-free.app/ws',
        // NO SockJS here
        onConnect: (StompFrame frame) {
          print('✅ Connected');

          // Subscribe to user-specific queue
          stompClient.subscribe(
            destination: '/user/$userId/queue/messages',
            callback: (frame) {
              if (frame.body != null) {
                print('📩 Message received: ${frame.body}');
                onMessageReceived(jsonDecode(frame.body!));
              }
            },
          );
        },
        onWebSocketError: (dynamic error) {
          print('❌ WebSocket error: $error');
        },
        onStompError: (StompFrame frame) {
          print('💥 STOMP error: ${frame.body}');
        },
        onDisconnect: (StompFrame frame) {
          print('🔌 Disconnected');
        },
        heartbeatOutgoing: const Duration(seconds: 10),
        heartbeatIncoming: const Duration(seconds: 10),
      ),
    );

    stompClient.activate();
  }

  void sendMessage(Map<String, dynamic> message) {
    stompClient.send(
      destination: '/app/chat.sendMessage',
      body: jsonEncode(message),
    );
  }

  void disconnect() {
    stompClient.deactivate();
  }
}
