import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

class Carte1 extends StatelessWidget {
  const Carte1({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Marqueurs pour EMIS Guéliz et EMIS Centre
    final emsiGueliz = const Marker(
      width: 80.0,
      height: 80.0,
      point: LatLng(31.6537468982776, -8.02145129999999),
      child: Icon(Icons.location_on, size: 50, color: Colors.red),
    );

    final emsiCentre = const Marker(
      width: 80.0,
      height: 80.0,
      point: LatLng(31.631264681786426, -8.0127703503005),
      child: Icon(Icons.location_on, size: 50, color: Colors.blue),
    );

    return Scaffold(
      appBar: AppBar(
        title: const Text('Carte1 - Avec Marqueurs'),
        backgroundColor: Colors.green,
      ),
      body: FlutterMap(
        options: const MapOptions(
          initialCenter: LatLng(31.63139639931016, -8.01278402883554), // Marrakech
          initialZoom: 14, // Zoom initial
        ),
        children: [
          TileLayer(
            urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
            userAgentPackageName: 'com.example.app',
          ),
          MarkerLayer(
            markers: [emsiGueliz, emsiCentre],
          ),
        ],
      ),
    );
  }
}
