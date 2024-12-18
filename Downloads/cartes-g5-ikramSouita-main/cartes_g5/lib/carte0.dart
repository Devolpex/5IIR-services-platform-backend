import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

class Carte0 extends StatelessWidget {
  const Carte0({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Carte0 - Carte Basique'),
        backgroundColor: Colors.green,
      ),
      body: FlutterMap(
        options: const MapOptions(
          initialCenter: LatLng(31.63139639931016, -8.01278402883554), // Marrakech
          initialZoom: 10, // Zoom initial
        ),
        children: [
          TileLayer(
            urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
            userAgentPackageName: 'com.example.app',
          ),
        ],
      ),
    );
  }
}
