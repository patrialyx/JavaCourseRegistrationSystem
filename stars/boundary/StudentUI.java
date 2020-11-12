package stars.boundary;
import java.util.ArrayList;
import java.util.Scanner;
import stars.controller.*;
import stars.entity.*;

public class StudentUI extends SelectUI{
    StudentController studentController;
    
    public StudentUI (String studentID) {
        this.studentController = new StudentController(studentID);
    }
    
    
    public void displayMenu(){
        
        Scanner sc = new Scanner(System.in);
        int i;
        System.out.println("===STUDENT MENU===");
        do {
            System.out.println("1. Add course index");
            System.out.println("2. Drop course index");
            System.out.println("3. Print Registered courses");
            System.out.println("4. Check vacancies available");
            System.out.println("5. Change index number of course");
            System.out.println("6. Swop index number with another student");
            System.out.print("Option: ");
            i = sc.nextInt();
            switch (i) {
                case 1:
                    addIndex();
                    break;
                case 2: 
                    dropIndex();
                    break;
                case 3:
                    printRegisteredCourses();
                    break;
                case 4:
                    checkVacanciesAvailable();
                    break;
                case 5:
                    changeIndex();
                    break;
                case 6:
                    swopIndex();
                    break;
                default:
                    break;
            }

        } while (i > 0 && i < 7) ;
        studentController.save();
        return;
    }

    private void addIndex() {
        boolean success;
        ArrayList<Course> courseList;
        Course selectedCourse;
        ArrayList<Index> indexList;
        Index selectedIndex;
        
        courseList = studentController.getCourseList();
        selectedCourse = (Course)select(courseList);
        indexList = selectedCourse.getIndexList();
        selectedIndex = (Index)select(indexList);
        success = studentController.addIndex(selectedIndex);
        if (success) {
            System.out.println("Successfully added!");
        } else {
            System.out.println("Error adding, timetable clash or too many AUs");
        }
    }
    private void dropIndex() {
        boolean success;
        ArrayList<Index> indexList;
        Index selectedIndex;
        indexList = studentController.getRegisteredIndex();
        selectedIndex = (Index)select(indexList);
        success = studentController.dropIndex(selectedIndex);
        if (success) {
            System.out.println("Successfully dropped!");
        } else {
            System.out.println("Error dropping");
        }
    }
    
    private void printRegisteredCourses() {
        ArrayList<Index> indexList;
        indexList = studentController.getRegisteredIndex();
        for (Index index: indexList) {
            System.out.println("  "+ index.print());
        }
    }
    
    private void checkVacanciesAvailable() {
        ArrayList<Course> courseList;
        Course selectedCourse;
        ArrayList<Index> indexList;
        Index selectedIndex;
        courseList = studentController.getCourseList();
        selectedCourse = (Course)select(courseList);
        indexList = selectedCourse.getIndexList();
        selectedIndex = (Index)select(indexList);
        System.out.printf("Vacancies: %i", selectedIndex.getVacancy());
    }
    
    private void changeIndex() {
        boolean success;
        ArrayList<Index> indexList;
        Index oldIndex, newIndex;
        
        indexList = studentController.getRegisteredIndex();
        oldIndex = (Index)select(indexList);
        indexList = oldIndex.getCourse().getIndexList();
        newIndex = (Index)select(indexList);
        success = studentController.changeIndex(oldIndex, newIndex);
        if (success) {
            System.out.println("Successfully changed!");
        } else {
            System.out.println("Error changing,  timetable clash");
        }
    }
    
    private void swopIndex() {
        boolean success;
        ArrayList<Index> indexList;
        Index selectedIndex;
        String username, password;
        StudentAuthenticator loginController = new FlatFileStudentAuthenticator();
        Scanner sc = new Scanner(System.in);

        indexList = studentController.getRegisteredIndex();
        selectedIndex = (Index)select(indexList);
        
        System.out.print("Username: ");
        username = sc.next();
        System.out.print("Password: ");
        password = new String(System.console().readPassword());
        // password = sc.next();
        success = loginController.authenticate(username, password);
        if (success) {
            studentController.swopIndex(username, selectedIndex);
        } else {
            System.out.println("Incorrect details");
        }
    }
}