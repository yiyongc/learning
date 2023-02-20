import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../models/http_exception.dart';
import './product.dart';
import './auth.dart';

class Products with ChangeNotifier {
  // This is the firebase real time database test database
  static const _API_URL =
      'flutter-shop-app-6c819-default-rtdb.asia-southeast1.firebasedatabase.app';
  static const _PRODUCTS_COLLECTION = '/products.json';
  List<Product> _items = [];

  List<Product> get items {
    return [..._items];
  }

  String _authToken;

  String _userId;

  Products(this._authToken, this._userId, this._items);

  void update(Auth auth) {
    this._authToken = auth.token;
    this._userId = auth.userId;
  }

  Future<void> fetchAndSetProducts([bool filterByUser = false]) async {
    final productsQueryParam = filterByUser
        ? {
            'orderBy': '"creatorId"',
            'equalTo': '"$_userId"',
            'auth': _authToken,
          }
        : {
            'auth': _authToken,
          };
    final productApiUrl = Uri(
      scheme: 'https',
      host: _API_URL,
      path: _PRODUCTS_COLLECTION,
      queryParameters: productsQueryParam,
    );
    final favouriteApiUrl = Uri.https(
        _API_URL, '/userFavourites/$_userId.json', {'auth': _authToken});
    try {
      final productResponse = await http.get(productApiUrl);
      final productData =
          json.decode(productResponse.body) as Map<String, dynamic>;
      final List<Product> loadedProducts = [];
      if (productData == null) {
        return;
      }
      final userFavouriteResponse = await http.get(favouriteApiUrl);
      final userFavouriteData = json.decode(userFavouriteResponse.body);
      productData.forEach((productId, productData) {
        loadedProducts.add(new Product(
          id: productId,
          title: productData['title'],
          description: productData['description'],
          price: productData['price'],
          imageUrl: productData['imageUrl'],
          isFavourite: userFavouriteData == null
              ? false
              : userFavouriteData[productId] ?? false,
        ));
      });
      _items = loadedProducts;
      notifyListeners();
    } catch (error) {
      print('Error occurred when fetching products :: $error');
      throw error;
    }
  }

  Future<void> addProduct(Product item) async {
    final url = Uri.https(_API_URL, _PRODUCTS_COLLECTION, {'auth': _authToken});
    try {
      final response = await http.post(
        url,
        body: json.encode({
          'title': item.title,
          'description': item.description,
          'imageUrl': item.imageUrl,
          'price': item.price,
          'creatorId': _userId,
        }),
      );

      Map<String, dynamic> responseMap = json.decode(response.body);
      debugPrint("Product Saved :: Response from firebase :: $responseMap");
      final newProduct = Product(
        title: item.title,
        description: item.description,
        price: item.price,
        imageUrl: item.imageUrl,
        id: responseMap['name'],
      );
      _items.add(newProduct);
      notifyListeners();
    } catch (error) {
      print('Error occurred during addProduct :: $error');
      throw error;
    }
  }

  Future<void> updateProduct(Product editedProduct) async {
    final productIndex =
        _items.indexWhere((element) => element.id == editedProduct.id);
    if (productIndex != -1) {
      final url = Uri.https(
          _API_URL, '/products/${editedProduct.id}.json', {'auth': _authToken});
      await http.patch(url,
          body: json.encode({
            'title': editedProduct.title,
            'description': editedProduct.description,
            'imageUrl': editedProduct.imageUrl,
            'price': editedProduct.price,
          }));
      _items[productIndex] = editedProduct;
      notifyListeners();
    }
  }

  Product findById(String id) {
    return _items.firstWhere((product) => product.id == id);
  }

  Future<void> deleteProduct(String id) async {
    final url = Uri.https(_API_URL, '/products/$id.json', {'auth': _authToken});
    final existingProductIdx = _items.indexWhere((item) => item.id == id);
    var existingProduct = _items[existingProductIdx];
    _items.removeAt(existingProductIdx);
    notifyListeners();

    final response = await http.delete(url);
    // Optimistic updating, reinsert only if deletion fails
    if (response.statusCode >= 400) {
      _items.insert(existingProductIdx, existingProduct);
      notifyListeners();
      // Handle exception as http.delete does not throw errors
      throw HttpException(
          'Failed to delete object, statusCode: ${response.statusCode}');
    }
    existingProduct = null; // clear reference
  }

  List<Product> get favouriteItems {
    return _items.where((item) => item.isFavourite).toList();
  }

  static List<Product> _getDummyProducts() {
    return [
      Product(
        id: 'p1',
        title: 'Red Shirt',
        description: 'A red shirt - it is pretty red!',
        price: 29.99,
        imageUrl:
            'https://cdn.pixabay.com/photo/2016/10/02/22/17/red-t-shirt-1710578_1280.jpg',
      ),
      Product(
        id: 'p2',
        title: 'Trousers',
        description: 'A nice pair of trousers.',
        price: 59.99,
        imageUrl:
            'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Trousers%2C_dress_%28AM_1960.022-8%29.jpg/512px-Trousers%2C_dress_%28AM_1960.022-8%29.jpg',
      ),
      Product(
        id: 'p3',
        title: 'Yellow Scarf',
        description: 'Warm and cozy - exactly what you need for the winter.',
        price: 19.99,
        imageUrl:
            'https://live.staticflickr.com/4043/4438260868_cc79b3369d_z.jpg',
      ),
      Product(
        id: 'p4',
        title: 'A Pan',
        description: 'Prepare any meal you want.',
        price: 49.99,
        imageUrl:
            'https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Cast-Iron-Pan.jpg/1024px-Cast-Iron-Pan.jpg',
      ),
    ];
  }
}
