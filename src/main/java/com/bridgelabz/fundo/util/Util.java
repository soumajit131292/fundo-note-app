package com.bridgelabz.fundo.util;

import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
@Configuration
public class Util {
	private static final String secret = "Soumajit";

	public static String generateToken(Integer Id) {
		int id=Id;
		return JWT.create().withClaim("Id", id).sign(Algorithm.HMAC512(secret));
	}

	public static Integer parseToken(String token) {
		return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("Id").asInt();
	}
}