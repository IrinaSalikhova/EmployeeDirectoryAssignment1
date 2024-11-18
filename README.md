## Setup:
- no special setup needed, everything is ready. However, if emulator is used for running the app, to display a pin for « your » location in emulator you have to setup a location in emulator settings.

## Login:
- ken / 1234

## Features List:
1. Task Management:
  - Database-backed task storage with Room.
  - CRUD operations (Create, Read, Update, Delete) for tasks.
  - Tasks are pre-populated with sample data.
2. Google Maps Integration:
  - Displays user location and task locations on an interactive map.
  - Enables permission handling for fine location access.
3. Task Interaction:
  - Mark tasks as "Pending" or "Completed" directly from the map view.
  - Navigate back to task lists with status updates saved to the database.
4. User-Friendly Navigation:
  - Utilizes NavController for seamless screen transitions.
  - Includes buttons and top app bar for clear navigation cues.

## App Structure:
###  Database:
    - TaskDatabase: Singleton Room database instance with task management functionalities. Prepopulated database callback for sample data insertion.
    - TaskDao: Data Access Object interface for handling database queries.
    - Task data class.
###  UI:
    - MapView.kt: Displays map-based task interactions.
    - TaskList.kt: Placeholder for the task list screen.
    - LoginScreenIT.kt: Placeholder for the login screen.
###  Navigation:
    - appNavigation.kt: Centralizes navigation logic across the app.


## Contributions:
### Samuel:
1. Analyze: Analyze the documention and design all the UI. 
2. Tasks management: Set all the tasks and complete the tasks Division.
3. Coding: Complete the LoginScreen. The input value check, login verification logic. Login succesful then save userName to sharedPreferences("username"). 
4. Code Merging: Merge all the coding and check, confirm the Compiling is done.
5. Submission: Prepare the video.
### Hung: (TaskList.kt)
1. Data Loading: Successfully loaded the task data from the SQLite database.
2. Sorting Tasks: Implemented functionality to display the task list ordered by address.
3. Task Modification: Enabled users to modify task data by clicking on the checkbox next to each task, which toggles the completion status and updates the database accordingly.
4. Navigation to Address Page: Integrated a clickable address link for each task that redirects users to map details associated with each task.
5. Logout button clean SharedPreferences("username") when clicked and redirect to Login page.
### Mahima:
### Irina:
  1. Project Structure: Designed and organized the app's structure, creating packages for database (ITRoomDB) and IT-related screens (ITSupport).
  2. Google Maps Setup: Configured the app to use the Google Maps SDK, set up API integration in AndroidManifest.xml and build.gradle.
  3. Database Development: Implemented the TaskDatabase class with Room's singleton pattern, designed the Task entity for task data storage, developed TaskDao with CRUD and task retrieval queries; added a prepopulation mechanism for sample tasks during the database initialization.
  4. MapView Implementation: Developed MapView.kt, integrating Google Maps to display user and task locations, implemented dynamic marker interactions, including toggling task completion status, handled runtime permissions for location services, built functionality to sync task updates with the database in real time.
