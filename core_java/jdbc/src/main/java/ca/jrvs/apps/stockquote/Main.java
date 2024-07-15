package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.*;
import okhttp3.OkHttpClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                properties.put(tokens[0], tokens[1]);
            }
            LOGGER.info("Properties file loaded successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("Properties file not found.", e);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error opening properties file.", e);
        }

        try {
            Class.forName(properties.get("db-class"));
            LOGGER.info("Database driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("Database driver not found.", e);
        }
        OkHttpClient client = new OkHttpClient();
        String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
        try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
            LOGGER.info("Connected to the database.");
            QuoteDao qRepo = new QuoteDao(c);
            PositionDao pRepo = new PositionDao(c);
            QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"));
            QuoteService sQuote = new QuoteService(qRepo, rcon);
            PositionService sPos = new PositionService(pRepo);
            StockQuoteController con = new StockQuoteController(sQuote, sPos);
            con.initClient();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error connecting to the database.", e);
        }
    }

}