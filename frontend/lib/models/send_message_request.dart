class SendMessageRequest {
  final String senderId;
  final String receiverId;
  final String content;
  final String type;

  SendMessageRequest({
    required this.senderId,
    required this.receiverId,
    required this.content,
    required this.type,
  });

  Map<String, dynamic> toJson() => {
    'senderId': senderId,
    'receiverId': receiverId,
    'content': content,
    'type': type,
  };

  factory SendMessageRequest.fromJson(Map<String, dynamic> json) {
    return SendMessageRequest(
      senderId: json['senderId'],
      receiverId: json['receiverId'],
      content: json['content'],
      type: json['type'],
    );
  }
}
