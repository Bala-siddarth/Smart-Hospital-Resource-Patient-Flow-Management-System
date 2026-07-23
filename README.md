# Smart Hospital Resource & Patient Flow Management System

A Java console-based Hospital Management System designed to optimize patient flow, automate doctor allocation, manage emergency admissions, and streamline hospital resource utilization using Object-Oriented Programming principles and Java Collections.

---

##  Project Overview

This project simulates the workflow of a modern hospital by managing patients, doctors, wards, appointments, billing, and emergency admissions. The primary focus is on efficient resource allocation and maintaining a structured patient flow throughout the hospital.

Unlike a basic CRUD application, this system incorporates real-world business rules such as specialization-based doctor assignment, emergency prioritization, ward capacity management, and workload balancing.

---

##  Features

###  Patient Management

* Register new patients
* Search patients by ID
* Update patient details
* Prevent duplicate phone number registration
* Input validation using custom exceptions

###  Intelligent Doctor Assignment

* Automatic doctor allocation during admission
* Matches patient's required specialization
* Assigns the least-loaded available doctor
* Tracks doctor workload dynamically

###  Emergency Management

* Priority Queue implementation
* Critical patients admitted before lower priority patients
* Emergency waiting status management

###  Ward Management

* Automatic ward allocation
* ICU allocation for emergency patients
* General ward allocation for regular patients
* Ward transfer functionality
* Bed occupancy tracking

###  Appointment Scheduling

* Appointment booking for admitted patients
* Doctor schedule conflict detection
* Patient schedule conflict detection
* Working-hour validation

###  Billing

* Automatic bill generation
* Ward-based billing
* Emergency charges
* Duplicate bill prevention

###  Reports

* All Patients
* Admitted Patients
* Doctors
* Ward Status
* Appointments
* Bills

---

#  System Architecture

```text
                Main
                  │
                  ▼
          HospitalManager
                  │
                  ▼
          HospitalService
                  │
      ┌───────────┼────────────┐
      ▼           ▼            ▼
   Patient      Doctor       Ward
      ▼           ▼            ▼
 Appointment    Bill       Collections
```

The project follows a layered architecture:

* **Manager Layer** → Handles user interaction.
* **Service Layer** → Contains business logic.
* **Entity Layer** → Represents system models.
* **Utility Layer** → ID generation and helper methods.

---

#  Core Concepts Used

* Object-Oriented Programming (OOP)
* Encapsulation
* Abstraction
* Enums
* Custom Exception Handling
* Layered Architecture
* Java Collections Framework

---

#  Collections Used

| Collection    | Purpose                             |
| ------------- | ----------------------------------- |
| HashMap       | Fast patient lookup (O(1))          |
| ArrayList     | Doctors, Bills, Appointments, Wards |
| PriorityQueue | Emergency patient prioritization    |

---

#  Business Rules

* Duplicate phone numbers are not allowed.
* Only available doctors can be assigned.
* Doctor specialization must match patient requirements.
* Least-loaded doctor is selected automatically.
* Emergency patients receive ICU allocation.
* Ward capacity cannot exceed available beds.
* Appointment conflicts are prevented.
* Bills cannot be generated twice.
* Patients cannot be discharged without bill generation.

---

#  Technologies Used

* Java
* Java Collections Framework
* Object-Oriented Programming
* IntelliJ IDEA

---

#  Project Structure

```text
src
│
├── entities
├── enums
├── exceptions
├── manager
├── service
├── util
└── Main.java
```

---

#  Future Enhancements

* JDBC & MySQL Integration
* Admin Login
* Doctor Management Module
* Patient Medical History
* Bill Payment Module
* Outpatient Appointment System
* GUI using JavaFX
* REST API using Spring Boot

---

#  Learning Outcomes

This project helped strengthen practical knowledge of:

* Object-Oriented Design
* Layered Application Architecture
* Business Logic Implementation
* Data Structures in Real Applications
* Exception Handling
* Java Collections
* Resource Management Algorithms

---

##  Author

**Bala Siddarth**

Biomedical Engineering Student

Java | Object-Oriented Programming | Problem Solving | Software Development

---

 If you found this project useful, consider giving it a star!
