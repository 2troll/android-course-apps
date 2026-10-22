package com.tonyhanma.lastproject;

/** Datos de la cuenta. La contraseña nunca se guarda en claro, solo su hash. */
final class User {

    final String name;
    final String username;
    final String email;
    final String birthDate;
    final String phone;
    final String gender;

    User(String name, String username, String email, String birthDate, String phone, String gender) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
        this.gender = gender;
    }
}
