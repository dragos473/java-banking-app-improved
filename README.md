## Java Banking System – Advanced Features
This project extends the [Core Engine](https://github.com/dragos473/java-banking-app) to support **Business-Level Banking** and **Advanced Fintech Logic**. It prioritizes complex system interactions. While the first stage focused on transaction integrity, this stage introduces **complex business logic, tiered service plans, and role-based access control (RBAC).**
The primary goal of this phase was **architectural refactoring** to ensure the codebase can handle the increasing complexity of **modern fintech requirements**.

**🚀 Advanced Functionality**

Business Accounts & Role-Based Access (RBAC): Implementation of shared business accounts with distinct permissions for Owners, Managers, and Employees.

**Dynamic Service Plans:** A tiered plan system (Standard, Student, Silver, Gold) that adjusts transaction fees and automates upgrades based on user spending habits.

**Complex Cashback Strategies:** High-performance logic to calculate rewards based on transaction frequency and spending thresholds.

**Interactive Split Payments:** A "Custom Split" mechanism requiring a consensus flow (Accept/Reject) among multiple participants.

**System Refactoring:** Extensive use of Design Patterns to manage growing complexity.

**📐 Architectural Evolution:**

To manage the system's growth, I implemented several Behavioral and Creational Design Patterns:

**Command Pattern:** Encapsulated financial operations within an Action interface, allowing for a decoupled and extensible execution flow for different banking commands.

**Factory Method Pattern:** Utilized AccountFactory and CardFactory to centrally manage the instantiation of diverse entities (Classic, Savings, Business), ensuring consistent object creation across the system.

**Observer Pattern:** Implemented a Cashback engine using a subscription-based model (subscribe/notify) to update account balances based on real-time transaction events.

**Singleton Pattern:** Used for the central Bank and Output managers to maintain a single source of truth for the system's state and registry.

🛠 Technical Stack
Language: Java 21

Data Processing: JSON(Jackson) for structured I/O.

Design Focus: Scalability, Refactoring, and Enterprise-grade Logic.
