import 'package:chatify/customs/auth_redirect.dart';
import 'package:chatify/customs/custom_button.dart';
import 'package:chatify/models/login_request.dart';
import 'package:chatify/service/user_service.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../customs/custom_text_field.dart';
import '../models/login_response.dart';
import '../preferences/user_session_manager.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreen();
}

class _LoginScreen extends State<LoginScreen> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscureText = true;
  bool _isLoading = false;

  final authService = UserService();

  void _login() async {
    String username = _usernameController.text;
    String password = _passwordController.text;

    LoginRequest request = LoginRequest(username: username, password: password);

    setState(() {
      _isLoading = true;
    });

    LoginResponse? response = await authService.loginUser(request);

    setState(() {
      _isLoading = false;
    });

    if (response != null) {
      await UserSessionManager.saveUser(response);
      context.go('/home');
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Welcome, ${response.fullName}!')));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Login failed. Please try again.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Center(child: const Text('Login'))),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Welcome back',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 24),
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
                  label: "Login",
                  onPressed: () {
                    _login();
                  },
                ),

            const SizedBox(height: 32),
            AuthRedirect(
              text: "Don't have an account? ",
              clickableText: "Register here",
              onPressed: () {
                context.push('/register');
              },
            ),
          ],
        ),
      ),
    );
  }
}
