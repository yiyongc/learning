import 'dart:convert';
import 'dart:async';

import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../models/http_exception.dart';

class Auth with ChangeNotifier {
  final _API_KEY = 'AIzaSyAHN41PTXJGJdt2EDlh9_wExBDPAz6jiRo';
  final _PREFS_USERDATA = 'shop_app:userData';
  String _token;
  DateTime _expiryDate;
  String _userId;
  Timer _authTimer;

  bool get isAuth {
    return token != null;
  }

  String get token {
    if (_expiryDate != null &&
        _expiryDate.isAfter(DateTime.now()) &&
        _token != null) {
      return _token;
    }
    return null;
  }

  String get userId {
    return _userId;
  }

  Future<void> _accountAuthenticate(
      String email, String password, String urlSegment) async {
    Uri url = Uri.parse(
        'https://identitytoolkit.googleapis.com/v1/accounts:$urlSegment?key=$_API_KEY');
    try {
      final response = await http.post(
        url,
        body: json.encode(
          {
            'email': email,
            'password': password,
            'returnSecureToken': true,
          },
        ),
      );
      final responseData = json.decode(response.body);
      if (responseData['error'] != null) {
        throw HttpException(responseData['error']['message']);
      }
      _token = responseData['idToken'];
      _userId = responseData['localId'];
      _expiryDate = DateTime.now().add(
        Duration(
          seconds: int.parse(responseData['expiresIn']),
        ),
      );
      _autoLogout();
      notifyListeners();
      final prefs = await SharedPreferences.getInstance();
      final userData = json.encode({
        'token': _token,
        'userId': _userId,
        'expiryDate': _expiryDate.toIso8601String(),
      });
      print('Setting prefs, $userData');
      prefs.setString(_PREFS_USERDATA, userData);
    } catch (error) {
      print(error);
      throw error;
    }
  }

  Future<void> signup(String email, String password) async {
    return _accountAuthenticate(email, password, 'signUp');
  }

  Future<void> login(String email, String password) async {
    return _accountAuthenticate(email, password, 'signInWithPassword');
  }

  Future<bool> tryAutoLogin() async {
    print('Trying to autologin');
    final prefs = await SharedPreferences.getInstance();
    if (!prefs.containsKey(_PREFS_USERDATA)) {
      print('No previous auth data found');
      return false;
    } else {
      final userData =
          json.decode(prefs.getString(_PREFS_USERDATA)) as Map<String, Object>;
      print('Previous data found, logging in $userData');
      final expiryDate = DateTime.parse(userData['expiryDate']);
      if (expiryDate.isBefore(DateTime.now())) {
        return false;
      }
      _token = userData['token'];
      _userId = userData['userId'];
      _expiryDate = expiryDate;
      _autoLogout();
      notifyListeners();
      return true;
    }
  }

  Future<void> logout() async {
    _token = null;
    _userId = null;
    _expiryDate = null;
    if (_authTimer != null) {
      _authTimer.cancel();
      _authTimer = null;
    }
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_PREFS_USERDATA);
    notifyListeners();
  }

  void _autoLogout() {
    if (_authTimer != null) {
      _authTimer.cancel();
    }
    final secondsToExpiry = _expiryDate.difference(DateTime.now()).inSeconds;
    _authTimer = Timer(Duration(seconds: secondsToExpiry), logout);
  }
}
