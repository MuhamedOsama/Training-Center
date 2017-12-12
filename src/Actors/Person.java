/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import File.Manager.FileManager;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Person {

    protected String UserName;
    protected String FullName;
    protected String Password;
    private static FileManager Fmanager;

    public Person() {

    }

    /*
    public boolean Login(String username,String pass,int type) throws FileNotFoundException{
        switch(type){
            case 0:{
                
                for(int i=0;i<admin.size();i++){
                    if(admin.get(i).getUserName().equals(username)&&admin.get(i).getPassword().equals(pass)){
                        return true;
                    }
                } 
                return false;
                        }
            case 1:{
               
                for(int i=0;i<tutor.size();i++){
                    if(tutor.get(i).getUserName().equals(username)&&tutor.get(i).getPassword().equals(pass)){
                        return true;
                    }
                } 
                return false;                
            }
            case 2:{
                
                for(int i=0;i<student.size();i++){
                    if(student.get(i).getUserName().equals(username)&&student.get(i).getPassword().equals(pass)){
                        return true;
                    }
                } 
                return false;                
            } 
            
    }
        
        
     return false;   
    }
           
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setPassWord(String Password) {
        this.Password = Password;
    }

    public String getUserName() {
        return this.UserName;
    }

    public String getFullName() {
        return this.FullName;
    }

    public String getPassword() {
        return this.Password;
    }

}
