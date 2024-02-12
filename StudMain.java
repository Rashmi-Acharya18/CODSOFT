import java.io.*;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    int rollNumber;
    char grade;

    Student(String name, int rollNumber, char grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    String getName() {
        return name;
    }

    int getRollNumber() {
        return rollNumber;
    }

    char getGrade() {
        return grade;
    }

    public String toString() {
        return "\n Student:-\n name='" + name + "' \n rollNumber=" + rollNumber + " \n grade=" + grade + " \n";
    }
}

class StudentManagementSystem {
    private static final int MAX_STUDENTS = 100;
    Student[] students;
    int currentSize;
    Scanner scanner;

    StudentManagementSystem() {
        this.students = new Student[MAX_STUDENTS];
        this.currentSize = 0;
        this.scanner = new Scanner(System.in);
    }

    public void addStudent() {
        if (currentSize < MAX_STUDENTS) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter roll number: ");
            int rollNumber = getValidIntInput();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter grade: ");
            char grade = getValidCharInput();
            scanner.nextLine(); // Consume the newline character

            students[currentSize++] = new Student(name, rollNumber, grade);
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Maximum number of students reached. Cannot add more.");
        }
    }

    public void removeStudent() {
        System.out.print("Enter roll number of the student to be removed: ");
        int rollNumber = getValidIntInput();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < currentSize; i++) {
            if (students[i].getRollNumber() == rollNumber) {
                System.out.println("Student removed: " + students[i].getName());

                for (int j = i; j < currentSize - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--currentSize] = null; // Clear the last reference
                return;
            }
        }
        System.out.println("Student with roll number " + rollNumber + " not found.");
    }

    public void searchStudent() {
        System.out.print("Enter roll number of the student to be searched: ");
        int rollNumber = getValidIntInput();

        for (int i = 0; i < currentSize; i++) {
            if (students[i].getRollNumber() == rollNumber) {
                System.out.println("Student found: " + students[i]);
                return;
            }
        }
        System.out.println("Student with roll number " + rollNumber + " not found.");
    }

    public void displayAllStudents() {
        System.out.println("List of all the students:");
        for (int i = 0; i < currentSize; i++) {
            System.out.println(students[i]);
        }
    }

    public void writeStudentsToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            System.out.println("Student data written to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void readStudentsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (Student[]) ois.readObject();
            System.out.println("Student data read from file: " + filename);
            currentSize = students.length;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    private int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private char getValidCharInput() {
        while (!scanner.hasNext()) {
            System.out.println("Invalid input. Please enter a character.");
            scanner.next();
        }
        return scanner.next().charAt(0);
    }
}

public class StudMain {
    public static void main(String[] args) {
        StudentManagementSystem studentSystem = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search for Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Students to File");
            System.out.println("6. Load Students from File");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    studentSystem.addStudent();
                    break;
                case 2:
                    studentSystem.removeStudent();
                    break;
                case 3:
                    studentSystem.searchStudent();
                    break;
                case 4:
                    studentSystem.displayAllStudents();
                    break;
                case 5:
                    System.out.print("Enter filename to save students: ");
                    String saveFilename = scanner.nextLine();
                    studentSystem.writeStudentsToFile(saveFilename);
                    break;
                case 6:
                    System.out.print("Enter filename to load students from: ");
                    String loadFilename = scanner.nextLine();
                    studentSystem.readStudentsFromFile(loadFilename);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 0);

        studentSystem.closeScanner();
    }
}
