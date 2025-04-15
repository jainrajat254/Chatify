import 'package:shared_preferences/shared_preferences.dart';

import '../models/login_response.dart';

class UserSessionManager {
  static Future<void> saveUser(LoginResponse user) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('userId', user.id);
    print(user.id);
    await prefs.setString('username', user.username);
    await prefs.setString('name', user.fullName);
    await prefs.setString('bio', user.bio);
  }

  static Future<Map<String, String?>> getUser() async {
    final prefs = await SharedPreferences.getInstance();
    return {
      'userId': prefs.getString('userId'),
      'username': prefs.getString('username'),
      'name': prefs.getString('name'),
      'bio': prefs.getString('bio'),
    };
  }

  static Future<void> clearUser() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
  }
}