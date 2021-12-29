package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.StoreHibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (StoreHibernate.instOf().findUser(email) == null) {
            User user = new User(name, email, password);
            HttpSession sc = req.getSession();
            if (StoreHibernate.instOf().addUser(user)) {
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
        } else {
            req.setAttribute("error", "This email already exist");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}