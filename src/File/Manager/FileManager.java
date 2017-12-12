/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import Actors.*;

public class FileManager {

    public boolean writer(String Query, String filepath, boolean appendType) {
        PrintWriter Writter = null;
        try {

            Writter = new PrintWriter(new FileWriter(new File(filepath), appendType));
            Writter.println(Query);
            //OptionPane.showMessageDialog(null, "Data have been saved Successfully !","Information",JOptionPane.INFORMATION_MESSAGE);
            Writter.close();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Data haven't Saved Successfully !", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }//end writer

    public boolean ReadGrades(int ID, String FilePath, Student s) throws FileNotFoundException {
        try {
            //id&grade&attendance
            Scanner Reader = null;

            try {

                Reader = new Scanner(new File(FilePath));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Grades file not found !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            File f = new File(FilePath);
            if (f.length() > 5) {
                while (Reader.hasNextLine()) {
                    String Line = Reader.nextLine();
                    String[] Seprated = Line.split("&");
                    if (Integer.parseInt(Seprated[0]) == ID) {
                        s.setAttendance(Double.parseDouble(Seprated[2]));
                        s.setGrades(Float.parseFloat(Seprated[1]));
                    }
                }
                Reader.close();
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return false;

    }//read grades

    public ArrayList<Object> read(String FilePath, int UserType) throws FileNotFoundException {
        try {
            Scanner Reader = null;
            Scanner Tempread = null;
            Scanner tempr = null;
            try {
                Reader = new Scanner(new File(FilePath));

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Users file not found !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //if(FilePath.equals("Student.txt")){

            //array of objects
            Courses c = null;
            Student s = null;//object for reading 
            switch (UserType) {
                case 0:
                    ArrayList<Admin> Admins = new ArrayList<Admin>();
                    Admin A;
                    while (Reader.hasNextLine()) {
                        A = new Admin();
                        String Line = Reader.nextLine();
                        String[] Seprated = Line.split("&");
                        if (Integer.parseInt(Seprated[0]) == UserType) {
                            A.setUserName(Seprated[1]);
                            A.setPassWord(Seprated[2]);
                            A.setFullName(Seprated[3]);
                            Admins.add(A);
                        } else {
                            continue;
                        }
                    }
                    Reader.close();
                    return (ArrayList<Object>) (Object) Admins;
                case 1:
                    ArrayList<Tutor> Tutors = new ArrayList<Tutor>();
                    Tutor t = null;

                    //1&UserName&Password&fullname&salary&
                    while (Reader.hasNextLine()) {
                        t = new Tutor();
                        String Line = Reader.nextLine();
                        String[] Seprated = Line.split("&");
                        if (Integer.parseInt(Seprated[0]) == 1) {
                            t.setUserName(Seprated[1]);
                            t.setPassWord(Seprated[2]);
                            t.setFullName(Seprated[3]);
                            t.setSalary(Double.parseDouble(Seprated[4]));

                            //CourseHours&CourseName&CoursePrice&tutorFULlName
                            File f = new File("Courses.txt");
                            if (f.exists() && f.isDirectory()) {
                                Tempread = new Scanner(f);
                                while (Tempread.hasNextLine()) {
                                    c = new Courses();
                                    String ln = Tempread.nextLine();
                                    String[] sep = ln.split("&");

                                    if (sep[3].equals(t.getFullName())) {
                                        c.setCourseName(Seprated[1]);
                                        c.setCourseHours(Integer.parseInt(sep[0]));
                                        c.setCoursePrice(Double.parseDouble(sep[2]));
                                        c.setTutor(t.getFullName());
                                        t.AddCourse(c.getCourseName());
                                    }
                                }
                                Tempread.close();
                            }
                        } else {
                            continue;
                        }

                        Tutors.add(t);
                    }
                    Reader.close();

                    return (ArrayList<Object>) (Object) Tutors;

                //student
                case 2:
                    ArrayList<Student> Students = new ArrayList<Student>();
                    while (Reader.hasNextLine()) {
                        //UserType+tUserName()+"&"Password()"&"ID+"&"+"&"+FullName()"&"invoices+"&"Attendance+"&"Grades+"&"
                        s = new Student();
                        String line = Reader.nextLine();
                        String[] Seprated = line.split("&");
                        if (Integer.parseInt(Seprated[0]) == UserType) {

                            s.setUserName(Seprated[1]);
                            s.setPassWord(Seprated[2]);
                            s.setID(Integer.parseInt(Seprated[3]));
                            s.setFullName(Seprated[4]);
                            s.setInvoices(Double.parseDouble(Seprated[5]));
                            File fff = new File("Assigned.txt");
                            if (fff.exists() && fff.length() > 5) {
                                this.ReadGrades(s.getID(), "Assigned.txt", s);
                                //s.setAttendance(Double.parseDouble(Seprated[6]));
                                //s.setGrades(Float.parseFloat(Seprated[7]));
                            }
                            File f = new File(s.getID() + ".txt");
                            //CourseHours&CourseName&CoursePrice&tutorFULlName
                            if (f.exists() && f.isDirectory()) {
                                Tempread = new Scanner(f);
                                while (Tempread.hasNextLine()) {
                                    c = new Courses();
                                    String ln = Tempread.nextLine();
                                    String[] sep = ln.split("&");
                                    c.setCourseName(Seprated[1]);
                                    c.setCourseHours(Integer.parseInt(sep[0]));
                                    c.setCoursePrice(Double.parseDouble(sep[2]));
                                    s.AddCourse(c.getCourseName());
                                }
                                Tempread.close();
                            }
                        } else {
                            continue;
                        }
                        Students.add(s);
                    }
                    Reader.close();

                    return (ArrayList<Object>) (Object) Students;

                default:
                    break;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return null;
    }

    public ArrayList<Object> readstudentmaterial(String FilePath) throws FileNotFoundException {
        try {
            Scanner Reader = null;
            Scanner TReader = null;
            try {

                Reader = new Scanner(new File(FilePath));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Courses File not found !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            ArrayList<Materials> materials = new ArrayList<Materials>();
            Materials m;
            File f = new File(FilePath);
            if (f.exists()) {
                while (Reader.hasNextLine() && f.length() > 2) {
                    m = new Materials();
                    String Line = Reader.nextLine();
                    String[] Seprated = Line.split("&");
                    m.SetMaterial(Seprated[0], Double.parseDouble(Seprated[1]));
                    materials.add(m);
                }
                Reader.close();
                return (ArrayList<Object>) (Object) materials;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return null;

    }

    public ArrayList<Object> read(String FilePath) throws FileNotFoundException {
        try {
            Scanner Reader = null;
            Scanner TReader = null;
            try {

                Reader = new Scanner(new File(FilePath));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Courses File not found !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            ArrayList<Courses> courses = new ArrayList<Courses>();
            ArrayList<Materials> materials = new ArrayList<Materials>();
            Courses c;
            Materials m;
            File f = new File(FilePath);
            if ("Materials.txt".equals(FilePath)) {
                while (Reader.hasNextLine() && f.length() > 2) {
                    m = new Materials();
                    String Line = Reader.nextLine();
                    String[] Seprated = Line.split("&");
                    m.SetMaterial(Seprated[0], Double.parseDouble(Seprated[1]));
                    materials.add(m);
                }
                Reader.close();
                return (ArrayList<Object>) (Object) materials;
            } //CourseHours&CourseName&CoursePrice&tutorFULlName&
            else if ("Courses.txt".equals(FilePath) || FilePath != ("Materials.txt") && f.length() > 2 && f.exists()) {
                while (Reader.hasNextLine() && f.length() > 2) {
                    c = new Courses();
                    String Line = Reader.nextLine();
                    String[] Seprated = Line.split("&");
                    c.setCourseHours(Integer.parseInt(Seprated[0]));
                    c.setCourseName(Seprated[1]);
                    c.setCoursePrice(Double.parseDouble(Seprated[2]));

                    try {
                        if (Seprated[3].length() > 0) {
                            c.setTutor(Seprated[3]);
                        }
                    } catch (Exception e) {
                        c.setTutor("");
                    }

                    courses.add(c);
                }
                Reader.close();
                return (ArrayList<Object>) (Object) courses;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " .", "Error", JOptionPane.ERROR);
        }
        return null;

    }

}
