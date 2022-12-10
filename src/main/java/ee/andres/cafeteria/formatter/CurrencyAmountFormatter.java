package ee.andres.cafeteria.formatter;

import org.springframework.stereotype.Component;

@Component
public class CurrencyAmountFormatter {
    private static final Integer AMOUNT_MULTIPLIER = 100;

    public long format(Double d) {
        Double multiplied = d * AMOUNT_MULTIPLIER;
        return multiplied.longValue();
    }
}
