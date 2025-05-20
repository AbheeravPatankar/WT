package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Statement;

@CrossOrigin
@RestController
public class Controller{
	Connection conn;
	public Controller(){
		String url = "jdbc:mysql://localhost:3306/WT"; 
        String user = "root";                        
        String password = "1234"; 
        
        try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("connection successfull to database ");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/")
	public void sendMsg() {
		System.out.println("hello world in tom cat .... ");
	}
	
	@PostMapping("/add_student/{name}/{ese}/{mse}")
	public String add_student(@PathVariable String name, @PathVariable int ese, @PathVariable int mse) {
		String query = "insert into WT.Students (student_name, student_mse, student_ese) values ('" + name + "','" + mse + "','" + ese + "');";
		System.out.println(query);
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "query executed ... ";
	}
	
	@GetMapping("/get_students")
	public ArrayList<String> get_students() {
		String query = "SELECT * FROM `Students` WHERE 1";
		ArrayList<String> names = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
                // Assuming your columns are: id (int), stuedent_name (String), student_mse (int), student_ese (int)
                int id = rs.getInt("student_id"); // or use rs.getInt(1) for column index
                String name = rs.getString("student_name");
                int mse = rs.getInt("student_mse");
                int ese = rs.getInt("student_ese");
                names.add(name);
                System.out.println("ID: " + id + ", Name: " + name + ", MSE: " + mse + ", ESE: " + ese);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return names;
	}
	
	@GetMapping("/calc_grade/{name}")
	public ArrayList<String> calculate_grade(@PathVariable String name){
		ArrayList<String> result = new ArrayList<String>();
		String query = "Select * from WT.Students where student_name = '" + name + "';";
		System.out.println(query);
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				int mse = rs.getInt("student_mse");
				int ese = rs.getInt("student_ese");
				char grade = 'A';
				result.add(name);
				result.add(String.valueOf(mse));
				result.add(String.valueOf(ese));
				result.add(String.valueOf(grade));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}




















