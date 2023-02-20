import 'package:flutter/material.dart';

class Result extends StatelessWidget {
  final int _resultScore;
  final Function _resetQuiz;

  Result(this._resultScore, this._resetQuiz);

  String get resultPhrase {
    String resultPhrase;
    if (_resultScore > 100) {
      resultPhrase = 'You are awesome yo';
    } else if (_resultScore > 50) {
      resultPhrase = 'Not too shabby!';
    } else {
      resultPhrase = 'Err.. try again?';
    }
    return resultPhrase;
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: <Widget>[
          Text(
            'You did it!',
            style: TextStyle(fontSize: 36, fontWeight: FontWeight.bold),
            textAlign: TextAlign.center,
          ),
          Text(resultPhrase),
          FlatButton(
            child: Text('Restart Quiz?'),
            onPressed: _resetQuiz,
          ),
        ],
      ),
    );
  }
}
