import 'package:flutter/material.dart';
import '../../services/demande_service.dart';

class FormulaireDemande extends StatefulWidget {
  final Map<String, dynamic>? demande;
  const FormulaireDemande({Key? key, this.demande}) : super(key: key);

  @override
  State<FormulaireDemande> createState() => _FormulaireDemandeState();
}

class _FormulaireDemandeState extends State<FormulaireDemande> {
  final _formKey = GlobalKey<FormState>();
  String description = "";
  String lieu = "";
  String service = "";

  @override
  void initState() {
    super.initState();
    if (widget.demande != null) {
      description = widget.demande!['description'] ?? "";
      lieu = widget.demande!['lieu'] ?? "";
      service = widget.demande!['service'] ?? "";
    }
  }

  Future<void> saveDemande() async {
    final demandeService = DemandeService();
    if (widget.demande == null) {
      await demandeService.createDemande({
        "description": description,
        "lieu": lieu,
        "service": service,
      });
    } else {
      await demandeService.updateDemande(widget.demande!['id'], {
        "description": description,
        "lieu": lieu,
        "service": service,
      });
    }
    Navigator.pop(context, true); // Retour après enregistrement
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.demande == null ? "Créer une Demande" : "Modifier la Demande"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                initialValue: description,
                decoration: const InputDecoration(labelText: "Description"),
                onChanged: (value) => description = value,
              ),
              TextFormField(
                initialValue: lieu,
                decoration: const InputDecoration(labelText: "Lieu"),
                onChanged: (value) => lieu = value,
              ),
              TextFormField(
                initialValue: service,
                decoration: const InputDecoration(labelText: "Service"),
                onChanged: (value) => service = value,
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: saveDemande,
                child: const Text("Enregistrer"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
