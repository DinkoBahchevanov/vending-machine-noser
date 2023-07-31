package vendingmachine.app.model.paymentItem;

import java.math.BigDecimal;

public enum PaymentItemValue {

    TEN_ST(BigDecimal.valueOf(0.10)),
    TWENTY_ST(BigDecimal.valueOf(0.20)),
    FIFTY_ST(BigDecimal.valueOf(0.50)),
    LEV(BigDecimal.valueOf(1.0)),
    TWO_LEV(BigDecimal.valueOf(2.0));

    private final BigDecimal value;

    PaymentItemValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static PaymentItemValue fromBigDecimal(BigDecimal value) {
        for (PaymentItemValue itemValue : PaymentItemValue.values()) {
            if (itemValue.value.equals(value)) {
                return itemValue;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
