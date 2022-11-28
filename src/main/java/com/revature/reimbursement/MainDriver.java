package com.revature.reimbursement;


import com.revature.reimbursement.utils.ConnectionFactory;
import com.revature.reimbursement.utils.Router;
import io.javalin.Javalin;

import java.sql.SQLException;

//can get rid of services/ handlers for statuses, types, and roles
public class MainDriver {

    public static void main(String[] args) throws SQLException {

        System.out.println(ConnectionFactory.getInstance().getConnection());

        Javalin app = Javalin.create(c -> {
            c.contextPath = "/reimbursement";
        }).start(8080);

        Router.router(app);
    }
}
