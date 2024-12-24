import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:mobile/screens/authentication/signin/signin_dialog.dart';
import 'package:mobile/utils/helpers.dart';
import '../../../utils/colors.dart';


class WelcomeScreen extends StatefulWidget {
  const WelcomeScreen({super.key});

  @override
  State<WelcomeScreen> createState() => _WelcomeScreenState();
}

class _WelcomeScreenState extends State<WelcomeScreen> {
  bool isSignDialogShow = false;

  @override
  void initState() {
    super.initState();

    // Show the SignInDialog as soon as the screen is loaded
    WidgetsBinding.instance.addPostFrameCallback((_) {
      setState(() {
        isSignDialogShow = true;
      });
      customSignInDialog(context, isClosed: (_) {
        setState(() {
          isSignDialogShow = false;
        });
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: primary,
      child: SafeArea(
        bottom: false,
        left: false,
        right: false,
        child: Scaffold(
          body: Stack(
            children: [
              // Background image
              Container(
                decoration: const BoxDecoration(
                  image: DecorationImage(
                    image: AssetImage("assets/images/welcome.png"),
                    fit: BoxFit.cover,
                  ),
                ),
              ),

              // Footer with the "JAIMSERVICE 2024" text
              Align(
                alignment: Alignment.bottomCenter,
                child: SafeArea(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(
                        Icons.copyright,
                        size: 14.sp,
                        color: Colors.grey.shade600,
                      ),
                      Text(
                        "JAIM Services 2024",
                        style: TextStyle(
                          fontSize: 12.sp,
                          color: Colors.grey.shade600,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              addVerticalSpace(10),
            ],
          ),
        ),
      ),
    );
  }
}