import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:mobile/utils/colors.dart';


import 'signin_form.dart';

Future<Object?> customSignInDialog(BuildContext context,
    {required ValueChanged isClosed}) {
  return showGeneralDialog(
    context: context,
    barrierDismissible: false,
    barrierLabel: "Sign in",
    transitionDuration: const Duration(milliseconds: 400),
    transitionBuilder: ((context, animation, secondaryAnimation, child) {
      Tween<Offset> tween;
      tween = Tween(begin: const Offset(-1, 0), end: Offset.zero);
      return SlideTransition(
        position: tween.animate(
          CurvedAnimation(parent: animation, curve: Curves.easeInOut),
        ),
        child: child,
      );
    }),
    pageBuilder: ((context, _, __) {
      return Center(
        child: Container(
          height: 400.h,
          padding: EdgeInsets.symmetric(
            vertical: 15.h,
            horizontal: 30.w,
          ),
          margin: EdgeInsets.symmetric(horizontal: 30.w),
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.2),
            borderRadius: BorderRadius.circular(40.r),
          ),
          child: Material(
            color: transparent,
            child: SingleChildScrollView(
              child: Column(
                children: [
                  Text(
                    "Login",
                    style: TextStyle(
                        fontSize: 35.sp,
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                      ),
                  ),
                  SizedBox(height: 10.h),
                  Text(
                    "Get access to your account",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 16.sp,
                      color: white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 12.h),
                  const SignInForm(),
                  // Row(
                  //   children: [
                  //     const Expanded(child: Divider()),
                  //     Padding(
                  //       padding: EdgeInsets.symmetric(horizontal: 16.w),
                  //       child: const Text(
                  //         "OR",
                  //         style: TextStyle(
                  //           color: white,
                  //           fontWeight: FontWeight.bold,
                  //         ),
                  //       ),
                  //     ),
                  //     const Expanded(child: Divider()),
                  //   ],
                  // ),
                  // Padding(
                  //   padding: EdgeInsets.symmetric(vertical: 20.h),
                  //   child: Text(
                  //     "Sign in with Email",
                  //     style: TextStyle(
                  //       color: white,
                  //       fontSize: 14.sp,
                  //       fontWeight: FontWeight.bold,
                  //     ),
                  //   ),
                  // ),
                  // Row(
                  //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  //   children: [
                  //     IconButton(
                  //       padding: EdgeInsets.zero,
                  //       onPressed: () {},
                  //       icon: SvgPicture.asset(
                  //         "assets/icons/email_box.svg",
                  //         height: 64.r,
                  //         width: 64.r,
                  //       ),
                  //     ),
                  //     IconButton(
                  //       padding: EdgeInsets.zero,
                  //       onPressed: () {},
                  //       icon: SvgPicture.asset(
                  //         "assets/icons/google_box.svg",
                  //         height: 64.r,
                  //         width: 64.r,
                  //       ),
                  //     ),
                  //     IconButton(
                  //       padding: EdgeInsets.zero,
                  //       onPressed: () {},
                  //       icon: SvgPicture.asset(
                  //         "assets/icons/apple_box.svg",
                  //         height: 64.r,
                  //         width: 64.r,
                  //       ),
                  //     ),
                  //   ],
                  // )
                ],
              ),
            ),
          ),
        ),
      );
    }),
  ).then(isClosed);
}
