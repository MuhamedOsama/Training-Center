/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import java.io.IOException;
import java.util.ArrayList;

public interface UserManagement {

    ArrayList<Courses> ListCourse() throws IOException, ClassNotFoundException;

    boolean Login(String username, String pass);

}
