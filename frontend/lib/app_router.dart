import 'package:chatify/screens/home_screen.dart';
import 'package:chatify/screens/login_screen.dart';
import 'package:chatify/screens/register_screen.dart';
import 'package:go_router/go_router.dart';

final GoRouter appRouter = GoRouter(
    initialLocation: '/login',
    routes: [
      GoRoute(path: '/login',
          builder: (context, state) => const LoginScreen()
      ),

      GoRoute(path: '/register',
          builder: (context, state) => const RegisterScreen()
      ),

      GoRoute(path: '/home',
          builder: (context, state) => const HomeScreen()
      ),
    ]
);
