# DriveSafe Desktop App

The DriveSafe Desktop App is designed to help users query and visualize data related to driving hazards. With DriveSafe Desktop, users can execute SQL statements to retrieve data from a MySQL database, view the results in a table, and generate an interactive map of hazard locations.

## Features

- Execute SQL statements: Users can enter SQL statements to retrieve data from a MySQL database.
- Display query results: Users can view the results of their SQL statements in a table.
- Generate a hazard map: Users can generate an interactive map of hazard locations.

## How to Use

1. Clone this repository to your local machine.
2. Install Java SE Development Kit (JDK) version 8 or later.
3. Install MySQL Server and create a database called "drive_safe".
4. Import the data from the `drive_safe.sql` file into the database.
5. Compile the `drivesafe_desktop.java` file by running the command `javac drivesafe_desktop.java`.
6. Run the `drivesafe_desktop` class by running the command `java drivesafe_desktop`.
7. Enter an SQL statement in the text field and click "Query" to view the results in a table.
8. Click "Show Map" to generate an interactive map of hazard locations.

## Technologies Used

- Java
- MySQL
- Swing
- JSON

## Contributors

- Dhruv Parikh
- Prince Osholaja
