package user.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String requestURI = request.getRequestURI();
        //遇到login.jsp或index.jsp或register.jsp或loginServlet或veCoServlet就进行放行
        if (requestURI.contains("login.jsp") || requestURI.contains("index.jsp") || requestURI.contains("register.jsp")
                || requestURI.contains("/loginServlet") || requestURI.contains("/veCodeServlet")) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("user");
                    if (name != null) {
                            chain.doFilter(request, response);
                        } else {
                            request.setAttribute("msg", "你还没有登录，请亲先登录！");
                            request.getRequestDispatcher("/index.jsp").forward(request, response);
                        }
                    }

                }
    public void destroy() {
    }


}
