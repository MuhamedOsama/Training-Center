/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import File.Manager.FileManager;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Courses {

    private String CourseName;
    private double CoursePrice;
    private int Hours;
    private String teacher;
    private FileManager Fmanager = new FileManager();

    public ArrayList<Courses> loadcourse() throws FileNotFoundException {
        return (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
    }

    //CourseHours&CourseName&CoursePrice&tutorFULlName&Materialname
    public String getCourseData() {
        return (this.Hours + "&" + this.CourseName + "&" + this.CoursePrice + "&" + this.teacher + "&");
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseName() {
        return this.CourseName;
    }

    public void setCoursePrice(double CoursePrice) {
        this.CoursePrice = CoursePrice;
    }

    public double getCoursePrice() {
        return this.CoursePrice;
    }

    public void setCourseHours(int Hours) {
        this.Hours = Hours;
    }

    public int getCourseHours() {
        return this.Hours;
    }

    public void setTutor(String TutorName) {
        this.teacher = TutorName;
    }

    public String getTutor() {
        return this.teacher;
    }

}
