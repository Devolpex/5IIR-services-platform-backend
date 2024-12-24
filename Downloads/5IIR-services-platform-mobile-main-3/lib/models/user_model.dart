class User {
  final int id;
  final String email;
  final String role;
  final String token;
  final bool verified;

  User({
    required this.id,
    required this.email,
    required this.role,
    required this.token,
    required this.verified,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      email: json['email'],
      role: json['role'],
      token: json['token'],
      verified: json['verified'],
    );
  }

  static Map<String, dynamic> toJson(User user) {
    return {
      'id': user.id,
      'email': user.email,
      'role': user.role,
      'token': user.token,
      'verified': user.verified,
    };
  }

  @override
  String toString() {
    return 'User{id: $id, email: $email, role: $role, token: $token, verified: $verified}';
  }
}

