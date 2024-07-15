package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionService;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;

public class StockQuoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteController.class);
    private QuoteService quoteService;
    private PositionService positionService;
    public StockQuoteController(QuoteService quoteService, PositionService positionService) {
        this.quoteService = quoteService;
        this.positionService = positionService;
    }
    /**
     * User interface for our application
     */
    public void initClient() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            // Display menu options
            System.out.println("Welcome to Stock Quote App!");
            System.out.println("1. View Stock Quote");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Read user input
            int choice = scanner.nextInt();

            // Handle user choice
            switch (choice) {
                case 1:
                    // Get Stock Quote
                    // TO DO: Implement logic to get stock quote
                    viewStock();
                    break;
                case 2:
                    // Buy Stock
                    // TO DO: Implement logic to buy stock
                    buyStock();
                    break;
                case 3:
                    // Sell Stock
                    // TO DO: Implement logic to sell stock
                    sellStock();
                    break;
                case 4:
                    // Exit
                    System.out.println("Exiting Stock Quote App. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        // Close scanner
        scanner.close();
    }

    private void viewStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stock symbol to view: ");
        String stock = scanner.nextLine();
        if (stock.isEmpty()) {
            System.out.println("Stock symbol cannot be empty. Please try again.");
            return;
        }
        Optional<Quote> quoteData = quoteService.fetchQuoteDataFromAPI(stock);
        if (!quoteData.isEmpty()) {
            //logger.info("Latest Stock Information: {}", quote);
            System.out.println("Latest Stock Information: ");
            System.out.println(quoteData);
        } else {
            //logger.warn("No quote found for symbol: {}", stock);
            System.out.println("No quote found for symbol: " + stock);
        }
    }

    private void buyStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stock symbol to buy: ");
        String stock = scanner.nextLine();
        if (stock.isEmpty()) {
            System.out.println("Stock symbol cannot be empty. Please try again.");
            return;
        }
        Optional<Quote> quoteData = quoteService.fetchQuoteDataFromAPI(stock);
        if (!quoteData.isEmpty()) {
            LOGGER.info("Latest Stock Information: {}", quoteData);
            System.out.println("Latest Stock Information: ");
            System.out.println(quoteData);
            System.out.println("Enter number of shares to buy: ");
            int numShares = scanner.nextInt();
            positionService.buy(stock, numShares, quoteData.get().getPrice());
            System.out.println(numShares + " shares of" + stock + " bought at " + quoteData.get().getPrice());
        }else{
            LOGGER.warn("No quote found for symbol: {}", stock);
            System.out.println("No quote found for symbol: " + stock);
        }

    }

    private void sellStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stock symbol to buy: ");
        String stock = scanner.nextLine();
        if (stock.isEmpty()) {
            System.out.println("Stock symbol cannot be empty. Please try again.");
            return;
        }
        Optional<Quote> quoteData = quoteService.fetchQuoteDataFromAPI(stock);
        if (!quoteData.isEmpty()) {
            LOGGER.info("Latest Stock Information: {}", quoteData);
            System.out.println("Latest Stock Information: ");
            System.out.println(quoteData);
            positionService.sell(stock);
            System.out.println("All shares of " + stock + " sold at " + quoteData.get().getPrice());
        }else{
            LOGGER.warn("No quote found for symbol: {}", stock);
            System.out.println("No quote found for symbol: " + stock);
        }
    }

}
