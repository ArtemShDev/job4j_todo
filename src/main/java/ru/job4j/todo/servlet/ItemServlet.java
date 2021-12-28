package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.StoreHibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ItemServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String showAll = req.getParameter("showAll");
        String showByUser = req.getParameter("showByUser");
        String json = "";
        if (showByUser != null) {
            String email = ((User) req.getSession().getAttribute("user")).getEmail();
            json = GSON.toJson(StoreHibernate.instOf().findUserItems(email, showAll));
        } else {
            json = GSON.toJson(StoreHibernate.instOf().findItems(showAll));
        }
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String[] catIds = req.getParameterValues("catIds");
        String descItem = req.getParameter("descItem");
        Item item = new Item(descItem, false);
        item.addCategories(catIds);
        item.setUser((User) req.getSession().getAttribute("user"));
        StoreHibernate.instOf().add(item);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(item);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}