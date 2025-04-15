class LoginResponse {
  final String id;
  final String fullName;
  final String bio;
  final String username;
  final String? status;

  LoginResponse({
    required this.id,
    required this.fullName,
    required this.bio,
    required this.username,
    this.status,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      id: json['id'],
      fullName: json['fullName'],
      bio: json['bio'],
      username: json['username'],
      status: json['status'],
    );
  }
}
