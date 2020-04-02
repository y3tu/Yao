package com.y3tu.yao.auth.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y3tu.tool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;

/**
 * 手机登录成功处理
 *
 * @author y3tu
 */
@Slf4j
@Component
public class MobileAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private DefaultTokenServices tokenServices;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        if (header == null && !header.startsWith("Basic")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String tmp = header.substring(6);
        String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));

        String[] clientArrays = defaultClientDetails.split(":");
        String clientId = clientArrays[0].trim();
        String clientSecret = clientArrays[1].trim();

        if (clientId == null) {
            throw new BadCredentialsException("No client credentials presented");
        }

        if (clientSecret == null) {
            clientSecret = "";
        }

        clientId = clientId.trim();
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new InvalidClientException("Client do not exist");
        }
        if (StrUtil.isEmpty(clientDetails.getClientSecret()) || clientDetails.getClientSecret().equalsIgnoreCase(clientSecret)) {
            throw new InvalidClientException("Given client ID does not match authenticated client");
        }

        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "mobile");
        new DefaultOAuth2RequestValidator().validateScope(tokenRequest, clientDetails);

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken auth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
        log.info("登录成功 token: {}", auth2AccessToken.getValue());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(auth2AccessToken));

    }
}
