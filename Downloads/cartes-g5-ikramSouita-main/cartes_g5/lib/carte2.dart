import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:http/http.dart' as http;

class Carte2 extends StatefulWidget {
  const Carte2({Key? key}) : super(key: key);

  @override
  State<Carte2> createState() => _Carte2State();
}

class _Carte2State extends State<Carte2> {
  List<Map<String, dynamic>> pharmacies = [];

  @override
  void initState() {
    super.initState();
    fetchPharmacies();
  }

  Future<void> fetchPharmacies() async {
    try {
      final response = await http.get(Uri.parse(
          'https://api.jsonbin.io/v3/b/658212f71f5677401f10889b'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        setState(() {
          pharmacies = (data['record']['pharmacies'] as List).map((pharmacy) {
            final fields = pharmacy['fields'];
            return {
              'name': fields['pharmacie'],
              'latitude': double.parse(fields['latitude']),
              'longitude': double.parse(fields['longitude']),
            };
          }).toList();
        });
      } else {
        throw Exception('Erreur API');
      }
    } catch (e) {
      print('Erreur: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Carte2 - Pharmacies'),
        backgroundColor: Colors.green,
      ),
      body: FlutterMap(
        options: const MapOptions(
          initialCenter: LatLng(48.8, 2.23), // Paris par défaut
          initialZoom: 14, // Zoom initial
        ),
        children: [
          TileLayer(
            urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
            userAgentPackageName: 'com.example.app',
          ),
          if (pharmacies.isNotEmpty)
            MarkerLayer(
              markers: pharmacies.map((pharmacy) {
                return Marker(
                  width: 80.0,
                  height: 80.0,
                  point: LatLng(pharmacy['latitude'], pharmacy['longitude']),
                  child: Column(
                    children: [
                      const Icon(
                        Icons.local_pharmacy,
                        size: 40,
                        color: Colors.red,
                      ),
                      Text(
                        pharmacy['name'],
                        style: const TextStyle(
                          fontSize: 10,
                          color: Colors.black,
                        ),
                      ),
                    ],
                  ),
                );
              }).toList(),
            ),
        ],
      ),
    );
  }
}
