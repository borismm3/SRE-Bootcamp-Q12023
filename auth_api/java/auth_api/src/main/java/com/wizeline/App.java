package com.wizeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static com.wizeline.JsonUtil.json;
import static spark.Spark.*;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws SQLException {
        log.info("Listening on: http://localhost:8000/");

        port(8000);
        get("/", App::routeRoot);
        get("/_health", App::routeRoot);
        post("/login", App::urlLogin, json());
        get("/protected", App::protect, json());
    }

    public static Object routeRoot(spark.Request req, spark.Response res) throws Exception {
        MysqlDBClient mysqlDBClient = MysqlDBClient.getInstance();
        if (!mysqlDBClient.exceptionThrown) return "OK";
        res.status(500);
        return "Application is running but database is not accessible";
    }

    public static Object urlLogin(spark.Request req, spark.Response res) throws Exception {
        String username = req.queryParams("username");
        String password = req.queryParams("password");
        String methodGenerateToken = Methods.generateToken(username, password);
        Response r = new Response(methodGenerateToken);
        res.type("application/json");
        if (methodGenerateToken.contains("ERROR")) res.status(403);
        return r;
    }

    public static Object protect(spark.Request req, spark.Response res) throws Exception {
        String authorization = req.headers("Authorization");
        String methodAccessData = Methods.accessData(authorization);
        //Response r = new Response(Methods.accessData(authorization));
        Response r = new Response(methodAccessData);
        res.type("application/json");
        if (methodAccessData.contains("ERROR")) res.status(403);
        return r;
    }
}
