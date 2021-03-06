package stars.boundary;

import stars.controller.*;
import stars.entity.*;
import stars.boundary.admin.*;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Displays User Interface and handles User Input for Admin User
 */
public class AdminUI extends SelectUI {
    private AdminController adminController;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor for AdminUI
     */
    public AdminUI() {
        adminController = new AdminController();
    }

    /**
     * Lets Admin select from Admin Menu
     */
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int i;
        do {
            System.out.println("\n===ADMIN MENU===");
            System.out.println("1. Edit student access period");
            System.out.println("2. Add student");
            System.out.println("3. Add course");
            System.out.println("4. Update course");
            System.out.println("5. Check availability slot for an index");
            System.out.println("6. Print student list by index number");
            System.out.println("7. Print student list by course");
            System.out.println("8. Print all students in database");
            System.out.println("9. Print all courses in database");
            System.out.println("10: Display Index Timings");
            System.out.println("0. Logout");
            System.out.print("Option: ");
            i = IntScanner.nextInt();
            switch (i) {
                case 1:
                    EditStudentAccessUI editStudentAccess = new EditStudentAccessUI();
                    editStudentAccess.editStudentAccess(adminController, dateFormatter);
                    break;
                case 2:
                    AddStudentUI addStudentUI = new AddStudentUI();
                    addStudentUI.addStudent(adminController, dateFormatter);
                    break;
                case 3:
                    AddCourseUI addCourseUI = new AddCourseUI();
                    addCourseUI.addCourse(adminController, timeFormatter);
                    break;
                case 4:
                    EditCourseInformationUI editCourseInformationUI = new EditCourseInformationUI();
                    editCourseInformationUI.editCourseInformation(adminController, timeFormatter);
                    break;
                case 5:
                    CheckVacancyUI checkVacancyUI = new CheckVacancyUI();
                    checkVacancyUI.checkVacancy(adminController);
                    break;
                case 6:
                    PrintStudentListByIndexUI printStudentListByIndexUI = new PrintStudentListByIndexUI();
                    printStudentListByIndexUI.printStudentListByIndex(adminController);
                    break;
                case 7:
                    PrintStudentListByCourseUI printStudentListByCourseUI = new PrintStudentListByCourseUI();
                    printStudentListByCourseUI.printStudentListByCourse(adminController);
                    break;
                case 8:
                    getallstudents();
                    break;
                case 9:
                    getallcourses();
                    break;
                case 10:
                    PrintIndexTimetableUI printIndexTimetableUI = new PrintIndexTimetableUI();
                    printIndexTimetableUI.printIndexTimetable(adminController);
                    break;
                default:
                    break;
            }

        } while (i > 0 && i < 11);
        adminController.save();
        return;
    }

    /**
     * UI to print all students in the database
     */
    private void getallstudents() {
        ArrayList<Student> ls = adminController.getStudentList();
        System.out.println("Students: ");
        if (ls.size() == 0) {
            System.out.println("\nNo Students in Database!\n");
        }
        for (Student smth : ls) {
            System.out.printf("%s\n", smth.print());
        }
    }

    /**
     * UI to print all courses in the database
     */
    private void getallcourses() {
        ArrayList<Course> ls = adminController.getCourseList();
        System.out.println("Courses: ");
        if (ls.size() == 0) {
            System.out.println("\nNo Courses in Database!\n");
        }
        for (Course another : ls) {
            System.out.printf("%s\n", another.print());
        }
    }
}
