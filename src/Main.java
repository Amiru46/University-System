import java.io.*;
import java.util.*;
import java.util.List;


public class Main {
    // Total number of seats available
    private static final int AllSeats = 100;
    private static int availableSeats = AllSeats;
    // Array to store student objects
    public static Student[] students = new Student[AllSeats];
    // Count of registered students
    private static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main program loop

        while (true) {
            showMenu();

            try {
                String choice = scanner.nextLine();
                // Handle user menu choices

                if (choice.equals("1")) {
                    checkAvailableSeats();
                } else if (choice.equals("2")) {
                    registerStudent(scanner);
                } else if (choice.equals("3")) {
                    deleteStudent(scanner);
                } else if (choice.equals("4")) {
                    findStudentById(scanner);
                } else if (choice.equals("5")) {
                    storeStudentDetails();
                } else if (choice.equals("6")) {
                    loadStudentDetails();
                } else if (choice.equals("7")) {
                    viewSortedStudents();
                } else if (choice.equals("8")) {
                    additionalControls_A(scanner);
                } else if (choice.equals("0")) {
                    System.out.println("Exiting the system.");
                    System.exit(0);
                } else if (choice.equals("9")) {
                    additionalControls_B(scanner);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
    // Displays the main menu options

        private static void showMenu() {
            System.out.println("\n--- University System Menu ---");
            System.out.println("1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Additional controls part A");
            System.out.println("9. Additional controls part B");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
        }

    // Display the number of available seats

        private static void checkAvailableSeats() {

            System.out.println("Available seats: " + availableSeats);
        }
    // Validate student ID format
        private static boolean isValidStudentId(String id) {

            return id.matches("w\\d{7}");
        }

        private static boolean DupStudentId(String id) {
            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null && students[i].getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }
    // Register a new student

        private static void registerStudent(Scanner scanner) {
            if (availableSeats <= 0) {
                System.out.println("Sorry! No available seats.");
                return;
            }
            String id;
            while (true) {
                System.out.print("Enter student ID (must start with 'w' and be 8 characters long): ");
                id = scanner.nextLine().toLowerCase();
                if (isValidStudentId(id) && !DupStudentId(id)) {
                    break;
                } else {
                    System.out.println("Invalid or duplicate student ID. Please try again.");
                }
            }
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            students[studentCount++] = new Student(id, name);
            availableSeats--;
            System.out.println("Student registered successfully.");
        }

    // Find a student by ID

        private static void findStudentById(Scanner scanner) {
            System.out.print("Enter student ID to find: ");
            String id = scanner.nextLine();
            boolean found = false;
            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null && students[i].getId().equals(id)) {
                    System.out.println("Student found:");
                    System.out.println(students[i]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with ID " + id + " not found.");
            }
        }
    // Delete a student by ID

        private static void deleteStudent(Scanner scanner) {
            System.out.print("Enter student ID to delete: ");
            String idToDelete = scanner.nextLine();
            String name = "name";
            boolean studentFound = false;
            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null && students[i].getId().equals(idToDelete)) {

                    for (int j = i; j < studentCount - 1; j++) {
                        students[j] = students[j + 1];
                    }
                    students[--studentCount] = null;
                    availableSeats++;
                    System.out.println("Student with ID " + idToDelete + " deleted successfully.");
                    studentFound = true;
                    break;
                }
            }
            if (!studentFound) {
                System.out.println("Student with ID " + idToDelete + " not found.");
            }
        }
    // Store student details into a file

    public static void storeStudentDetails() {
        try {
            FileWriter writer = new FileWriter("students.txt");

            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];

                if (student != null) {
                    String studentData = student.getId() + "," +
                            student.getName() + "," +
                            student.getModuleMarks()[0] + "," +
                            student.getModuleMarks()[1] + "," +
                            student.getModuleMarks()[2] + System.lineSeparator();
                    writer.write(studentData);
                }
            }

            writer.close();
            System.out.println("Student details stored successfully.");

        } catch (IOException e) {
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }
    // Load student details from a file
    private static void loadStudentDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String id = data[0];
                String name = data[1];
                int mark1 = Integer.parseInt(data[2]);
                int mark2 = Integer.parseInt(data[3]);
                int mark3 = Integer.parseInt(data[4]);

                Student student = new Student(id, name);
                student.setModuleMarks(mark1, mark2, mark3);

                students[studentCount++] = student;
                availableSeats--;
            }

            System.out.println("Student details loaded successfully.");

        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }



    // View the list of students sorted by name

        private static void viewSortedStudents() {
            if (studentCount == 0) {
                System.out.println("Students are not registered yet.");
                return;
            }

            List<Student> sortedStudents = new ArrayList<>();
            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null) {
                    sortedStudents.add(students[i]);
                }
            }
            sortedStudents.sort(Comparator.comparing(Student::getName));
            System.out.println("Students sorted by name:");
            for (Student student : sortedStudents) {
                System.out.println(student);
            }
        }
    // Display the submenu for additional controls part A

        private static void additionalControls_A(Scanner scanner) {
            while (true) {
                SubMenu_A();
                String choice = scanner.nextLine();
                try {
                    if (choice.equals("1")) {
                        addStudentName(scanner);
                    } else if (choice.equals("2")) {
                        addModuleMarks(scanner);
                    } else if (choice.equals("3")) {
                        return;
                    } else {
                        throw new IllegalArgumentException("Invalid choice. Please try again.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    // Display the submenu for additional controls part B

    private static void additionalControls_B(Scanner scanner) {
        while (true) {
            SubMenu_B();
            String choice = scanner.nextLine();
            try {
                if (choice.equals("1")) {
                    getSummary();
                } else if (choice.equals("2")) {
                    getReport();
                } else if (choice.equals("3")) {
                    return;
                } else {
                    throw new IllegalArgumentException("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    // Display the submenu options for additional controls part B

        private static void SubMenu_B() {
            System.out.println("\n--- Additional Controls B ---");
            System.out.println("1. Generate Summary");
            System.out.println("2. Generate Report");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");
        }
    // Display the submenu options for additional controls part A

        private static void SubMenu_A() {
            System.out.println("\n--- Additional Controls A ---");
            System.out.println("1. Add student name");
            System.out.println("2. Add module marks");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");
        }
    // Add or update student name

        private static void addStudentName(Scanner scanner) {
            //System.out.println(students);
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine();
            for (Student student : students) {
                if (student != null && student.getId().equals(id)) {
                    System.out.print("Enter new student name: ");
                    String name = scanner.nextLine();
                    student.setName(name);

                    System.out.println("Student name updated successfully.");
                    return;
                }
            }
            System.out.println("Student not found!!!");
        }
    // Add or update module marks for a student

        private static void addModuleMarks(Scanner scanner) {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine();
            //System.out.println("Student name is");

            for (Student student : students) {
                if (student != null && student.getId().equals(id)) {
                    System.out.print("Enter marks for Module 1: ");
                    int mark1 = scanner.nextInt();
                    System.out.print("Enter marks for Module 2: ");
                    int mark2 = scanner.nextInt();
                    System.out.print("Enter marks for Module 3: ");
                    int mark3 = scanner.nextInt();
                    scanner.nextLine();  // consume the newline
                    student.setModuleMarks(mark1, mark2, mark3);
                    System.out.println("Done! Module marks updated successfully.");
                    return;
                }
            }
            System.out.println("Student not found!!");
        }

    // Generate summary report
        private static void getSummary() {
            int totalRegistrations = studentCount;

            int studentsScoredMoreThan40 = 0;

            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null && students[i].PassedAllModules()) {
                    studentsScoredMoreThan40++;
                }
            }

            System.out.println("\n--- Summary Report ---");

            System.out.println("Total student registrations: " + totalRegistrations);

            System.out.println("Total Students who scored more than 40 marks in all modules: " + studentsScoredMoreThan40);
        }

    // Generate detailed report

        private static void getReport() {
            List<Student> studentList = new ArrayList<>();
            for (int i = 0; i < studentCount; i++) {
                if (students[i] != null) {
                    studentList.add(students[i]);
                }
            }

            bubsortbyAverage(studentList);

            System.out.println("\n--- Final Report ---");

            System.out.println("Name    \tID     \tModule 1\tModule 2\tModule 3\tTotal\tAverage\tGrade");

            for (Student student : studentList) {
                System.out.println(student.getReport());
            }
        }
    // Sort students by average marks using bubble sort

        private static void bubsortbyAverage(List<Student> studentList) {
            int n = studentList.size();

            boolean swapped;
            do {
                swapped = false;
                for (int i = 0; i < n - 1; i++) {
                    if (studentList.get(i).getAvMarks() < studentList.get(i + 1).getAvMarks()) {
                        Collections.swap(studentList, i, i + 1);
                        swapped = true;
                    }
                }
            } while (swapped);
        }
    }