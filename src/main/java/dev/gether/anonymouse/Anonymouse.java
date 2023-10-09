package dev.gether.anonymouse;

import dev.gether.anonymouse.rest.DemoRestController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.TimeUnit;

public class Anonymouse extends JavaPlugin {

    private Server server;
    @Override
    public void onEnable() {
        // config.yml
        saveDefaultConfig();

        // init rest api
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                initRestApi();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {

        try {
            if (server != null && server.isRunning()) {
                server.stop();
                getLogger().info("Server stopped!");
            }
        } catch (Exception ignored) {}
    }

    private void initRestApi() throws Exception {
        // creating server with port
        server = new Server();

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setIdleTimeout(TimeUnit.HOURS.toMillis(1));
        serverConnector.setHost(getConfig().getString("server.host", "127.0.0.1"));
        serverConnector.setPort(getConfig().getInt("server.port", 8080));
        server.addConnector(serverConnector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);


        // routes
        ServletHolder servletHolder = new ServletHolder(new DemoRestController());
        context.addServlet(servletHolder, "/api/*");

        server.setHandler(context);

        server.start();
        server.join();



    }

}
