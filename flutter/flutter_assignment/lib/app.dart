import 'package:flutter/material.dart';

import './text_body.dart';
import './text_control_button.dart';

class App extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return AppState();
  }
}

class AppState extends State<App> {
  var _textIndex = 0;

  var _texts = [
    'Hello World',
    'What is up',
    'Practice more flutter please',
  ];

  void changeText() {
    setState(() {
      _textIndex++;
      if (_textIndex >= _texts.length) {
        _textIndex = 0;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Assignment App Bar'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              TextBody(_texts[_textIndex]),
              TextControlButton(changeText),
            ],
          ),
        ),
      ),
    );
  }
}
