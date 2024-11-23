package web.security;

import function.factory.ArrayTabulatedFunctionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        // Инициализируем фабрику по умолчанию
        if (session.getAttribute("FACTORY_KEY") == null) {
            session.setAttribute("FACTORY_KEY", new ArrayTabulatedFunctionFactory());
        }

        // Продолжаем обработку после успешного логина
        response.sendRedirect("/");
    }
}
