package ua.stocks.app;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockMarketService stockmarket;

    public StocksPortfolio(IStockMarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0;

        for (Stock s: this.stocks) {
            total += stockmarket.lookUpPrice(s.getLabel()) * s.getQuantity();
        }

        return total;
    }
}
