import 'package:flutter/material.dart';
import '../../services/demande_service.dart';
import 'OfferDetailsPage.dart';

class DemandeurPage extends StatefulWidget {
  const DemandeurPage({Key? key}) : super(key: key);

  @override
  State<DemandeurPage> createState() => _DemandeurPageState();
}

class _DemandeurPageState extends State<DemandeurPage> {
  final DemandeService _demandeService = DemandeService();
  List<dynamic> offres = [];
  bool isLoading = true;
  String? errorMessage; // Pour stocker les messages d'erreur

  @override
  void initState() {
    super.initState();
    loadOffres();
  }

  Future<void> loadOffres() async {
    setState(() {
      isLoading = true;
      errorMessage = null; // Réinitialiser le message d'erreur
    });

    try {
      final result = await _demandeService.getOffres();
      setState(() {
        offres = result;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        errorMessage = "Erreur lors de la récupération des offres : $e";
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Liste des Offres"),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : errorMessage != null
          ? Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              errorMessage!,
              style: const TextStyle(color: Colors.red),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: loadOffres, // Relancer le chargement
              child: const Text("Réessayer"),
            ),
          ],
        ),
      )
          : offres.isEmpty
          ? const Center(child: Text("Aucune offre disponible"))
          : ListView.builder(
        itemCount: offres.length,
        itemBuilder: (context, index) {
          final offre = offres[index];
          return Card(
            child: ListTile(
              title: Text(
                offre['description'] ?? "Pas de description",
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
              subtitle: Text(
                  "Tarif : ${offre['tarif']?.toString() ?? 'N/A'} €"),
              trailing: ElevatedButton(
                onPressed: () {
                  // Navigation vers OfferDetailsPage avec l'ID de l'offre
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => OfferDetailsPage(
                        offerId: offre['id'], // Passez l'ID de l'offre
                      ),
                    ),
                  );
                },
                child: const Text("Détails"),
              ),
            ),
          );
        },
      ),
    );
  }
}
