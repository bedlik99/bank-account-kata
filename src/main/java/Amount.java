import java.math.BigDecimal;
import java.math.RoundingMode;


record Amount(BigDecimal value, CurrencyEnum currency) {

  Amount(BigDecimal value, CurrencyEnum currency) {
    this.value = value.setScale(2, RoundingMode.HALF_UP);
    this.currency = currency;
  }

  static Amount zero(CurrencyEnum currency) {
    return new Amount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), currency);
  }

}
