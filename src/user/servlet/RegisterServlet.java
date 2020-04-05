package user.servlet;

import user.dao.UserDao;
import user.dao.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String u_username = request.getParameter("u_username");
        String u_password = request.getParameter("u_password");
        String u_sex = request.getParameter("u_sex");
        String u_hobbies = request.getParameter("u_hobbies");
        String u_phone = request.getParameter("u_phone");
        String u_email = request.getParameter("u_email");
        String u_address = request.getParameter("u_address");
        String is_delete = request.getParameter("is_delete");


        UserDao userDao = new UserDaoImpl();
        int rows = userDao.re(u_username, u_password, u_sex, u_hobbies, u_phone, u_email, u_address, is_delete);
        if (rows > 0) {
            request.getRequestDispatcher("/success.jsp").forward(request, response);
        } else {
            request.setAttribute("fail", "注册失败! 请从新开始注册");
            request.getRequestDispatcher("/register.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);

    }
}
