import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Account {
  private final List<AccountStatement> accountStatements;
  private Amount balance;

  Account(List<AccountStatement> accountStatements, Amount balance) {
    this.accountStatements = accountStatements;
    this.balance = balance;
  }

  static Account createAccount() {
    return new Account(new ArrayList<>(), new Amount(BigDecimal.ZERO, CurrencyEnum.EURO));
  }

  Amount getBalance() {
    return balance;
  }

  List<AccountStatement> getAccountStatements() {
    return accountStatements;
  }

  void printStatements() {
    accountStatements.forEach(System.out::println);
  }

  void deposit(Amount amount) {
    BigDecimal multiplier = getCurrencyMultiplier(amount.currency());
    this.balance = new Amount(this.balance.value().add(amount.value().multiply(multiplier)), this.balance.currency());
    accountStatements.add(AccountStatement.create(AccountOperationEnum.DEPOSIT, amount, this.balance));
  }

  Amount withdraw(Amount amount) {
    BigDecimal currencyMultiplier = getCurrencyMultiplier(amount.currency());
    Amount balanceAfterWithdrawal = new Amount(this.balance.value().subtract(amount.value().multiply(currencyMultiplier)), this.balance.currency());
    if (balanceAfterWithdrawal.value().compareTo(BigDecimal.ZERO) <= 0) {
      return Amount.zero(amount.currency());
    }
    this.balance = balanceAfterWithdrawal;
    accountStatements.add(AccountStatement.create(AccountOperationEnum.WITHDRAWAL, amount, this.balance));
    return amount;
  }

  private BigDecimal getCurrencyMultiplier(CurrencyEnum providedAmountCurrency) {
    if (this.balance.currency().equals(providedAmountCurrency)) {
      return BigDecimal.ONE;
    }
    return BigDecimal.valueOf(1); // for multiple currencies system retrieve currency multiplier
  }

}
