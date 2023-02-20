import 'package:flutter/material.dart';

class Answer extends StatelessWidget {
  final String _answerText;
  final Function _onPressedHandler;

  Answer(this._answerText, this._onPressedHandler);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      child: RaisedButton(
        color: Colors.blue[100],
        child: Text(_answerText),
        onPressed: _onPressedHandler,
      ),
    );
  }
}
