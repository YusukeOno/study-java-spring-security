package com.example.SecurityGetUser.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Component("LoggingInterceptor")
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    // USER_IDのキー名ß
    private static final String USER_ID = "USER_ID";

    // SESSION_IDのキー名
    private static final String SESSION_ID = "SESSION_ID";

    // コントローラー実行前に呼び出されるメソッド
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // ユーザー情報の取得
        Principal user = request.getUserPrincipal();

        // ユーザーIDの設定
        String userId = null;
        if (user != null) {
            userId = user.getName();
        }

        // ポイント2:MDC
        // ユーザーIDをMDCにセット
        if (userId != null && "".equals(userId) == false) {
            MDC.put(USER_ID, userId);
        } else {
            MDC.put(USER_ID, "");
        }

        // セッション情報の取得
        HttpSession session = request.getSession(false);
        if (session != null) {
            MDC.put(SESSION_ID, session.getId());
        }

        return true;
    }

    // コントローラー実行後に呼び出されるメソッド
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        // キーの削除
        MDC.remove(SESSION_ID);
        MDC.remove(USER_ID);
    }

    // リクエスト完了後に呼び出されるメソッド
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // 何もしない
    }
}
