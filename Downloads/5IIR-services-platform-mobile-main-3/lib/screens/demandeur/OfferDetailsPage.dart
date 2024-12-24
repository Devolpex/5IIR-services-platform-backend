import 'package:flutter/material.dart';
import '../../services/demande_service.dart';

class OfferDetailsPage extends StatefulWidget {
  final int offerId;

  const OfferDetailsPage({Key? key, required this.offerId}) : super(key: key);

  @override
  State<OfferDetailsPage> createState() => _OfferDetailsPageState();
}

class _OfferDetailsPageState extends State<OfferDetailsPage> {
  final DemandeService _demandeService = DemandeService();
  Map<String, dynamic>? offerDetails; // Stocker les détails de l'offre
  bool isLoading = true; // Indicateur de chargement
  bool hasError = false; // Indicateur d'erreur

  @override
  void initState() {
    super.initState();
    fetchOfferDetails(); // Charger les détails de l'offre au démarrage
  }

  Future<void> fetchOfferDetails() async {
    setState(() {
      isLoading = true;
      hasError = false;
    });

    try {
      final result = await _demandeService.getOfferDetails(widget.offerId);
      setState(() {
        offerDetails = result;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        hasError = true;
        isLoading = false;
      });
      debugPrint("Erreur lors de la récupération des détails de l'offre : $e");
    }
  }

  Future<void> approveOffer() async {
    if (offerDetails == null) return;

    try {
      await _demandeService.approveOffer(offerDetails!['id'], context);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Offre approuvée avec succès !")),
      );
      Navigator.pop(context); // Retour à la liste des offres après approbation
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Erreur lors de l'approbation de l'offre.")),
      );
      debugPrint("Erreur lors de l'approbation de l'offre : $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Détails de l'Offre"),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : hasError
          ? Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              "Une erreur est survenue lors de la récupération des détails de l'offre.",
              textAlign: TextAlign.center,
              style: TextStyle(color: Colors.red),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: fetchOfferDetails,
              child: const Text("Réessayer"),
            ),
          ],
        ),
      )
          : offerDetails != null
          ? Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "ID : ${offerDetails!['id']}",
              style: const TextStyle(
                  fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              "Description : ${offerDetails!['description']}",
              style: const TextStyle(fontSize: 16),
            ),
            const SizedBox(height: 8),
            Text(
              "Tarif : ${offerDetails!['tarif']} €",
              style: const TextStyle(fontSize: 16),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: approveOffer,
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                padding: const EdgeInsets.symmetric(
                    vertical: 12, horizontal: 24),
              ),
              child: const Text("Approuver l'offre"),
            ),
          ],
        ),
      )
          : const Center(
        child: Text(
            "Aucune information disponible pour cette offre."),
      ),
    );
  }
}
