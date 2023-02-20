import 'package:flutter/material.dart';

class TextControlButton extends StatelessWidget {
  final Function _triggerNextText;

  TextControlButton(this._triggerNextText);

  @override
  Widget build(BuildContext context) {
    return RaisedButton(
      child: Text('Click me to change the text!'),
      onPressed: _triggerNextText,
    );
  }
}
