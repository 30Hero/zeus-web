package vn.zeus.web.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import vn.zeus.web.config.AppProperties;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private final AppProperties appProperties;

	public TokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public AuthToken createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		String token = Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
				.compact();

		AuthToken authToken = new AuthToken();
		authToken.setToken(token);
		authToken.setCreated(now);
		authToken.setExpiration(expiryDate);
		return authToken;
	}

	public AuthToken createToken(Integer userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		String token = Jwts.builder().setSubject(Long.toString(userId)).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
				.compact();

		AuthToken authToken = new AuthToken();
		authToken.setToken(token);
		authToken.setCreated(now);
		authToken.setExpiration(expiryDate);
		return authToken;
	}

	public Integer getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token)
				.getBody();

		return Integer.parseInt(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.warn("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.warn("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.warn("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.warn("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.warn("JWT claims string is empty.");
		} catch (Exception ex) {
			logger.warn("Unknown error");
		}
		return false;
	}

}
