# In-Memory Database & CLI Task Manager

This project combines an interactive Command-Line Interface (CLI) application with a custom-built, generic **In-Memory Database**. It is designed to strictly follow Solid principles, specifically demonstrating advanced memory management, custom validation, and data serialization.

## 🚀 Key Features

*   **Custom In-Memory Database:** An abstract, reusable data layer that securely stores generic `Entity` objects. It assigns unique IDs and isolates the internal state from the application layer to prevent unintended memory modifications.
*   **Deep Object Copying:** The database enforces strict deep copying via an abstract `copy()` method. This guarantees that entities retrieved from the database are isolated instances, mitigating the risks associated with "pass-by-reference" mutations.
*   **Entity Validation Engine:** Implements a dynamic `Validator` interface. The database utilizes a `HashMap` registry to map entity codes to their specific validators, dynamically checking business constraints before persistence (e.g., non-null titles, valid ID references).
*   **Trackable Entities:** Employs a `Trackable` interface to automatically manage metadata. Entities implementing this interface have their `CreationDate` and `LastModificationDate` automatically managed by the database during `add` and `update` operations.
*   **Smart Business Logic (Services):** The application logic is completely decoupled from the data objects. Services like `TaskService` and `StepService` handle the complex cascade operations:
    *   *Automatic State Propagation:* If a Task is marked as `Completed`, all its Steps automatically complete. 
    *   *State Aggregation:* If a Step completes, its parent Task updates from `NotStarted` to `InProgress`. If all steps finish, the parent Task automatically marks itself as `Completed`.
    *   *Cascade Deletion:* Deleting a Task safely cleans up all associated Steps.
*   **Data Persistence (Serialization):** Transforms the temporary in-memory database into a persistent storage system using a custom `Serializer` interface. It serializes entities to a `db.txt` file and deserializes them on startup, ensuring no data loss between sessions.

## 🛠️ Technical Stack & Architecture

*   **Language:** Java
*   **Core Concepts:**
    *   Abstract Classes & Interfaces (`Validator`, `Trackable`, `Serializer`)
    *   Dynamic Dispatch & Instance Type Checking (`instanceof`)
    *   Memory Management (Heap Allocation, Deep Copying)
    *   Custom Exception Handling (Checked/Unchecked: `InvalidEntityException`, `EntityNotFoundException`)
    *   File I/O (`db.txt` interactions)
*   **Data Structures:** `HashMap`, `ArrayList`.
*   **Architecture Pattern:** Service-Oriented (Data Layer -> Validation Layer -> Service Layer -> CLI Presentation Layer).

## 💻 CLI Commands

The interactive terminal supports comprehensive CRUD operations:

*   **Create:** `add task`, `add step`
*   **Read:** `get task-by-id`, `get all-tasks` (Sorted by Due Date), `get incomplete-tasks`
*   **Update:** `update task`, `update step`
*   **Delete:** `delete`
*   **Persistence:** `save` (commits to `db.txt`), `exit`
