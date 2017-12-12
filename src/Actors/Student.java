/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import File.Manager.FileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Student extends Person implements UserManagement {

    private static final int UserType = 2;
    private int ID;
    // ArrayList<Courses> Booked;
    private ArrayList<Courses> BookedCourses = new ArrayList<Courses>();
    private ArrayList<Double> Attendance;
    private ArrayList<Float> Grades;
    private double invoices = 0.0;
    private int booked;
    private int attended;
    private int grad;
    private final String FileName = "User.txt";
    private String BookedFile = this.ID + ".txt";
    private String paidMaterials = this.ID + " Material.txt";
    private FileManager Fmanager = new FileManager();
    public static ArrayList<Student> Students = new ArrayList<Student>();
    public static ArrayList<Courses> Courses = new ArrayList<Courses>();
    public static ArrayList<Tutor> Tutors = new ArrayList<Tutor>();
    public static ArrayList<Materials> materials = new ArrayList<Materials>();

    public Student() throws FileNotFoundException {
        this.Grades = new ArrayList<Float>();
        this.Attendance = new ArrayList<Double>();
        booked = 0;
        attended = 0;
        grad = 0;
    }

    public Student(int id) throws FileNotFoundException {
        this.Grades = new ArrayList<Float>();
        this.Attendance = new ArrayList<Double>();
        this.setID(id);
        File f = new File("User.txt");
        if (f.exists() && f.length() > 5) {
            Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);
            for (int i = 0; i < Students.size(); i++) {
                if (Students.get(i).getID() == id) {
                    this.setFullName(Students.get(i).getFullName());
                    this.setPassWord(Students.get(i).getPassword());
                    this.setInvoices(Students.get(i).getInvoices());
                    this.setUserName(Students.get(i).getUserName());
                }

            }
        }
        booked = 0;
        attended = 0;
        grad = 0;
    }

    public void updateusers() throws FileNotFoundException {

        Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);

        for (int n = 0; n < Students.size(); n++) {
            if (Students.get(n).getID() == this.ID) {
                Students.get(n).setInvoices(this.invoices);
                break;
            }
        }
        ArrayList<Tutor> t = new ArrayList<Tutor>();
        t = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
        ArrayList<Admin> a = new ArrayList<Admin>();
        a = (ArrayList<Admin>) (Object) Fmanager.read(FileName, 0);

        Fmanager.writer(Students.get(0).getStudentData(), FileName, false);
        for (int n = 1; n < Students.size(); n++) {
            Fmanager.writer(Students.get(n).getStudentData(), FileName, true);
        }
        for (int n = 0; n < t.size(); n++) {
            Fmanager.writer(t.get(n).getTutordata(), FileName, true);
        }
        for (int n = 0; n < a.size(); n++) {
            Fmanager.writer(a.get(n).getAdminData(), FileName, true);
        }

    }

    public String getStudentData() {
        return (this.UserType + "&" + this.getUserName() + "&" + this.getPassword() + "&" + this.ID + "&" + this.getFullName() + "&" + this.invoices + "&");
    }

    public void payinvoices(double cash) throws FileNotFoundException {
        try {
            if (cash < this.invoices) {
                this.invoices -= cash;
            } else if (cash >= this.invoices) {
                this.invoices = 0;
            }
            /*
                    Students=(ArrayList<Student>)(Object)Fmanager.read(FileName, UserType);
                    for(int i=0;i<Students.size();i++){
                        if(Students.get(i).getID()==this.ID){
                            Students.get(i).setInvoices(this.invoices);
                            break;
                        }
                    }
                    
                    ArrayList<Tutor> t= new ArrayList<Tutor>();
                    t=(ArrayList<Tutor>)(Object)Fmanager.read(FileName, 1);
                    ArrayList<Admin> a =new ArrayList<Admin>();
                    a=(ArrayList<Admin>)(Object) Fmanager.read(FileName, 0);
                    
                    Fmanager.writer(Students.get(0).getStudentData(),FileName,false );
                    for(int i=1;i<Students.size();i++){
                    Fmanager.writer(Students.get(i).getStudentData(),FileName,true );    
                    }
                    for(int i=0;i<t.size();i++){
                    Fmanager.writer(t.get(i).getTutordata(),FileName,true );        
                    }
                    for(int i=0;i<a.size();i++){
                        Fmanager.writer(a.get(i).getAdminData(), FileName, true);
                    }
             */
            this.updateusers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean buyMaterial(String MaterialName) {
        try {
            File f = new File("Materials.txt");
            File ff = new File(this.ID + " Material.txt");
            if (ff.exists() && ff.length() > 5) {
                ArrayList<Materials> m = new ArrayList<Materials>();
                m = (ArrayList<Materials>) (Object) Fmanager.readstudentmaterial(this.ID + " Material.txt");
                for (int i = 0; i < m.size(); i++) {
                    if (m.get(i).getMaterialname().equals(MaterialName)) {
                        JOptionPane.showMessageDialog(null, "Dear " + this.getFullName() + " You've already paid this Material. ", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            if (f.length() > 5) {
                materials = (ArrayList<Materials>) (Object) Fmanager.read("Materials.txt");
                for (int i = 0; i < materials.size(); i++) {
                    if (materials.get(i).getMaterialname().equals(MaterialName)) {
                        this.invoices += materials.get(i).getMaterialPrice();
                        Fmanager.writer(materials.get(i).getMaterialData(), this.ID + " Material.txt", true);
                        Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);
                        for (int n = 0; n < Students.size(); n++) {
                            if (Students.get(n).getID() == this.ID) {
                                Students.get(n).setInvoices(this.invoices);
                                break;
                            }
                        }
                        ArrayList<Tutor> t = new ArrayList<Tutor>();
                        t = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
                        ArrayList<Admin> a = new ArrayList<Admin>();
                        a = (ArrayList<Admin>) (Object) Fmanager.read(FileName, 0);

                        Fmanager.writer(Students.get(0).getStudentData(), FileName, false);
                        for (int n = 1; n < Students.size(); n++) {
                            Fmanager.writer(Students.get(n).getStudentData(), FileName, true);
                        }
                        for (int n = 0; n < t.size(); n++) {
                            Fmanager.writer(t.get(n).getTutordata(), FileName, true);
                        }
                        for (int n = 0; n < a.size(); n++) {
                            Fmanager.writer(a.get(n).getAdminData(), FileName, true);
                        }
                        return true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No Materials to buy", "Materials not found", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void UpdateCourse(String CourseName) throws FileNotFoundException {
        try {
            int set = 0;
            Courses = (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
            ArrayList<Courses> c = new ArrayList<Courses>();
            c = (ArrayList<Courses>) (Object) Fmanager.read(this.ID + ".txt");
            File f = new File(this.ID + ".txt");
            if (f.exists() && f.length() > 5) {
                for (int i = 0; i < c.size(); i++) {
                    if (CourseName.equals(c.get(i).getCourseName())) {
                        for (int j = 0; j < Courses.size(); j++) {
                            if (Courses.get(j).getCourseName().equals(c.get(i).getCourseName())) {
                                c.get(i).setTutor(Courses.get(j).getTutor());
                                set = 1;
                                break;
                            }
                        }
                    }
                }
            }
            if (set == 1) {
                Fmanager.writer(c.get(0).getCourseData(), this.ID + ".txt", false);
                for (int i = 1; i < c.size(); i++) {
                    Fmanager.writer(c.get(i).getCourseData(), this.ID + ".txt", true);
                }
            } else {
                this.DeleteCourse(CourseName);
                JOptionPane.showMessageDialog(null, "The course didn't found so it deleted from your courses", FullName, UserType);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
    }

    public void AddCourse(String CourseName) throws FileNotFoundException {
        try {
            Courses = (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
            File f = new File(this.ID + ".txt");
            if (f.exists() && f.length() > 5) {
                ArrayList<Courses> c = new ArrayList<Courses>();
                c = (ArrayList<Courses>) (Object) Fmanager.read(this.ID + ".txt");
                for (int i = 0; i < c.size(); i++) {
                    if (CourseName.equals(c.get(i).getCourseName())) {
                        JOptionPane.showMessageDialog(null, "You have already booked this Course .", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            for (int i = 0; i < Courses.size(); i++) {
                if (Courses.get(i).getTutor() == "") {
                    JOptionPane.showMessageDialog(null, "We are Sorry this course is exist but not available yet !", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (Courses.get(i).getCourseName().equals(CourseName)) {
                    this.BookedCourses.add(Courses.get(i));
                    this.invoices += Courses.get(i).getCoursePrice();

                    Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);
                    for (int n = 0; n < Students.size(); n++) {
                        if (Students.get(n).getID() == this.ID) {
                            Students.get(n).setInvoices(this.invoices);
                            break;
                        }
                    }
                    ArrayList<Tutor> t = new ArrayList<Tutor>();
                    t = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
                    ArrayList<Admin> a = new ArrayList<Admin>();
                    a = (ArrayList<Admin>) (Object) Fmanager.read(FileName, 0);

                    Fmanager.writer(Students.get(0).getStudentData(), FileName, false);
                    for (int n = 1; n < Students.size(); n++) {
                        Fmanager.writer(Students.get(n).getStudentData(), FileName, true);
                    }
                    for (int n = 0; n < t.size(); n++) {
                        Fmanager.writer(t.get(n).getTutordata(), FileName, true);
                    }
                    for (int n = 0; n < a.size(); n++) {
                        Fmanager.writer(a.get(n).getAdminData(), FileName, true);
                    }
                    if (f.length() < 5) {
                        Fmanager.writer(this.BookedCourses.get(booked++).getCourseData(), this.ID + ".txt", false);

                    } else {
                        Fmanager.writer(this.BookedCourses.get(booked++).getCourseData(), this.ID + ".txt", true);
                    }
                    return;
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }

    }//end

    public void DeleteCourse(String CourseName) throws FileNotFoundException {
        try {
            this.BookedCourses = (ArrayList<Courses>) (Object) Fmanager.read(this.ID + ".txt");
            for (int j = 0; j < this.BookedCourses.size(); j++) {
                if (this.BookedCourses.get(j).getCourseName().equals(CourseName)) {
                    if (this.BookedCourses.get(j).getCoursePrice() < this.invoices) {
                        this.invoices -= this.BookedCourses.get(j).getCoursePrice();
                    } else if (this.BookedCourses.get(j).getCoursePrice() >= this.invoices) {
                        this.invoices = 0;
                    }

                    this.BookedCourses.remove(j);

                    Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);
                    for (int i = 0; i < Students.size(); i++) {
                        if (Students.get(i).getID() == this.ID) {
                            Students.get(i).setInvoices(this.invoices);
                            break;
                        }
                    }
                    ArrayList<Tutor> t = new ArrayList<Tutor>();
                    t = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
                    ArrayList<Admin> a = new ArrayList<Admin>();
                    a = (ArrayList<Admin>) (Object) Fmanager.read(FileName, 0);

                    Fmanager.writer(Students.get(0).getStudentData(), FileName, false);
                    for (int i = 1; i < Students.size(); i++) {
                        Fmanager.writer(Students.get(i).getStudentData(), FileName, true);
                    }
                    for (int i = 0; i < t.size(); i++) {
                        Fmanager.writer(t.get(i).getTutordata(), FileName, true);
                    }
                    for (int i = 0; i < a.size(); i++) {
                        Fmanager.writer(a.get(i).getAdminData(), FileName, true);
                    }
                    this.booked--;
                    break;
                }
            }
            //this.invoices-=this.BookedCourses.get(j).getCoursePrice();
            if (this.BookedCourses.size() > 0) {
                Fmanager.writer(this.BookedCourses.get(0).getCourseData(), this.ID + ".txt", false);
                for (int t = 1; t < this.BookedCourses.size(); t++) {
                    Fmanager.writer(this.BookedCourses.get(t).getCourseData(), this.ID + ".txt", true);
                }
            } else {
                Fmanager.writer("", this.ID + ".txt", false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
    }

    @Override
    public ArrayList<Courses> ListCourse() throws IOException, ClassNotFoundException {
        try {
            File f = new File(this.ID + ".txt");
            if (f.exists()) {
                Courses = (ArrayList<Courses>) (Object) Fmanager.read(this.ID + ".txt");
            } else {
                JOptionPane.showMessageDialog(null, "No courses to list", "Error_MSG", JOptionPane.ERROR_MESSAGE);
            }
            return Courses;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return null;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGrades(float Grades) {
        this.Grades.add(Grades);
    }

    public void setAttendance(double Attendance) {
        this.Attendance.add(Attendance);
    }

    public ArrayList<Double> getAttendance() throws FileNotFoundException {
        //Fmanager.ReadGrades(ID, "Assigned.txt",this);
        return this.Attendance;
    }

    public ArrayList<Float> getGrades() throws FileNotFoundException {
        //Fmanager.ReadGrades(ID, "Assigned.txt",this);
        return this.Grades;
    }

    public void setInvoices(double Invoices) {
        this.invoices = Invoices;
    }

    public ArrayList<Courses> getCourses() {
        return this.BookedCourses;
    }

    public int getID() {
        return this.ID;
    }

    public double getInvoices() {
        return this.invoices;
    }

    @Override
    public boolean Login(String username, String pass) {
        try {
            Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, UserType);
            for (int i = 0; i < Students.size(); i++) {
                if (Students.get(i).getUserName().equals(username) && Students.get(i).getPassword().equals(pass)) {
                    this.setUserName(username);
                    this.setPassWord(pass);
                    this.setFullName(Students.get(i).getFullName());
                    this.setID(Students.get(i).getID());
                    this.setInvoices(Students.get(i).getInvoices());
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
}
