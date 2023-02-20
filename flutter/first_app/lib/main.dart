import 'package:flutter/material.dart';

import './quiz.dart';
import './result.dart';

// void main() {
//   runApp(MyApp());
// }

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp> {
  var _questionIndex = 0;
  var _totalScore = 0;

  final _questions = const [
    {
      'questionText': 'What\'s your favourite food?',
      'answers': [
        {'text': 'Tofu', 'score': 50},
        {'text': 'Fried Rice', 'score': 30},
        {'text': 'Burgers', 'score': 10},
        {'text': 'Noodz', 'score': 20},
      ],
    },
    {
      'questionText': 'What\'s your favourite animal?',
      'answers': [
        {'text': 'Dog', 'score': 50},
        {'text': 'Cat', 'score': 30},
        {'text': 'Fish', 'score': 20},
        {'text': 'Rabbit', 'score': 10},
        {'text': 'Bird', 'score': 5},
      ]
    },
    {
      'questionText': 'Who is your favourite kpop artist?',
      'answers': [
        {'text': 'Twice', 'score': 10},
        {'text': 'Blackpink', 'score': 50},
        {'text': 'Itzy', 'score': 20},
        {'text': 'Ailee', 'score': 30},
      ]
    },
  ];

  void _answerQuestion(int score) {
    _totalScore += score;
    setState(() {
      _questionIndex = _questionIndex + 1;
    });
    print(_questionIndex);
    print('Question is answered!');
  }

  void _resetQuiz() {
    setState(() {
      _questionIndex = 0;
      _totalScore = 0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: Text('My First App'),
          ),
          body: _questionIndex < _questions.length
              ? Quiz(_questions, _questionIndex, _answerQuestion)
              : Result(_totalScore, _resetQuiz)),
    );
  }
}
