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

public class Tutor extends Person implements UserManagement {

    private static final int UserType = 1;
    private final String FilePath = "User.txt";
    private ArrayList<Courses> TeachingCourses;
    private double TotalSalary;
    private int noOfCourses = 0;
    private FileManager Fmanager;
    public static ArrayList<Courses> courses = new ArrayList<Courses>();
    public static ArrayList<Student> Students = new ArrayList<Student>();
    public static ArrayList<Admin> admins = new ArrayList<Admin>();
    public static ArrayList<Tutor> Tutors = new ArrayList<Tutor>();
    public static ArrayList<Materials> materials = new ArrayList<Materials>();

    public Tutor() throws FileNotFoundException {
        TeachingCourses = new ArrayList<Courses>();
        this.Fmanager = new FileManager();
        File f = new File("Courses.txt");
        File ff = new File("Materials.txt");
        if (f.exists() && f.length() > 5) {
            courses = (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
        }
        if (ff.exists() && f.length() > 5) {
            materials = (ArrayList<Materials>) (Object) Fmanager.read("Materials.txt");
        }

    }

    public ArrayList<Tutor> Load() throws FileNotFoundException {

        return (ArrayList<Tutor>) (Object) Fmanager.read(FilePath, UserType);

    }

    public String getTutordata() {
        return (this.UserType + "&" + this.getUserName() + "&" + this.getPassword() + "&" + this.getFullName() + "&" + this.GetSalary() + "&");
    }

    public void setSalary(double Salary) {
        this.TotalSalary = Salary;
    }

    public double GetSalary() {
        return this.TotalSalary;
    }

    public ArrayList<Courses> getTeachingCourses() {
        return this.TeachingCourses;
    }

    public void Assgin(int ID, float grade, double attendance) throws FileNotFoundException {//lama n3ml gui hnb3t el values bta3t el grade wel attendance mnhom
        Students = (ArrayList<Student>) (Object) Fmanager.read(FilePath, 2);

        if (Students.size() > 0) {
            for (int i = 0; i < Students.size(); i++) {
                if (Students.get(i).getID() == ID) {
                    Students.get(i).setGrades(grade);
                    Students.get(i).setAttendance(attendance);
                    Fmanager.writer(ID + "&" + grade + "&" + attendance + "&", "Assigned.txt", true);
                    break;
                }
            }

        }
    }

    public void AddMaterial(String Name, double Price) throws FileNotFoundException {
        try {
            Materials m = new Materials();
            m.SetMaterial(Name, Price);
            File f = new File("Materials.txt");
            if (Name.length() == 0) {
                JOptionPane.showMessageDialog(null, "The input  length must be at least 1 character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < Name.length(); i++) {
                if (Name.charAt(i) == '&') {
                    JOptionPane.showMessageDialog(null, "Your Data Can't Contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (f.length() < 5) {
                Fmanager.writer(m.getMaterialData(), "Materials.txt", false);
                return;
            }
            materials = (ArrayList<Materials>) (Object) Fmanager.read("Materials.txt");
            for (int i = 0; i < materials.size(); i++) {
                if (Name.equals(materials.get(i).getMaterialname()) && Price == materials.get(i).getMaterialPrice()) {
                    JOptionPane.showMessageDialog(null, "The material is already exist .", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Fmanager.writer(m.getMaterialData(), "Materials.txt", true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
    }

    public boolean DeleteMaterial(String Name) throws FileNotFoundException {
        try {
            materials = (ArrayList<Materials>) (Object) Fmanager.read("Materials.txt");
            for (int i = 0; i < materials.size(); i++) {
                if (materials.get(i).getMaterialname().equals(Name)) {
                    materials.remove(i);
                    if (materials.size() > 0) {
                        Fmanager.writer(materials.get(0).getMaterialData(), "Materials.txt", false);
                        for (int j = 1; j < materials.size(); j++) {
                            Fmanager.writer(materials.get(i).getMaterialData(), "Materials.txt", true);
                        }
                    } else {
                        Fmanager.writer("", "Materials.txt", false);

                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return false;

    }

    public void AddCourse(String CourseName) throws FileNotFoundException {
        try {
            File f = new File("Courses.txt");
            for (int i = 0; i < this.courses.size(); i++) {
                if (this.courses.get(i).getCourseName().equals(CourseName)) {
                    this.courses.get(i).setTutor(this.getFullName());
                    this.TeachingCourses.add(this.courses.get(i));
                    this.noOfCourses++;
                }
            }
            if (f.length() < 5) {
                JOptionPane.showMessageDialog(null, "No Courses to add", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (this.courses.size() == 1) {
                    Fmanager.writer(this.courses.get(0).getCourseData(), "Courses.txt", false);
                } else {
                    Fmanager.writer(this.courses.get(0).getCourseData(), "Courses.txt", false);
                    for (int i = 1; i < this.courses.size(); i++) {
                        Fmanager.writer(this.courses.get(i).getCourseData(), "Courses.txt", true);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }

    }

    public void DeleteCourse(String CourseName) throws FileNotFoundException {
        try {
            if (this.courses.size() >= 1) {
                this.courses = (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
                for (int j = 0; j < this.courses.size(); j++) {
                    if (this.courses.get(j).getTutor().equals(this.getFullName())) {
                        TeachingCourses.add(this.courses.get(j));
                    }
                }
                for (int i = 0; i < this.courses.size(); i++) {
                    if (this.courses.get(i).getCourseName().equals(CourseName)) {
                        this.courses.get(i).setTutor("");
                        for (int j = 0; j < this.TeachingCourses.size(); j++) {
                            if (this.TeachingCourses.get(j).getCourseName().equals(CourseName)) {
                                this.TeachingCourses.remove(j);
                                break;
                            }

                        }

                    }
                }

                Fmanager.writer(this.courses.get(0).getCourseData(), "Courses.txt", false);
                for (int i = 1; i < this.courses.size(); i++) {
                    Fmanager.writer(this.courses.get(i).getCourseData(), "Courses.txt", true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No Courses to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
    }

    @Override
    public ArrayList<Courses> ListCourse() throws IOException, ClassNotFoundException {
        ArrayList<Courses> courseslist = new ArrayList<Courses>();

        for (int i = 0; i < this.courses.size(); i++) {

            if (this.courses.get(i).getTutor() == this.getFullName()) {
                courseslist.add(this.courses.get(i));
            }
        }
        return courseslist;
    }

    @Override
    public boolean Login(String username, String pass) {
        try {
            Tutors = (ArrayList<Tutor>) (Object) Fmanager.read(FilePath, 1);
            for (int i = 0; i < Tutors.size(); i++) {
                if (Tutors.get(i).getUserName().equals(username) && Tutors.get(i).getPassword().equals(pass)) {
                    this.setUserName(username);
                    this.setPassWord(pass);
                    this.setFullName(Tutors.get(i).getFullName());
                    this.setSalary(Tutors.get(i).GetSalary());

                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

}
