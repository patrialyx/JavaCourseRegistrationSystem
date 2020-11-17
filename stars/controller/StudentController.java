package stars.controller;

import java.util.ArrayList;
import stars.entity.*;
import stars.exceptions.*;
import stars.boundary.*;

public class StudentController {

    private Student myStudent;
    private DatabaseManager dbManager;
    private TimetableDisplayer displayer;

    public StudentController(String studentID) {
        dbManager = new DatabaseManager();
        myStudent = dbManager.findStudent(studentID);
        displayer = new TimetableDisplayer(myStudent);
    }

    public ArrayList<Course> getCourseList() {
        return this.dbManager.getCoursesArray();
    }

    public boolean addIndex(Index myIndex) throws ExceedAUException, TimetableClashException, AlreadyRegisteredException {
        return myStudent.addIndex(myIndex);
    }
    public boolean dropIndex (Index index){
        return myStudent.dropIndex(index);
    }
    
    public ArrayList<Index> getRegisteredIndex () {
        return myStudent.getRegisteredIndex();
    }

    public ArrayList<Index> getConfirmedIndex () {
        return myStudent.getConfirmedIndex();
    }

    public ArrayList<Index> getWaitlistedIndex () {
        return myStudent.getWaitlistedIndex();
    }

    
    
    public boolean changeIndex (Index oldIndex, Index newIndex) throws ExceedAUException, TimetableClashException, AlreadyRegisteredException {
        return myStudent.changeIndex(oldIndex, newIndex);
    }
    
    /**
     * Method to swap position with another student another index index
     * @param friendID studentID of friend who going to take the place in the index
     * @param myIndex The index the student is currently in
     * @return true if myStudent is added to the confirmed list of the new index, false if added to the waitlist of the new index
     * @throws TimetableClashException    If the user is unable to add the Index to
     *                                    the list of Registered Courses because of
     *                                    a Timetable Clash between te Index and the
     *                                    Student's exisiting Indexes
     * @throws AlreadyRegisteredException If the user is unable to add the Index
     *                                    because he has already Registered for the
     *                                    Course
     */
    public boolean swopIndex (String friendID, Index myIndex) throws TimetableClashException, AlreadyRegisteredException {
        //using myIndex find myCourse, then find my friend's Index using myCourse
        Student friend = dbManager.findStudent(friendID); //find the student object for your friend
        Course myCourse = myIndex.getCourse(); //from the student object, find
        for (Index friendIndex : friend.getRegisteredIndex()) {
            Course friendCourse = friendIndex.getCourse();
            if (friendCourse.getCourseID().equals(myCourse.getCourseID())) {//id
                // check that index is not the same
                if (friendIndex.getIndexNumber() == myIndex.getIndexNumber()) {
                    throw new AlreadyRegisteredException();
                }
                
                if (friend.checkChangeIndex(myIndex, friendIndex) && myStudent.checkChangeIndex(friendIndex, myIndex)) {
                    friend.swopPlaces(myIndex, this.myStudent);
                    return myStudent.swopPlaces(friendIndex, friend);
                } 
            }
        }
        throw new TimetableClashException();
    }

    /**
     * method to serilaise the database back to disk
     */
    public void save() {
        this.dbManager.saveInformation();
    }

    /**
     * Method to display timetable of registered courses by the student
     */
    public void displayTimetable() {
        this.displayer.displayTimetable();
    }

}
