import 'package:chatify/customs/custom_button.dart';
import 'package:chatify/customs/custom_text_field.dart';
import 'package:chatify/models/login_response.dart';
import 'package:chatify/models/register_request.dart';
import 'package:chatify/service/user_service.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../customs/auth_redirect.dart';
import '../preferences/user_session_manager.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreen();
}

class _RegisterScreen extends State<RegisterScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _bioController = TextEditingController();
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscureText = true;
  bool _isLoading = false;

  final authService = UserService();

  void _register() async {
    String name = _nameController.text;
    String bio = _bioController.text;
    String username = _usernameController.text;
    String password = _passwordController.text;

    RegisterRequest request = RegisterRequest(
      fullName: name,
      bio: bio,
      username: username,
      password: password,
    );

    setState(() {
      _isLoading = true;
    });

    LoginResponse? response = await authService.registerUser(request);

    setState(() {
      _isLoading = false; // Stop loading
    });

    if (response != null) {
      await UserSessionManager.saveUser(response);
      context.go('/home');
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Welcome, ${response.fullName}!')));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Registration failed. Please try again.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Register')),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Welcome to Chatify',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 24),
            CustomTextField(
              controller: _nameController,
              label: 'Full Name',
              icon: Icons.person,
            ),
            const SizedBox(height: 16),
            CustomTextField(
              controller: _bioController,
              label: 'Bio',
              icon: Icons.message,
            ),
            const SizedBox(height: 16),
            CustomTextField(
              controller: _usernameController,
              label: 'Username',
              icon: Icons.account_circle,
            ),
            const SizedBox(height: 16),
            CustomTextField(
              controller: _passwordController,
              label: 'Password',
              icon: Icons.lock,
              obscureText: _obscureText,
              keyboardType: TextInputType.visiblePassword,
              onSuffixIconPressed: () {
                setState(() {
                  _obscureText = !_obscureText; // Toggle password visibility
                });
              },
            ),
            const SizedBox(height: 24),
            _isLoading
                ? CircularProgressIndicator()
                : CustomButton(
                  label: 'Register',
                  onPressed: () {
                    _register();
                  },
                ),

            const SizedBox(height: 32),
            AuthRedirect(
              text: "Already have an account? ",
              clickableText: "Login here",
              onPressed: () {
                context.go('/login');
              },
            ),
          ],
        ),
      ),
    );
  }
}
