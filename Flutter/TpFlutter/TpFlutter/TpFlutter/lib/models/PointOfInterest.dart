import 'dart:html';

import 'Review.dart';
import 'Location.dart';

class PointOfInterest {
  final int id;
  final String title;
  final String description;
  final List<String?> image;
  final Review? review;
  final Location location;
  final double latitude;
  final double longitude;

  PointOfInterest({
    required this.id,
    required this.title,
    required this.description,
    required this.image,
    required this.review,
    required this.location,
    required this.latitude,
    required this.longitude,
  });
}