package com.mronfim.invoicer.security;

public abstract class JWTConstants {

    public static final int EXPIRATION_TIME = 600_000; // 10 mintues
    public static final String TOKEN_HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_SECRET = "MY_TOKEN_SECRET";
}