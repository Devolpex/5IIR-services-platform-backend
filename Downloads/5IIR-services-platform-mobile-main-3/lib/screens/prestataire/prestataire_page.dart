import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:mobile/utils/colors.dart';

class PrestatairePage extends StatefulWidget {
  const PrestatairePage({Key? key}) : super(key: key);

  @override
  State<PrestatairePage> createState() => _PrestatairePageState();
}

class _PrestatairePageState extends State<PrestatairePage> {
  Logger logger = Logger();

  // List of menu items
  final List<String> menuItems = [
    "Demande List",
    "Proposition List",
    "Offer List",
    "Order List",
  ];

  // Current selected menu index
  int selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    logger.i("PrestatairePage build, selectedIndex: $selectedIndex");

    return Scaffold(
      appBar: AppBar(
        backgroundColor: primary,
        foregroundColor: Colors.white,
        title: Text(menuItems[selectedIndex],style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)), // Update title dynamically
        centerTitle: true,
        leading: Builder(
          builder: (context) => IconButton(
            icon: const Icon(Icons.menu),
            onPressed: () => Scaffold.of(context).openDrawer(),
          ),
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.account_circle),
            onPressed: () {
              // Handle profile button press
            },
          ),
        ],
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            Container(
              height: 110,
              child: DrawerHeader(
                decoration: BoxDecoration(
                  color: primary,
                  shape: BoxShape.rectangle,
                ),
                margin: EdgeInsets.zero,
                child: const Text(
                  'Menu',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 24,
                  ),
                ),
              ),
            ),
            ...List.generate(menuItems.length, (index) {
              return ListTile(
                title: Text(menuItems[index]),
                selected: selectedIndex == index, // Highlight selected item
                onTap: () {
                  setState(() {
                    selectedIndex = index; // Update selected index
                  });
                  Navigator.pop(context); // Close the drawer
                },
              );
            }),
          ],
        ),
      ),
      body: Center(
        child: Text(
          menuItems[selectedIndex], // Display selected menu content
          style: const TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}
