package com.cholog_ai.closet_saver.domain.user;


public class User {
    private final Long id;
    private final String email;
    private final String name;
    private final String password;

    public User(final Long id, final String email, final String name, final String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
