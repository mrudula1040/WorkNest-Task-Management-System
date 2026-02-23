# 🚀 WorkNest – Role-Based Task Management System #

WorkNest is a Role-Based Task Management System built using Spring Boot.
It allows organizations to manage tasks efficiently across different roles such as Admin, Manager, HR, and Employee.

# 🛠️ Tech Stack

Java

Spring Boot

Spring MVC

Thymeleaf

MySQL

Spring Data JPA

Maven

# 📌 Project Overview

This project simulates a real-world company task management system where different roles have different responsibilities and access levels.

Each user sees a dashboard based on their role.

# 👥 Roles & Functionalities #
 👨‍💼 Admin

View total users and tasks

Create, update, and delete tasks

Assign tasks to employees

View recent and overdue tasks

👨‍💻 Manager

View assigned team members

Monitor team task progress

Track task status

 🧑‍💼 HR

View all employees

Assign managers to employees

Manage employee structure

👩‍💻 Employee

View assigned tasks

Update task status (To Do, In Progress, Done)

Track deadlines and priorities

# ✨ Key Features 

Role-Based Access System

Task Creation & Assignment

Task Status Tracking

Priority Management (Low, Medium, High)

Overdue Task Detection

Search & Filtering

Dashboard with Task Statistics

Layered Architecture (Controller – Service – Repository)

JPA Relationships

# 🗃️ Database Entities #

# User

id

name

email

password

role

manager

# Task

id

title

description

status

priority

dueDate

assignedUser

# 📚 Learning Purpose #

Through this project, I gained hands-on experience in:
Designing a role-based system
Implementing layered architecture
Working with database relationships using JPA
Building dashboards with business logic
I am currently learning Spring Security , and I plan to enhance this project further with improved authentication and authorization.
