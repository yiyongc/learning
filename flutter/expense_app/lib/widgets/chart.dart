import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../models/transaction.dart';
import './chart_bar.dart';

class Chart extends StatelessWidget {
  final List<Transaction> _recentTransactions;

  Chart(this._recentTransactions);

  List<Map<String, Object>> get groupedTransactionValues {
    List<Map<String, Object>> groupedTransactions = List.generate(7, (index) {
      final weekDay = DateTime.now().subtract(Duration(days: index));
      double totalSum = 0;
      for (int i = 0; i < _recentTransactions.length; i++) {
        final transaction = _recentTransactions[i];
        if (transaction.date.day == weekDay.day &&
            transaction.date.month == weekDay.month &&
            transaction.date.year == weekDay.year) {
          totalSum += transaction.amount;
        }
      }

      return {
        'day': DateFormat.E().format(weekDay),
        'amount': totalSum,
      };
    });

    const sortOrder = {
      'Sun': 0,
      'Mon': 1,
      'Tue': 2,
      'Wed': 3,
      'Thu': 4,
      'Fri': 5,
      'Sat': 6
    };

    groupedTransactions.sort((tx1, tx2) => sortOrder[(tx1['day'] as String)]
        .compareTo(sortOrder[(tx2['day'] as String)]));

    return groupedTransactions;
  }

  double get maxSpending {
    return groupedTransactionValues.fold(0.0, (sum, tx) => sum += tx['amount']);
  }

  @override
  Widget build(BuildContext context) {
    print(groupedTransactionValues);
    return Card(
      elevation: 6,
      margin: EdgeInsets.all(20),
      child: Padding(
        padding: EdgeInsets.all(10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: groupedTransactionValues.map((tx) {
            return Flexible(
              fit: FlexFit.tight,
              child: ChartBar(
                  tx['day'],
                  tx['amount'],
                  maxSpending == 0.0
                      ? 0.0
                      : (tx['amount'] as double) / maxSpending),
            );
          }).toList(),
        ),
      ),
    );
  }
}
