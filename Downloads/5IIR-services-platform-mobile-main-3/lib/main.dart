import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:logger/logger.dart';
import 'package:mobile/screens/authentication/auth_wrapper.dart';
import 'package:mobile/utils/boxes.dart';
// import 'package:provider/provider.dart';

void main() async {
  Logger logger=Logger();
  // Initializing Flutter Hive database
  await Hive.initFlutter();

  // Opening database of the saved boxes
  await Future.wait([
    // Hive.openBox(Boxes.notificationBox),
    // Hive.openBox(Boxes.devicesBox),
    Hive.openBox(Boxes.authBox),
  ]);

  // Initializing dotenv variables
  try {
    await dotenv.load(fileName: "assets/dotenv/.env");
    logger.i("dotenv loaded");
  } catch (e) {
    print("Error loading .env file: $e");
  }

  runApp(AppWidget());
}

class AppWidget extends StatelessWidget {
  AppWidget({super.key});
  Logger logger = Logger();
  
  @override
  Widget build(BuildContext context) {
    logger.i("AppWidget build");
    return ScreenUtilInit(
      
      designSize: Size(375, 812),
      builder: (context, child) => GetMaterialApp(
        debugShowCheckedModeBanner: false, 
        title: 'JAIM Services',
        theme: ThemeData(
          primarySwatch: Colors.blue,
          visualDensity: VisualDensity.adaptivePlatformDensity,
        ),
        home: Scaffold( // Wrap AuthWrapper with Scaffold<
          body: AuthWrapper(),
        ),
      ),
    );
  }
}