import 'package:flutter/material.dart';
import '../widgets/meal_item.dart';

import '../models/meal.dart';

class FavouritesScreen extends StatelessWidget {
  final List<Meal> _favouriteMeals;

  FavouritesScreen(this._favouriteMeals);

  @override
  Widget build(BuildContext context) {
    if (_favouriteMeals.isEmpty) {
      return Center(
        child: Text('You have no favourites yet - start adding some!'),
      );
    } else {
      return ListView.builder(
        itemBuilder: (context, index) {
          final favouriteMeal = _favouriteMeals[index];
          return MealItem(
            id: favouriteMeal.id,
            title: favouriteMeal.title,
            duration: favouriteMeal.duration,
            imageUrl: favouriteMeal.imageUrl,
            affordability: favouriteMeal.affordability,
            complexity: favouriteMeal.complexity,
          );
        },
        itemCount: _favouriteMeals.length,
      );
    }
  }
}
