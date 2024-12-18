import 'package:flutter/material.dart';
import 'carte0.dart';
import 'carte1.dart';
import 'carte2.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Multi-Cartes',
      theme: ThemeData(primarySwatch: Colors.green),
      home: const HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Multi-Cartes'),
        backgroundColor: Colors.green,
      ),
      body: ListView(
        children: [
          ListTile(
            title: const Text('Carte 0 - Basique'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const Carte0()),
              );
            },
          ),
          ListTile(
            title: const Text('Carte 1 - EMSI'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const Carte1()),
              );
            },
          ),
          ListTile(
            title: const Text('Carte 2 - Pharmacies'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const Carte2()),
              );
            },
          ),
        ],
      ),
    );
  }
}
