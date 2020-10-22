package stars;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentUI extends SelectUI{
    StudentController studentController;
    
    public StudentUI (String studentID) {
        this.studentController = new StudentController(studentID);
    }
    
    
    public void displayMenu(){
        
        Scanner sc = new Scanner(System.in);
        int i;
        System.out.println("in admin display menu");
        do {
            System.out.println("1. Add course index");
            System.out.println("2. Drop course index");
            System.out.println("3. Print Rrgistered courses");
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
                default:
                    break;
            }

        } while (i > 0 && i < 7) ;
        return;
    }

    private void addIndex() {
        ArrayList<Course> courseList;
        Course selectedCourse;
        ArrayList<Index> indexList;
        Index selectedIndex;
        
        courseList = studentController.getCourseList();
        selectedCourse = (Course)select(courseList);
        indexList = selectedCourse.getIndexList();
        selectedIndex = (Index)select(indexList);
        studentController.addIndex(selectedIndex);
    }
    private void dropIndex() {
        ArrayList<Index> indexList;
        Index selectedIndex;
        indexList = studentController.getRegisteredIndex();
        selectedIndex = (Index)select(indexList);
        studentController.dropIndex(selectedIndex);
    }
    
    private void printRegisteredCourses() {
        ArrayList<Index> indexList;
        Index selectedIndex;
        indexList = studentController.getRegisteredIndex();
        for (Index index: indexList) {
            System.out.println(index.print());
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
    
}