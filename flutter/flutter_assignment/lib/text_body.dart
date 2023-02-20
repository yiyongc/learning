import 'package:flutter/material.dart';

class TextBody extends StatelessWidget {
  final String _text;

  TextBody(this._text);

  @override
  Widget build(BuildContext context) {
    return Text(_text);
  }
}
