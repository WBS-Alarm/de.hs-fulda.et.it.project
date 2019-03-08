package de.hsfulda.et.wbs.security.token;

import de.hsfulda.et.wbs.security.entity.TokenConfig;
import de.hsfulda.et.wbs.security.repository.TokenConfigRepository;
import de.hsfulda.et.wbs.util.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Service
final class JWTTokenService implements Clock, TokenService {

    private static final String DOT = ".";
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    private final Map<String, TokenConfig> configMap = new ConcurrentHashMap<>();
    private final TokenConfigRepository configRepository;
    private final String issuer;

    JWTTokenService(
        @Value("${jwt.issuer:wbs}") final String issuer,
        final TokenConfigRepository tokenConfigRepository) {
        super();

        this.issuer = issuer;
        this.configRepository = tokenConfigRepository;
    }

    @Override
    public String permanent(final Map<String, String> attributes) {
        return newToken(attributes, 0);
    }

    @Override
    public String expiring(final Map<String, String> attributes) {
        return newToken(attributes, getExpiresInSec());
    }

    private String newToken(final Map<String, String> attributes, final int expiresInSec) {
        final LocalDateTime now = LocalDateTime.now();
        final Claims claims = Jwts
            .claims()
            .setIssuer(issuer)
            .setIssuedAt(toDate(now));

        if (expiresInSec > 0) {
            Duration expires = Duration.of(getExpiresInSec(), ChronoUnit.SECONDS);
            final LocalDateTime expiresAt = now.plus(expires);
            claims.setExpiration(toDate(expiresAt));
        }
        claims.putAll(attributes);

        return Jwts
            .builder()
            .setClaims(claims)
            .signWith(HS256, getSecret())
            .compressWith(COMPRESSION_CODEC)
            .compact();
    }

    private Date toDate(LocalDateTime now) {
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Map<String, String> verify(final String token) {
        final JwtParser parser = Jwts
            .parser()
            .requireIssuer(issuer)
            .setClock(this)
            .setAllowedClockSkewSeconds(getClock())
            .setSigningKey(getSecret());
        return parseClaims(() -> parser.parseClaimsJws(token).getBody());
    }

    @Override
    public Map<String, String> untrusted(final String token) {
        final JwtParser parser = Jwts
            .parser()
            .requireIssuer(issuer)
            .setClock(this)
            .setAllowedClockSkewSeconds(getClock());

        // See: https://github.com/jwtk/jjwt/issues/135
        final String withoutSignature = substringBeforeLast(token, DOT) + DOT;
        return parseClaims(() -> parser.parseClaimsJwt(withoutSignature).getBody());
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        try {
            final Claims claims = toClaims.get();
            final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            for (final Map.Entry<String, Object> e : claims.entrySet()) {
                builder.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return builder.build();
        } catch (final IllegalArgumentException | JwtException e) {
            return ImmutableMap.of();
        }
    }

    @Override
    public Date now() {
        return toDate(LocalDateTime.now());
    }

    private TokenConfig getTokenConfig() {
        if (!configMap.containsKey(issuer)) {
            Optional<TokenConfig> config = configRepository.findById(issuer);
            config.ifPresent(tc -> configMap.put(issuer, tc));
        }
        return configMap.get(issuer);
    }

    private String getSecret() {
        TokenConfig config = getTokenConfig();
        return BASE64.encode(requireNonNull(config.getSecret()));
    }

    private int getExpiresInSec() {
        TokenConfig config = getTokenConfig();
        return config.getExpiration();
    }

    private int getClock() {
        TokenConfig config = getTokenConfig();
        return config.getClock();
    }

}