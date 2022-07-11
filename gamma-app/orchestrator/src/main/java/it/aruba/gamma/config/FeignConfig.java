package it.aruba.gamma.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Configuration
public class FeignConfig {


    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String CORRELATION_ID_HEADER_NAME = "correlation-id";

    @Bean
    public RequestInterceptor requestInterceptor(Environment env) {
        return requestTemplate -> {

            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return;
            }

            HttpServletRequest request = requestAttributes.getRequest();
            if (request == null) {
                return;
            }

            String jwt = request.getHeader(JWT_HEADER_NAME);

            requestTemplate.header("Content-Type", "application/json");

            if (jwt != null && !jwt.startsWith("Bearer")) {
                jwt = "Bearer " + jwt;
            }
            requestTemplate.header(JWT_HEADER_NAME, jwt);

            String correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME);
            if(correlationId != null) {
                requestTemplate.header(CORRELATION_ID_HEADER_NAME, correlationId);
            }


        };
    }

}



