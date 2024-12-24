import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';
import 'package:mobile/controllers/auth.dart';
import 'package:mobile/screens/authentication/signin/signin_input_field.dart';
import 'package:mobile/services/auth_service.dart';
import 'package:mobile/utils/colors.dart';
import 'package:mobile/utils/top_snackbar.dart';

enum IsLoading { idle, loading, success }

class SignInForm extends StatefulWidget {
  const SignInForm({super.key});

  @override
  State<SignInForm> createState() => _SignInFormState();
}

class _SignInFormState extends State<SignInForm> {
  final auth = Get.put(AuthState());
  final authService = Get.put(AuthService()); // Injecting AuthService

  String? email;
  String? password;
  IsLoading _isLoading = IsLoading.idle;

  void _signIn() async {
    if (_isLoading == IsLoading.loading) return;

    setState(() {
      _isLoading = IsLoading.loading;
    });

    authService.signIn(email ?? '', password ?? '', (success, errorMessage) {
      setState(() {
        _isLoading = IsLoading.idle;
      });

      if (success) {
        showMessage(
          message: "Authenticated successfully",
          title: "Success",
          type: MessageType.success,
        );
        showMessage(
          message: "Authenticated as $email",
          title: "Success",
          type: MessageType.success,
        );
        Future.delayed(const Duration(milliseconds: 400), () {
          auth.isSignedIn.value = true;
          // Close the dialog after successful sign-in
          Navigator.of(context).pop();
        });
      } else if (errorMessage != null) {
        showMessage(
          message: errorMessage,
          title: "Error",
          type: MessageType.error,
        );
      }
    });
  }

  @override
  Widget build(BuildContext context) {
      // If the user is not signed in, show the sign-in form
      final loading = _isLoading == IsLoading.loading;

      return Form(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SignInInputField(
              hintText: "Email",
              svg: "email.svg",
              onChanged: (value) {
                setState(() {
                  email = value;
                });
              },
            ),
            SignInInputField(
              hintText: "Password",
              svg: "pwd.svg",
              isPwd: true,
              onChanged: (value) {
                setState(() {
                  password = value;
                });
              },
            ),
            Center(
              child: Container(
                width: 150.w,
                margin: EdgeInsets.only(top: 10.h, bottom: 24.h),
                child: ElevatedButton.icon(
                  onPressed: loading ? null : _signIn,
                  icon: loading
                      ? LoadingAnimationWidget.progressiveDots(
                          color: primary, size: 30.h)
                      : const Icon(CupertinoIcons.lock_shield, size: 30,),
                  label: loading
                      ? LoadingAnimationWidget.staggeredDotsWave(
                          color: primary,
                          size: 25.h,
                        )
                      : Text(
                          "Login",
                          style: TextStyle(fontSize: 18.sp, fontWeight: FontWeight.bold),
                        ),
                  style: ButtonStyle(
                    padding: WidgetStateProperty.all(
                      EdgeInsets.symmetric(vertical: 12.h),
                    ),
                    backgroundColor: WidgetStateProperty.all(white),
                    foregroundColor: WidgetStateProperty.all(primary),	
                    shape: WidgetStateProperty.all(
                      RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.r),
                      ),
                    ),
                  ),
                ),
              ),
            )
          ],
        ),
      );
    }
    // );
  }
// }
