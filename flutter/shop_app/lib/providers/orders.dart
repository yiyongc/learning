import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

import '../models/order_item.dart';
import '../models/cart_item.dart';
import './auth.dart';

class Orders with ChangeNotifier {
  // This is the firebase real time database test database
  static const _API_URL =
      'flutter-shop-app-6c819-default-rtdb.asia-southeast1.firebasedatabase.app';

  String _authToken;

  String _userId;

  List<OrderItem> _orders = [];

  Orders(this._authToken, this._userId, this._orders);

  List<OrderItem> get orders {
    return [..._orders];
  }

  void update(Auth auth) {
    this._authToken = auth.token;
    this._userId = auth.userId;
  }

  Future<void> fetchAndSetOrders() async {
    final url =
        Uri.https(_API_URL, '/orders/$_userId.json', {'auth': _authToken});
    final response = await http.get(url);
    final List<OrderItem> loadedOrders = [];
    final extractedData = json.decode(response.body) as Map<String, dynamic>;
    if (extractedData == null) {
      return;
    }
    extractedData.forEach((orderId, orderData) {
      loadedOrders.add(
        OrderItem(
          id: orderId,
          amount: orderData['amount'],
          dateTime: DateTime.parse(orderData['dateTime']),
          products: (orderData['products'] as List<dynamic>)
              .map(
                (item) => CartItem(
                  id: item['id'],
                  title: item['title'],
                  price: item['price'],
                  quantity: item['quantity'],
                ),
              )
              .toList(),
        ),
      );
    });
    _orders = loadedOrders.reversed.toList();
    notifyListeners();
  }

  Future<void> addOrder(List<CartItem> cartProducts, double total) async {
    try {
      final url =
          Uri.https(_API_URL, '/orders/$_userId.json', {'auth': _authToken});
      final timestamp = DateTime.now();
      final response = await http.post(url,
          body: json.encode({
            'amount': total,
            'dateTime': timestamp.toIso8601String(),
            'products': cartProducts
                .map((cp) => {
                      'id': cp.id,
                      'title': cp.title,
                      'price': cp.price,
                      'quantity': cp.quantity,
                    })
                .toList(),
          }));
      Map<String, dynamic> responseMap = json.decode(response.body);
      debugPrint("Order Saved :: Response from firebase :: $responseMap");
      _orders.insert(
        0,
        OrderItem(
          id: responseMap['name'],
          amount: total,
          dateTime: timestamp,
          products: cartProducts,
        ),
      );
      notifyListeners();
    } catch (error) {
      print('Error occurred during addOrder :: $error');
      throw error;
    }
  }
}
