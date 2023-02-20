import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import '../models/http_exception.dart';

class Product with ChangeNotifier {
  // This is the firebase real time database test database
  static const _API_URL =
      'flutter-shop-app-6c819-default-rtdb.asia-southeast1.firebasedatabase.app';

  final String id;
  final String title;
  final String description;
  final double price;
  final String imageUrl;
  bool isFavourite;

  Product({
    @required this.id,
    @required this.title,
    @required this.description,
    @required this.price,
    @required this.imageUrl,
    this.isFavourite = false,
  });

  Future<void> toggleFavouriteStatus(String token, String userId) async {
    final oldStatus = isFavourite;
    _setFavValue(!this.isFavourite);

    final url = Uri.https(
        _API_URL, '/userFavourites/$userId/$id.json', {'auth': token});
    try {
      final response = await http.put(
        url,
        body: json.encode(
          isFavourite,
        ),
      );
      if (response.statusCode >= 400) {
        // Handle exception as status code errors are not considered errors
        throw HttpException(
            'Failed to patch object favourite status, statusCode: ${response.statusCode}');
      }
    } catch (error) {
      debugPrint(error.toString());
      _setFavValue(oldStatus);
    }
  }

  void _setFavValue(bool newValue) {
    this.isFavourite = newValue;
    notifyListeners();
  }
}
