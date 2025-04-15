class RegisterRequest {
  final String fullName;
  final String bio;
  final String username;
  final String password;

  RegisterRequest({
    required this.fullName,
    required this.bio,
    required this.username,
    required this.password,
  });

  Map<String, dynamic> toJson() => {
    'fullName': fullName,
    'bio': bio,
    'username': username,
    'password': password,
  };
}
