package user.servlet;

import user.dao.UserDao;
import user.dao.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        /* 获取用户名和密码和验证码*/
        String u_username = request.getParameter("u_username");
        String u_password = request.getParameter("u_password");
        //验证码
        String checkcode = request.getParameter("checkcode");


        /*判断是否是免登录*/
        String remember = request.getParameter("remember");

        //获取验证码
        HttpSession session = request.getSession();
        String vecode = (String) session.getAttribute("vecode");

        UserDao userDao = new UserDaoImpl();
        boolean login = userDao.login(u_username, u_password);
        if (checkcode.equals(vecode)) {
            if (login) {
                request.getSession().setAttribute("user", u_username);
                if (remember != null) {
                    Cookie cookie = new Cookie("u_username", u_username);
                    cookie.setMaxAge(60*60*24);
                    response.addCookie(cookie);
                }
                response.sendRedirect("/goodsListServlet?method=findByPageMVC");
            } else {
                request.setAttribute("er", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "对不起，你输入的验证码有误，无法验证");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);

    }
}
