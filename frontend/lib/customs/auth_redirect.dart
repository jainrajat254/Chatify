import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class AuthRedirect extends StatelessWidget {
  final String text;
  final String clickableText;
  final VoidCallback onPressed;

  const AuthRedirect({
    Key? key,
    required this.text,
    required this.clickableText,
    required this.onPressed,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return RichText(
      text: TextSpan(
        style: DefaultTextStyle.of(context).style,
        children: <TextSpan>[
          TextSpan(text: text, style: TextStyle(color: Colors.black)),
          TextSpan(
            text: clickableText,
            style: TextStyle(color: Colors.blue),
            recognizer:
                TapGestureRecognizer()
                  ..onTap = () {
                    onPressed();
                  },
          ),
        ],
      ),
    );
  }
}
