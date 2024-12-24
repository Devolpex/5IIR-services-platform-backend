class Demande {
  final int id;
  final String description;
  final String lieu;
  final String service; // Champ requis

  Demande({
    required this.id,
    required this.description,
    required this.lieu,
    required this.service, // Champ obligatoire
  });

  // Méthode fromJson
  factory Demande.fromJson(Map<String, dynamic> json) {
    return Demande(
      id: json['id'],
      description: json['description'],
      lieu: json['lieu'],
      service: json['service'],
    );
  }

  // Méthode toJson
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'description': description,
      'lieu': lieu,
      'service': service,
    };
  }
}
