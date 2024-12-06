import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'firebase_options.dart';

class Review {
  final int id;
  final int likes;
  final int dislikes;

  Review({
    required this.id,
    required this.likes,
    required this.dislikes,
  });
}

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

  List<PointOfInterest> getAssociatedPointsOfInterest(List<PointOfInterest> allPointsOfInterest) {
    return allPointsOfInterest
        .where((poi) => poi.locationId == this.id)
        .toList();
  }
}

class PointOfInterest {
  final int id;
  final String title;
  final String description;
  final List<String?> image;
  final Review? review;
  final double latitude;
  final double longitude;
  final int locationId;

  PointOfInterest({
    required this.id,
    required this.title,
    required this.description,
    required this.image,
    required this.review,
    required this.latitude,
    required this.longitude,
    required this.locationId,
  });
}

void initFirebase() async {
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
}

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  initFirebase();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LocationsScreen(),
    );
  }
}

class RecentlyViewedScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Recently Viewed Points of Interest"),
      ),
      body: FutureBuilder<List<PointOfInterest>>(
        future: SharedPreferencesUtils.loadRecentlyViewedPoints(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return CircularProgressIndicator();
          } else if (snapshot.hasError) {
            return Text("Error: ${snapshot.error}");
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Text("No recently viewed points of interest.");
          } else {
            return ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                final pointOfInterest = snapshot.data![index];
                return ListTile(
                  title: Text(pointOfInterest.title),
                  subtitle: Text(pointOfInterest.description),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => PointOfInterestView(pointOfInterest: pointOfInterest),
                      ),
                    );
                  },
                );
              },
            );
          }
        },
      ),
    );
  }
}

class LocationsScreen extends StatefulWidget {
  @override
  _LocationsScreenState createState() => _LocationsScreenState();
}

class _LocationsScreenState extends State<LocationsScreen> {
  final TextEditingController _searchController = TextEditingController();
  bool _sortByAlphabetical = false;

  Future<PointOfInterest?> _loadPointFromSharedPreferences(int pointId, int currentLocationId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    bool isLiked = prefs.getBool('${pointId}_isLiked') ?? false;
    bool isDisliked = prefs.getBool('${pointId}_isDisliked') ?? false;

    if (isLiked || isDisliked) {
      PointOfInterest poi = PointOfInterest(
        id: pointId,
        locationId: prefs.getInt('${pointId}_location_id') ?? 0, // Adicione essa linha para obter o ID do local associado ao ponto de interesse
        title: prefs.getString('${pointId}_title') ?? '',
        description: prefs.getString('${pointId}_description') ?? '',
        image: prefs.getStringList('${pointId}_image') ?? [],
        review: Review(
          id: prefs.getInt('${pointId}_review_id') ?? 0,
          likes: prefs.getInt('${pointId}_review_likes') ?? 0,
          dislikes: prefs.getInt('${pointId}_review_dislikes') ?? 0,
        ),
        latitude: prefs.getDouble('${pointId}_latitude') ?? 0.0,
        longitude: prefs.getDouble('${pointId}_longitude') ?? 0.0,
      );

      // Verifica se o ponto de interesse pertence ao local atual
      return poi.locationId == currentLocationId ? poi : null;
    } else {
      return null; // Retornar nulo se o ponto de interesse não foi classificado
    }
  }




  static Future<List<PointOfInterest>> loadRecentlyViewedPoints() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<int> recentlyViewedIds = prefs.getStringList('recentlyViewedIds')?.map((id) => int.parse(id)).toList() ?? [];

    List<PointOfInterest> recentlyViewed = [];
    for (int id in recentlyViewedIds) {
      PointOfInterest? poi = null;
      if (poi != null) {
        recentlyViewed.add(poi);
      }
    }

    return recentlyViewed;
  }



  Future<List<Location>> getAllLocations() async {
    var collectionReference = FirebaseFirestore.instance.collection("location");
    var collection = await collectionReference.get();
    //return snapshot.docs.map((doc) => doc.data()).toList();
    List<Location> list = List.empty();
    for(var doc in collection.docs) {
      debugPrint("Doc: ${doc.id}"); //doc.data()...
      list.add(Location(
          id: doc['id'],
          title: doc['title'],
          description:  doc['description'],
          image:  doc['image'],
          review:  doc['review'],
          latitude:  doc['latitude'],
          longitude:  doc['longitude']));
    }
    return list;
  }






  void _addToRecentlyViewedIds(int pointOfInterestId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    // Obtenha a lista atual de IDs recentemente visualizados
    List<int> recentlyViewedIds = prefs.getStringList('recentlyViewedIds')?.map((id) => int.parse(id)).toList() ?? [];

    // Adicione o ID à lista
    recentlyViewedIds.add(pointOfInterestId);

    // Mantenha apenas os últimos 10 IDs
    if (recentlyViewedIds.length > 10) {
      recentlyViewedIds = recentlyViewedIds.sublist(recentlyViewedIds.length - 10);
    }

    // Salve a lista atualizada em SharedPreferences
    prefs.setStringList('recentlyViewedIds', recentlyViewedIds.map((id) => id.toString()).toList());
  }


  List<Location> _locations = [
    Location(
      id: 1,
      title: "Local A",
      description: "Descrição do Local A",
      image: ["image_url_a"],
      review: Review(id: 1, likes: 5, dislikes: 2),
      latitude: 12.345,
      longitude: 67.890,
    ),
    Location(
      id: 2,
      title: "Local B",
      description: "Descrição do Local B",
      image: ["image_url_a"],
      review: Review(id: 1, likes: 5, dislikes: 2),
      latitude: 12.345,
      longitude: 67.890,
    ),
    Location(
      id: 3,
      title: "Local A",
      description: "Descrição do Local B",
      image: ["image_url_a"],
      review: Review(id: 1, likes: 5, dislikes: 2),
      latitude: 12.345,
      longitude: 67.890,
    ),
  ];

  List<PointOfInterest> _pointsOfInterest = [
    PointOfInterest(
      id: 1,
      title: "Ponto de Interesse 1",
      description: "Descrição do Ponto de Interesse 1",
      image: ["image_url_poi1"],
      review: Review(id: 2, likes: 8, dislikes: 1),
      latitude: 12.345,
      longitude: 67.890, locationId: 1,
    ),
    // Adicione mais pontos de interesse conforme necessário
  ];

  List<Location> getFilteredAndSortedLocations() {
    List<Location> filteredLocations = _locations
        .where((location) =>
        location.title.toLowerCase().contains(_searchController.text.toLowerCase()))
        .toList();

    if (_sortByAlphabetical) {
      filteredLocations.sort((a, b) => a.title.compareTo(b.title));
    }

    return filteredLocations;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Locations"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller: _searchController,
              decoration: InputDecoration(labelText: "Search Locations"),
            ),
            SizedBox(height: 8.0),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      _sortByAlphabetical = !_sortByAlphabetical;
                    });
                  },
                  child: Text(_sortByAlphabetical ? "Sort Default" : "Sort ↑"),
                ),
                SizedBox(width: 8.0),
                ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => RecentlyViewedScreen(),
                      ),
                    );
                  },
                  child: Text("Recently Viewed Points"),
                ),
              ],
            ),
            SizedBox(height: 8.0),
            Expanded(
              child: ListView.builder(
                itemCount: getFilteredAndSortedLocations().length,
                itemBuilder: (context, index) {
                  final location = getFilteredAndSortedLocations()[index];
                  return LocationItem(
                    location: location,
                    pointsOfInterest: _pointsOfInterest,
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class LocationItem extends StatelessWidget {
  final Location location;
  final List<PointOfInterest> pointsOfInterest;

  const LocationItem({
    Key? key,
    required this.location,
    required this.pointsOfInterest,
  }) : super(key: key);

  List<PointOfInterest> getPointsOfInterestForLocation(Location location) {
    return pointsOfInterest
        .where((poi) => poi.latitude == location.latitude && poi.longitude == location.longitude)
        .toList();
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      child: InkWell(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => LocationView(
                location: location,
                pointsOfInterest: getPointsOfInterestForLocation(location),
              ),
            ),
          );
        },
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Text(
            location.title,
            style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
          ),
        ),
      ),
    );
  }
}

class LocationView extends StatelessWidget {
  final Location location;
  final List<PointOfInterest> pointsOfInterest;

  const LocationView({
    Key? key,
    required this.location,
    required this.pointsOfInterest,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(location.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              location.description,
              style: TextStyle(fontSize: 18.0),
            ),
            SizedBox(height: 16.0),
            Text(
              "Latitude: ${location.latitude}",
              style: TextStyle(fontSize: 16.0),
            ),
            Text(
              "Longitude: ${location.longitude}",
              style: TextStyle(fontSize: 16.0),
            ),
            SizedBox(height: 16.0),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => PointsOfInterestScreen(
                      pointsOfInterest: pointsOfInterest,
                    ),
                  ),
                );
              },
              child: Text("View Points of Interest"),
            ),
          ],
        ),
      ),
    );
  }
}

class PointsOfInterestScreen extends StatelessWidget {
  final List<PointOfInterest> pointsOfInterest;

  const PointsOfInterestScreen({Key? key, required this.pointsOfInterest}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Points of Interest"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView.builder(
          itemCount: pointsOfInterest.length,
          itemBuilder: (context, index) {
            final pointOfInterest = pointsOfInterest[index];
            return PointOfInterestItem(pointOfInterest: pointOfInterest);
          },
        ),
      ),
    );
  }
}

class PointOfInterestItem extends StatefulWidget {
  final PointOfInterest pointOfInterest;

  const PointOfInterestItem({Key? key, required this.pointOfInterest}) : super(key: key);

  @override
  _PointOfInterestItemState createState() => _PointOfInterestItemState();
}

class _PointOfInterestItemState extends State<PointOfInterestItem> {
  bool isLiked = false;
  bool isDisliked = false;

  @override
  void initState() {
    super.initState();
    _loadLikeDislikeStatus();
  }
  @override
  Widget build(BuildContext context) {
    return Card(
      child: InkWell(
        onTap: () {
          _addToRecentlyViewedIds(widget.pointOfInterest.id);
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => PointOfInterestView(pointOfInterest: widget.pointOfInterest),
            ),
          );
        },
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text(
                widget.pointOfInterest.title,
                style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 16.0),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      _handleLikeDislike(true); // true indica "Like"
                    },
                    style: ButtonStyle(
                      backgroundColor: MaterialStateProperty.all(isLiked ? Colors.green : null),
                    ),
                    child: Text("Like"),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      _handleLikeDislike(false); // false indica "Dislike"
                    },
                    style: ButtonStyle(
                      backgroundColor: MaterialStateProperty.all(isDisliked ? Colors.red : null),
                    ),
                    child: Text("Dislike"),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _handleLikeDislike(bool isLike) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    if (isLike) {
      // Adicione a lógica para tratar o "Like"
      prefs.setBool('${widget.pointOfInterest.id}_isLiked', true);
      prefs.setBool('${widget.pointOfInterest.id}_isDisliked', false);
    } else {
      // Adicione a lógica para tratar o "Dislike"
      prefs.setBool('${widget.pointOfInterest.id}_isLiked', false);
      prefs.setBool('${widget.pointOfInterest.id}_isDisliked', true);
    }

    setState(() {
      _loadLikeDislikeStatus();
    });
  }

  void _loadLikeDislikeStatus() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      isLiked = prefs.getBool('${widget.pointOfInterest.id}_isLiked') ?? false;
      isDisliked = prefs.getBool('${widget.pointOfInterest.id}_isDisliked') ?? false;
    });
  }

  void _addToRecentlyViewedIds(int pointId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    List<int> recentlyViewedIds = prefs.getStringList('recentlyViewedIds')?.map((id) => int.parse(id))?.toList() ?? [];
    // Adiciona o id à lista, removendo se já existir
    recentlyViewedIds.remove(pointId);
    recentlyViewedIds.insert(0, pointId);

    // Mantém apenas os últimos 10 ids
    if (recentlyViewedIds.length > 10) {
      recentlyViewedIds = recentlyViewedIds.sublist(0, 10);
    }

    // Salva a lista atualizada
    prefs.setStringList('recentlyViewedIds', recentlyViewedIds.map((id) => id.toString()).toList());
  }
}

class SharedPreferencesUtils {
  static Future<List<PointOfInterest>> loadRecentlyViewedPoints() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<int> recentlyViewedIds = prefs.getStringList('recentlyViewedIds')?.map((id) => int.parse(id)).toList() ?? [];

    List<PointOfInterest> recentlyViewed = [];
    for (int id in recentlyViewedIds) {
      PointOfInterest? poi = await loadPointFromSharedPreferences(id);
      if (poi != null) {
        recentlyViewed.add(poi);
      }
    }

    return recentlyViewed;
  }

  static Future<PointOfInterest?> loadPointFromSharedPreferences(int pointId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    bool isLiked = prefs.getBool('${pointId}_isLiked') ?? false;
    bool isDisliked = prefs.getBool('${pointId}_isDisliked') ?? false;

    if (isLiked || isDisliked) {
      return PointOfInterest(
        id: 1, // Adicionando um ID fixo (pode ajustar conforme necessário)
        title: prefs.getString('${pointId}_title') ?? '',
        description: prefs.getString('${pointId}_description') ?? '',
        image: prefs.getStringList('${pointId}_image') ?? [],
        review: Review(
          id: prefs.getInt('${pointId}_review_id') ?? 0,
          likes: prefs.getInt('${pointId}_review_likes') ?? 0,
          dislikes: prefs.getInt('${pointId}_review_dislikes') ?? 0,
        ),
        latitude: prefs.getDouble('${pointId}_latitude') ?? 0.0,
        longitude: prefs.getDouble('${pointId}_longitude') ?? 0.0,
        locationId: prefs.getInt('${pointId}_location_id') ?? 0, // Adicionando locationId
      );
    }


    return null;

  }
}


class PointOfInterestView extends StatelessWidget {
  final PointOfInterest pointOfInterest;

  const PointOfInterestView({Key? key, required this.pointOfInterest}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(pointOfInterest.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              pointOfInterest.description,
              style: TextStyle(fontSize: 18.0),
            ),
            SizedBox(height: 16.0),
            Text(
              "Latitude: ${pointOfInterest.latitude}",
              style: TextStyle(fontSize: 16.0),
            ),
            Text(
              "Longitude: ${pointOfInterest.longitude}",
              style: TextStyle(fontSize: 16.0),
            ),
          ],
        ),
      ),
    );
  }
}
