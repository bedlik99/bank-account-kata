import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;


record AccountStatement(AccountOperationEnum operation, LocalDate date, Amount amount, Amount balance) {

  static AccountStatement create(AccountOperationEnum operation, Amount amount, Amount balance) {
    return new AccountStatement(operation, Instant.now().atZone(ZoneOffset.UTC).toLocalDate(), amount, balance);
  }
}
