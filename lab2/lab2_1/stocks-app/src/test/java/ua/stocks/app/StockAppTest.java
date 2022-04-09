package ua.stocks.app;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class StockAppTest 
{
    @Mock
    private IStockMarketService mockmarket;

    @InjectMocks
    private StocksPortfolio portfolio;

    @Test
    public void totalValueTest() {

        when(mockmarket.lookUpPrice("netflix")).thenReturn(2.5);
        when(mockmarket.lookUpPrice("amazon")).thenReturn(3.0);
        when(mockmarket.lookUpPrice("hbo")).thenReturn(1.0);

        portfolio.addStock(new Stock("netflix", 1));
        portfolio.addStock(new Stock("amazon", 2));
        portfolio.addStock(new Stock("hbo", 4));

        assertTrue(portfolio.getTotalValue() == 12.5);

        //hamcrest version
        assertThat(portfolio.getTotalValue(), is(12.5));
    }
}
