import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:mobile/controllers/auth.dart';
import 'package:mobile/screens/welcome.dart';
import 'package:mobile/screens/demandeur/demandeur_page.dart';
import 'package:mobile/screens/prestataire/prestataire_page.dart';
import 'package:mobile/services/auth_service.dart';

class AuthWrapper extends StatefulWidget {
  const AuthWrapper({super.key});

  @override
  State<AuthWrapper> createState() => _AuthWrapperState();
}

class _AuthWrapperState extends State<AuthWrapper> {
  final auth = Get.put(AuthState());
  Logger logger = Logger();

  final _hiveDb = AuthService();

  _init() {
    final account = _hiveDb.getAuth();
    logger.i("AuthWrapper account: $account");
    if (account != null) {
      auth.isSignedIn.value = true;
      auth.role.value = account.role; // Store the role in the auth state
    }
  }

  @override
  void initState() {
    _init();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    logger.i("AuthWrapper build");
    return Obx(
      () {
        if (auth.isSignedIn.value) {
          switch (auth.role.value) {
            case 'DEMANDEUR':
              logger.i("AuthWrapper DEMANDEUR");
              return DemandeurPage();
            case 'PRESTATAIRE':
              logger.i("AuthWrapper PRESTATAIRE");
              return PrestatairePage();
            default:
              return WelcomeScreen();
          }
        } else {
          return const WelcomeScreen();
        }
      },
    );
  }
}