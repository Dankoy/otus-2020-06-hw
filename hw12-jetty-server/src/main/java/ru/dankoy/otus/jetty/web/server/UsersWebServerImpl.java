package ru.dankoy.otus.jetty.web.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.dankoy.otus.jetty.core.dao.UserDao;
import ru.dankoy.otus.jetty.hibernate.sessionmanager.SessionManagerHibernate;
import ru.dankoy.otus.jetty.service.FileSystemHelper;
import ru.dankoy.otus.jetty.service.TemplateProcessor;
import ru.dankoy.otus.jetty.web.servlet.AllUsersApiServlet;
import ru.dankoy.otus.jetty.web.servlet.UsersApiServlet;
import ru.dankoy.otus.jetty.web.servlet.UsersServlet;

public class UsersWebServerImpl implements UsersWebServer {

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final UserDao userDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final SessionManagerHibernate sessionManagerHibernate;

    public UsersWebServerImpl(int port, UserDao userDao, Gson gson, TemplateProcessor templateProcessor,
            SessionManagerHibernate sessionManagerHibernate) {
        this.userDao = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        this.server = new Server(port);
        this.sessionManagerHibernate = sessionManagerHibernate;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/api/user/*", "users", "/api/user"));


        server.setHandler(handlers);
        return server;

    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler
                .addServlet(new ServletHolder(new UsersServlet(templateProcessor, userDao, sessionManagerHibernate)),
                        "/users");
        servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(userDao, gson, sessionManagerHibernate)),
                "/api/user/*");
        servletContextHandler.addServlet(new ServletHolder(new AllUsersApiServlet(userDao, gson, sessionManagerHibernate)),
                "/api/user");
        return servletContextHandler;
    }

}
