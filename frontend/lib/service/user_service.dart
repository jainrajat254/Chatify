import 'dart:convert';

import 'package:chatify/models/login_request.dart';
import 'package:chatify/models/login_response.dart';
import 'package:chatify/models/register_request.dart';
import 'package:http/http.dart' as http;

import '../models/message.dart';

class UserService {
  final String baseUrl =
      'https://c770-2409-40d2-1029-bda4-cdd9-ea91-2aff-28f3.ngrok-free.app';

  Future<LoginResponse?> registerUser(RegisterRequest request) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/register'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(request.toJson()),
    );
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return LoginResponse.fromJson(json);
    } else {
      print('Register failed: ${response.body}');
      return null;
    }
  }

  Future<LoginResponse?> loginUser(LoginRequest request) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(request.toJson()),
    );
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return LoginResponse.fromJson(json);
    } else {
      print('Login failed: ${response.body}');
      return null;
    }
  }

  Future<bool> deleteUser(String id) async {
    final response = await http.delete(
      Uri.parse('$baseUrl/auth/delete/$id'),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode == 200) {
      return true;
    } else {
      print("Failed to delete user: ${response.body}");
      return false;
    }
  }

  Future<List<Message>> getChatHistory(
    String senderId,
    String receiverId,
  ) async {
    final response = await http.get(
      Uri.parse('$baseUrl/chat/history/$senderId/$receiverId'),
    );
    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((json) => Message.fromJson(json)).toList();
    } else {
      print("Failed to fetch history: ${response.body}");
      return [];
    }
  }

    // Future<String?> uploadMedia(File file) async {
    //   final request = http.MultipartRequest(
    //     'POST',
    //     Uri.parse('$baseUrl/chat/upload'),
    //   );
    //
    //   request.files.add(
    //     await http.MultipartFile.fromPath(
    //       'file',
    //       file.path,
    //       contentType: MediaType('image', 'jpeg'), // or 'video/mp4'
    //     ),
    //   );
    //
    //   final streamedResponse = await request.send();
    //   final response = await http.Response.fromStream(streamedResponse);
    //
    //   if (response.statusCode == 200) {
    //     final body = jsonDecode(response.body);
    //     return body['url'];
    //   } else {
    //     print('Upload failed: ${response.body}');
    //     return null;
    //   }
    // }
}
