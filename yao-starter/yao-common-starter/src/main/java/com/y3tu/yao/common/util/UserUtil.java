package com.y3tu.yao.common.util;

import cn.hutool.core.codec.Base64;
import com.y3tu.yao.common.constant.AuthConstant;
import com.y3tu.yao.common.constant.UserConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关工具处理类
 *
 * @author y3tu
 * @date 2019-05-03
 */
@Slf4j
public class UserUtil {
    /**
     * 获取请求中的token
     *
     * @param request
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        String token = "";
        String authorization = request.getHeader(AuthConstant.TOKEN_HEADER);
        if (authorization != null) {
            token = authorization.split(" ")[1];
        }
        if (request.getParameter("access_token") != null) {
            token = request.getParameter("access_token");
        }

        log.info("获取token成功，值为{}", token);
        return token;
    }

    /**
     * 获取jwt中的claims信息
     *
     * @param token
     * @return claim
     */
    public static Claims getClaims(String token) {

        String key = Base64.encode(AuthConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 获取请求中的userId
     *
     * @param request
     * @return userId
     */
    public static String getUserId(HttpServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        Claims claims = getClaims(token);
        String userId = (String) claims.get(UserConstant.USER_ID);
        log.info("获取userId成功，值为", userId);
        return userId;
    }

    /**
     * 获取请求中的username
     *
     * @param request
     * @return userId
     */
    public static String getUserName(HttpServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        Claims claims = getClaims(token);
        String username = (String) claims.get(UserConstant.USER_NAME);
        log.info("获取username成功，值为", username);
        return username;
    }

    /**
     * 获取请求中的roles集合
     *
     * @param request
     * @return roles
     */
    public static List<String> getRoles(HttpServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        Claims claims = getClaims(token);
        List<String> roles = (List<String>) claims.get(UserConstant.AUTHORITIES);
        return roles;
    }
}
