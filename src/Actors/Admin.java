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

public class Admin extends Person implements UserManagement {

    private static final int UserType = 0;
    private final String CoursesFileName = "Courses.txt";
    private final String FileName = "User.txt";
    private Courses c;
    private FileManager Fmanager;
    private ArrayList<Student> Students;
    private ArrayList<Courses> Courses;
    private ArrayList<Tutor> Tutors;
    private ArrayList<Float> grades = new ArrayList<Float>();

    public Admin() throws FileNotFoundException {
        Fmanager = new FileManager();
        File f = new File(FileName);
        File ff = new File(CoursesFileName);
        if (f.exists() && f.length() > 5) {
            this.Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, 2);
            this.Tutors = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
        }
        if (ff.exists() && ff.length() > 5) {
            this.Courses = (ArrayList<Courses>) (Object) Fmanager.read("Courses.txt");
        }
    }

    private boolean checkexist(String UserName, String Password) throws FileNotFoundException {
        try {
            File f = new File(FileName);

            if (f.length() > 5) {
                this.Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, 2);
                this.Tutors = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
                ArrayList<Admin> add = new ArrayList<Admin>();
                add = (ArrayList<Admin>) (Object) Fmanager.read(FileName, UserType);
                for (int i = 0; i < add.size(); i++) {
                    if (UserName.equals(add.get(i).getUserName())) {

                        return true;
                    }
                }

                for (int i = 0; i < this.Students.size(); i++) {
                    if (UserName.equals(this.Students.get(i).getUserName())) {

                        return true;
                    }
                }
                for (int i = 0; i < this.Tutors.size(); i++) {
                    if (UserName.equals(this.Tutors.get(i).getUserName())) {

                        return true;
                    }
                }

            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return false;

    }

    private int checkdata(String UserName, String Password, String FullName) {
        try {
            if (UserName.length() == 0) {
                return 0;
            } else if (Password.length() == 0) {

                return 0;
            } else if (FullName.length() == 0) {
                return 0;
            }
            for (int i = 0; i < UserName.length(); i++) {

                if (UserName.charAt(i) == '&') {

                    return 1;
                }

            }
            for (int i = 0; i < Password.length(); i++) {

                if (Password.charAt(i) == '&') {
                    return 1;
                }

            }
            for (int i = 0; i < FullName.length(); i++) {

                if (FullName.charAt(i) == '&') {
                    return 1;
                }

            }
            return 3;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return 3;

    }

    public String getAdminData() {
        return (this.UserType + "&" + this.getUserName() + "&" + this.getPassword() + "&" + this.getFullName() + "&");
    }

    public void addAdmin(String UserName, String Password, String FullName) throws FileNotFoundException {
        try {
            Admin x = new Admin();
            File f = new File(FileName);
            if (this.checkdata(UserName, Password, FullName) == 0) {
                JOptionPane.showMessageDialog(null, "The input  length must be at least 1 character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkdata(UserName, Password, FullName) == 1) {
                JOptionPane.showMessageDialog(null, "We are Sorry Your data can't contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkexist(UserName, Password)) {
                JOptionPane.showMessageDialog(null, "The admin's username is already exist .", "Error_MSG", JOptionPane.ERROR_MESSAGE);
                return;
            }
            x.setFullName(FullName);
            x.setPassWord(Password);
            x.setUserName(UserName);
            if (f.length() > 5) {
                Fmanager.writer(x.getAdminData(), FileName, true);
            } else {
                Fmanager.writer(x.getAdminData(), FileName, false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
    }

    public void LoadStudent() throws FileNotFoundException {
        this.Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, 2);
    }

    void SendEmail(int ID) throws FileNotFoundException {
        try {
            Student x = new Student();

            this.Students = (ArrayList<Student>) (Object) Fmanager.read(FileName, 2);
            for (int i = 0; i < Students.size(); i++) {
                if (Students.get(i).getID() == ID) {
                    x = Students.get(i);
                }
            }
            int s = x.getGrades().size();
            for (int i = 0; i < s; i++) {
                String mail = ((i + 1) + "- Dear " + x.getFullName() + " , Your Final Grade is " + x.getGrades().get(i) + " and your attendance is " + x.getAttendance().get(i) + " .");
                String filepath = x.getID() + " Emails.txt";
                Fmanager.writer(mail, filepath, true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }

    }

    public void addStudent(int ID, String UserName, String Password, double invoices, String Fullname) throws FileNotFoundException {//admin
        try {
            Student x = new Student();
            x.setID(ID);
            x.setUserName(UserName);
            x.setPassWord(Password);
            x.setInvoices(invoices);
            x.setFullName(Fullname);
            File f = new File("User.txt");
            if (this.checkdata(UserName, Password, Fullname) == 0) {
                JOptionPane.showMessageDialog(null, "The input  length must be at least 1 character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkdata(UserName, Password, Fullname) == 1) {
                JOptionPane.showMessageDialog(null, "We are Sorry Your data can't contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkexist(UserName, Password)) {
                JOptionPane.showMessageDialog(null, "The Student's username is already exist .", "Error_MSG", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (f.length() < 5) {
                Fmanager.writer(x.getStudentData(), FileName, false);
                return;
            }
            Fmanager.writer(x.getStudentData(), FileName, true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }

    }

    public void addTutor(String FullName, String password, double Salary, String UserName) throws FileNotFoundException {//admin
        try {
            Tutor x = new Tutor();
            x.setFullName(FullName);
            x.setPassWord(password);
            x.setSalary(Salary);
            x.setUserName(UserName);
            File f = new File("User.txt");
            if (this.checkdata(UserName, password, FullName) == 0) {
                JOptionPane.showMessageDialog(null, "The input  length must be at least 1 character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkdata(UserName, password, FullName) == 1) {
                JOptionPane.showMessageDialog(null, "We are Sorry Your data can't contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.checkexist(UserName, password)) {

                JOptionPane.showMessageDialog(null, "The Tutor's username is already exist .", "Error_MSG", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (f.length() > 5) {
                this.Tutors = (ArrayList<Tutor>) (Object) Fmanager.read(FileName, 1);
                for (int i = 0; i < this.Tutors.size(); i++) {
                    if (FullName.equals(this.Tutors.get(i).getFullName())) {
                        JOptionPane.showMessageDialog(null, "The Tutor's FullName is already exist try to shortcut it .", "info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }

            if (f.length() < 5) {
                Fmanager.writer(x.getTutordata(), FileName, false);
                return;
            }
            Fmanager.writer(x.getTutordata(), FileName, true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }
    }

    public void AddCourse(String name, double price, int hours) throws FileNotFoundException {//can't add tutor name because there is probability of the tutor isn't exist and the center admin want to add course in his system
        try {
            File f = new File(CoursesFileName);
            Courses course = new Courses();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "The input  length must be at least 1 character !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == '&') {
                    JOptionPane.showMessageDialog(null, "Sorry your data can't contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (f.length() > 5) {
                course.setCourseName(name);
                course.setCoursePrice(price);
                course.setCourseHours(hours);
                course.setTutor("");
                for (int i = 0; i < this.Courses.size(); i++) {
                    if (course.getCourseName().equals(this.Courses.get(i).getCourseName())) {
                        JOptionPane.showMessageDialog(null, "the new Course's name is already exist", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Fmanager.writer(course.getCourseData(), CoursesFileName, true);
            } else {
                course.setCourseName(name);
                course.setCoursePrice(price);
                course.setCourseHours(hours);
                course.setTutor("");
                Fmanager.writer(course.getCourseData(), CoursesFileName, false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }
    }

    public void UpdateCourse(String CourseOldName, String name, double price, int hours, String teacher) throws FileNotFoundException {
        try {
            File f = new File(CoursesFileName);
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == '&') {
                    JOptionPane.showMessageDialog(null, "Sorry your data can't contains '&' this character !", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            for (int i = 0; i < this.Courses.size(); i++) {
                if (name.equals(this.Courses.get(i).getCourseName()) && price == this.Courses.get(i).getCoursePrice() && hours == this.Courses.get(i).getCourseHours() && teacher.equals(this.Courses.get(i).getTutor())) {
                    JOptionPane.showMessageDialog(null, "the new Course data is already exist", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (f.length() > 5) {
                for (int i = 0; i < this.Courses.size(); i++) {
                    if (this.Courses.get(i).getCourseName().equals(CourseOldName)) {
                        this.Courses.get(i).setCourseName(name);
                        this.Courses.get(i).setCoursePrice(price);
                        this.Courses.get(i).setCourseHours(hours);
                        this.Courses.get(i).setTutor(teacher);
                        break;
                        //courses.get(i).setTutor(teacher);
                    }//
                }

                Fmanager.writer(this.Courses.get(0).getCourseData(), CoursesFileName, false);
                for (int i = 1; i < this.Courses.size(); i++) {
                    Fmanager.writer(this.Courses.get(i).getCourseData(), CoursesFileName, true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No courses to update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }

    }

    public void DeleteCourse(String CourseOldName) throws FileNotFoundException {
        try {
            File f = new File(CoursesFileName);
            if (f.length() > 5) {
                for (int i = 0; i < this.Courses.size(); i++) {
                    if (this.Courses.get(i).getCourseName().equals(CourseOldName)) {
                        this.Courses.remove(i);
                        //break;
                    }
                }
                if (this.Courses.size() > 0) {
                    Fmanager.writer(this.Courses.get(0).getCourseData(), CoursesFileName, false);
                    for (int i = 1; i < this.Courses.size(); i++) {
                        Fmanager.writer(this.Courses.get(i).getCourseData(), CoursesFileName, true);
                    }
                } else {
                    Fmanager.writer("", CoursesFileName, false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No courses to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "info", JOptionPane.ERROR);
        }

    }

    /**
     *
     * @return @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public ArrayList<Courses> ListCourse() throws IOException, ClassNotFoundException {
        return this.Courses;
    }

    @Override
    public boolean Login(String username, String pass) {
        try {
            ArrayList<Admin> admin = (ArrayList<Admin>) (Object) Fmanager.read(FileName, UserType);
            for (int i = 0; i < admin.size(); i++) {
                if (admin.get(i).getUserName().equals(username) && admin.get(i).getPassword().equals(pass)) {
                    this.setUserName(username);
                    this.setPassWord(pass);
                    this.setFullName(admin.get(i).getFullName());
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
