import 'dart:html';

import 'package:tpflutter/models/Review.dart';

class Location {
  final int id;
  final String title;
  final String description;
  final List<String?> image;
  final Review? review;
  final double latitude;
  final double longitude;

  Location({
    required this.id,
    required this.title,
    required this.description,
    required this.image,
    required this.review,
    required this.latitude,
    required this.longitude,
  });
}