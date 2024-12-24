import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:logger/logger.dart';
import 'auth_service.dart';

class DemandeService {
  final Dio _dio = Dio();
  final AuthService _authService = AuthService();
  final Logger logger = Logger();
  final String baseUrl = dotenv.env['BACKEND_URL'] ?? "";

  DemandeService() {
    if (baseUrl.isEmpty) {
      logger.e("BACKEND_URL non configuré dans le fichier .env.");
      throw Exception("BACKEND_URL non configuré.");
    }
  }

  /// Gestion des erreurs Dio
  void _handleDioError(DioError e) {
    if (e.response != null) {
      logger.e("Erreur Serveur [${e.response?.statusCode}] : ${e.response?.data}");
    } else {
      logger.e("Erreur Réseau : ${e.message}");
    }
  }

  /// Récupérer la liste des offres
  Future<List<dynamic>> getOffres() async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) throw Exception("Token non trouvé.");

      final response = await _dio.get(
        "$baseUrl/api/offres/list",
        options: Options(headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        }),
      );

      if (response.statusCode == 200 && response.data is List) {
        logger.i("Offres récupérées avec succès.");
        return response.data;
      } else {
        logger.e("Erreur lors de la récupération des offres : ${response.data}");
        return [];
      }
    } on DioError catch (e) {
      _handleDioError(e);
      return [];
    } catch (e) {
      logger.e("Erreur inconnue lors de la récupération des offres : $e");
      return [];
    }
  }

  /// Approuver une offre
  Future<void> approveOffer(int offerId, BuildContext context) async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) {
        throw Exception("Token non trouvé.");
      }

      // Configuration des en-têtes HTTP
      final headers = {
        "Authorization": "Bearer $token",
        "Content-Type": "application/json",
      };

      // URL pour l'approbation de l'offre
      final orderUrl = "$baseUrl/api/order/offer/confirm/$offerId";

      // Appel PATCH pour approuver l'offre
      final response = await _dio.patch(
        orderUrl,
        options: Options(headers: headers),
      );

      // Gestion de la réponse
      if (response.statusCode == 200) {
        print("Offre approuvée avec succès : ${response.data}");
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Offre approuvée avec succès.")),
        );
      } else {
        print("Erreur lors de l'approbation de l'offre : ${response.data}");
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Erreur lors de l'approbation de l'offre.")),
        );
      }
    } catch (e) {
      // Gestion des erreurs
      print("Erreur lors de l'approbation de l'offre : $e");
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Erreur réseau ou serveur.")),
      );
    }
  }



  /// Récupérer les détails d'une offre
  Future<Map<String, dynamic>> getOfferDetails(int id) async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) throw Exception("Token non trouvé.");

      final response = await _dio.get(
        "$baseUrl/api/offres/$id",
        options: Options(headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        }),
      );

      if (response.statusCode == 200) {
        logger.i("Détails de l'offre récupérés avec succès.");
        return response.data;
      } else {
        logger.e("Erreur lors de la récupération des détails de l'offre : ${response.data}");
        return {};
      }
    } on DioError catch (e) {
      _handleDioError(e);
      return {};
    } catch (e) {
      logger.e("Erreur inconnue lors de la récupération des détails de l'offre : $e");
      return {};
    }
  }

  /// Créer une nouvelle demande
  Future<void> createDemande(Map<String, dynamic> demandeData) async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) throw Exception("Token non trouvé.");

      final response = await _dio.post(
        "$baseUrl/api/demande",
        data: jsonEncode(demandeData),
        options: Options(headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        }),
      );

      if (response.statusCode == 201) {
        logger.i("Demande créée avec succès.");
      } else {
        logger.e("Erreur lors de la création de la demande : ${response.data}");
      }
    } on DioError catch (e) {
      _handleDioError(e);
      rethrow;
    } catch (e) {
      logger.e("Erreur inconnue lors de la création de la demande : $e");
      rethrow;
    }
  }

  /// Mettre à jour une demande existante
  Future<void> updateDemande(int id, Map<String, dynamic> demandeData) async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) throw Exception("Token non trouvé.");

      final response = await _dio.put(
        "$baseUrl/api/demande/$id",
        data: jsonEncode(demandeData),
        options: Options(headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        }),
      );

      if (response.statusCode == 200) {
        logger.i("Demande mise à jour avec succès.");
      } else {
        logger.e("Erreur lors de la mise à jour de la demande : ${response.data}");
      }
    } on DioError catch (e) {
      _handleDioError(e);
      rethrow;
    } catch (e) {
      logger.e("Erreur inconnue lors de la mise à jour de la demande : $e");
      rethrow;
    }
  }

  /// Récupérer les demandes de l'utilisateur
  Future<List<dynamic>> getUserDemandes() async {
    try {
      final token = _authService.getAuthToken();
      if (token == null) throw Exception("Token non trouvé.");

      final response = await _dio.get(
        "$baseUrl/api/demande/mes-demandes",
        options: Options(headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        }),
      );

      if (response.statusCode == 200) {
        logger.i("Demandes récupérées avec succès.");
        return response.data;
      } else {
        logger.e("Erreur lors de la récupération des demandes : ${response.data}");
        return [];
      }
    } on DioError catch (e) {
      _handleDioError(e);
      return [];
    } catch (e) {
      logger.e("Erreur inconnue lors de la récupération des demandes : $e");
      return [];
    }
  }
}
