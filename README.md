# iMoney Banking Application

A comprehensive personal finance management system built with pure Java, demonstrating advanced object-oriented programming principles including inheritance, polymorphism, encapsulation, and abstraction.

## Overview

iMoney is a banking application that helps users track expenses, manage budgets, monitor overdrafts, and work toward savings goals. The system automatically syncs with bank transactions and allows manual entry, providing real-time alerts when spending limits are exceeded.

## Features

### Core Functionality
- **Dual Transaction Types**: Supports both bank-synced and manually entered transactions
- **Budget Management**: Set weekly and monthly spending limits across multiple categories
- **Overdraft Monitoring**: Automatic calculation of overdraft charges (9p per £100 daily)
- **Savings Goals**: Track progress toward multiple financial goals with deadline management
- **Smart Notifications**: Real-time alerts for budget violations and account status changes
- **Spending Analytics**: Identify highest spending categories and get detailed breakdowns

### Transaction Categories
- Transportation
- Bills
- Food
- Shopping
- Other

## Technical Highlights

### Object-Oriented Design
- **Encapsulation**: All fields private with public getters/setters for controlled access
- **Inheritance**: Abstract Transaction class extended by SyncedTransaction and ManualTransaction
- **Polymorphism**: Runtime method selection for transaction display based on actual object type
- **Abstraction**: Abstract classes and interfaces define contracts without implementation details
- **Composition**: Manager pattern for separation of concerns

### Architecture
```
com.imoney
├── enums          # Type-safe constants (TransactionCategory, Priority, etc.)
├── interfaces     # Behavioral contracts (Notifiable, Calculable)
├── models         # Domain objects (Transaction, BankAccount, SavingGoal, etc.)
├── managers       # Business logic coordinators (ExpenseTracker, NotificationManager, etc.)
└── iMoneyApp      # Main application facade
```

### Data Storage
Uses ArrayList collections to simulate database storage, demonstrating collection framework proficiency without external dependencies.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal or Command Prompt

### Compilation

```bash
# Navigate to project directory
cd iMoney-Banking-App/src/main/java

# Compile all files
javac -encoding UTF-8 com/imoney/**/*.java

# Or compile from root
javac -encoding UTF-8 -d bin -sourcepath src/main/java src/main/java/com/imoney/iMoneyApp.java
```

### Running the Application

```bash
# From src/main/java directory
java com.imoney.iMoneyApp

# Or from bin directory (if compiled with -d bin)
java -cp bin com.imoney.iMoneyApp
```

## Usage Example

When you run the application, it automatically:

1. Initializes default spending limits based on common budget categories
2. Creates sample transactions to demonstrate functionality
3. Displays account summary with current balance and status
4. Shows all transactions with polymorphic display (manual vs synced transactions appear differently)
5. Generates spending summary by category
6. Identifies categories exceeding budget limits
7. Calculates and displays overdraft information if applicable
8. Shows progress toward savings goals
9. Displays all notifications and alerts

### Sample Output
```
======================================================================
                     iMONEY BANKING APP
======================================================================

Account Number     : 12345678
Account Holder     : John Doe
Current Balance    : £-70.00
Account Status     : OVERDRAWN

Food           : £220.00 / £200.00 (Monthly) EXCEEDED
Transportation : £170.00 / £160.00 (Monthly) EXCEEDED

HIGHEST SPENDING: Food (£220.00)

Overdraft Amount      : £70.00
Daily Charge          : £0.0630

SAVINGS GOALS
1. Holiday - Visit Turkey: £120.00 / £500.00 (24.0%)
2. Buying a New Car: £1200.00 / £8000.00 (15.0%)
3. Emergency Fund: £300.00 / £1000.00 (30.0%)
```

## Key Classes

### Transaction Hierarchy
- **Transaction** (Abstract): Base class for all transactions
- **SyncedTransaction**: Automatically imported from bank with transaction ID
- **ManualTransaction**: User-entered transactions with entry timestamp

### Manager Classes
- **ExpenseTracker**: Manages transactions and spending limits
- **NotificationManager**: Handles all system notifications
- **SavingsManager**: Tracks savings goals and progress
- **OverdraftCalculator**: Calculates overdraft charges based on exact formula

### Domain Models
- **BankAccount**: Manages account balance and overdraft facility
- **SpendingLimit**: Defines weekly/monthly spending thresholds per category
- **SavingGoal**: Tracks financial goals with target amounts and dates
- **Notification**: System alerts with priority levels

## Business Rules

### Spending Limits (Default Values)
| Category       | Weekly Min-Max | Monthly Min-Max |
|---------------|----------------|-----------------|
| Transportation| £33 - £40      | £132 - £160     |
| Bills         | £50 - £53      | £200 - £212     |
| Food          | £45 - £50      | £180 - £200     |
| Shopping      | £30 - £32      | £120 - £128     |

### Overdraft Calculation
- **Formula**: 9 pence per £100 borrowed, calculated daily
- **Example**: £70 overdraft = (70 / 100) × 0.09 = £0.063 per day
- **Weekly estimate**: Daily charge × 7
- **Monthly estimate**: Daily charge × 30

### Alert Triggers
- **Budget Warning**: When spending reaches 80% of limit
- **Budget Exceeded**: When spending surpasses 100% of limit
- **Overdraft Warning**: When balance goes negative
- **Overdraft Charge**: Daily when account remains overdrawn

## Project Structure

```
iMoney-Banking-App/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── imoney/
│                   ├── enums/
│                   │   ├── TransactionCategory.java
│                   │   ├── NotificationType.java
│                   │   ├── Priority.java
│                   │   └── BudgetStatus.java
│                   ├── interfaces/
│                   │   ├── Notifiable.java
│                   │   └── Calculable.java
│                   ├── models/
│                   │   ├── BaseEntity.java
│                   │   ├── Transaction.java
│                   │   ├── SyncedTransaction.java
│                   │   ├── ManualTransaction.java
│                   │   ├── BankAccount.java
│                   │   ├── SpendingLimit.java
│                   │   ├── SavingGoal.java
│                   │   └── Notification.java
│                   ├── managers/
│                   │   ├── ExpenseTracker.java
│                   │   ├── NotificationManager.java
│                   │   ├── SavingsManager.java
│                   │   └── OverdraftCalculator.java
│                   └── iMoneyApp.java
└── README.md
```

## Design Patterns

- **Facade Pattern**: iMoneyApp provides simplified interface to complex subsystems
- **Manager Pattern**: Separate managers for distinct business concerns
- **Template Method**: Abstract Transaction class defines skeleton, subclasses fill details
- **Strategy Pattern**: Different transaction types implement display strategy differently

## Learning Outcomes

This project demonstrates:
- Clean code principles and proper Java conventions
- Effective use of Java Collections Framework
- OOP principles applied to real-world problems
- Software architecture and design patterns
- Separation of concerns and single responsibility
- Defensive programming with input validation
- Code organization and package structure

## Customization

### Adding New Transaction Categories
1. Add enum value to `TransactionCategory.java`
2. Add corresponding spending limit in `ExpenseTracker.initializeDefaultSpendingLimits()`
3. Update display methods in `iMoneyApp.java` if needed

### Modifying Spending Limits
Edit the hardcoded values in `ExpenseTracker.initializeDefaultSpendingLimits()` method.

### Adding Savings Goals
Add new goals in `SavingsManager.initializeDefaultGoals()` with target amount, date, and frequency.

### Changing Overdraft Formula
Modify the `DAILY_RATE_PER_100` constant and calculation logic in `OverdraftCalculator.java`.

## Future Enhancements

- Database persistence (MySQL/PostgreSQL)
- REST API for mobile/web clients
- Data visualization and charts
- Multi-currency support
- Recurring transaction templates
- Export functionality (CSV/PDF)
- Multi-user support with authentication
- Budget recommendations using AI/ML

## Contributing

This is an educational project demonstrating OOP concepts. Feel free to fork and extend for your own learning purposes.

## License

This project is open source and available for educational purposes.

## Author

Built as part of an Object-Oriented Programming assignment to demonstrate advanced Java concepts and clean code principles.

## Acknowledgments

- Project structure follows standard Java package conventions
- Design inspired by modern banking applications
- Implements best practices from "Effective Java" by Joshua Bloch
