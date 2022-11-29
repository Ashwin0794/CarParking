package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Servlet implementation class CarPark
 */
@WebServlet("/")
public class CarPark extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarPark() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carparking", "root",
					"idkwhy@123");
			System.out.println("Connected!");
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected boolean checkRegistrationExists(String registration) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carparking", "root",
					"idkwhy@123");
			String sqlString = "SELECT * FROM user WHERE registration=" + "\"" + registration + "\";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
				conn.close();
				return true;
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	protected boolean checkRFID_IN_DB(String rfid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carparking", "root",
					"idkwhy@123");
			String sqlString = "SELECT * FROM user WHERE rfid=" + "\"" + rfid + "\";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
				conn.close();
				return false;
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public String rfid() {
		String rfid = "";
		for (int i = 0; i < 10; i++) {

			if (i % 2 == 0) {
				int upperbound = 9;
				// generate random values from 0-9
				Random rand = new Random();
				Integer int_random = rand.nextInt(upperbound);
				char c = (char) (int_random + 'A');
				rfid += c;
			} else {
				int upperbound = 9;
				// generate random values from 0-9
				Random rand = new Random();
				Integer int_random = rand.nextInt(upperbound);
				rfid += int_random.toString();
			}

		}

		return rfid;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");

		String carMake = request.getParameter("carmake");
		String carModel = request.getParameter("carmodel");
		String carRegistration = request.getParameter("carregistration");

		String creditCardNumber = request.getParameter("ccn");
		String cvv = request.getParameter("cvv");
		String expireMM = request.getParameter("expireMM");
		String expireYY = request.getParameter("expireYY");
		
		
		// check if car is unique based on registration
		if (checkRegistrationExists(carRegistration)) {
			System.out.println("Already exists");
		} else {
			String rfid = "";
			// check if rfid is unique
			while (true) {
				rfid += rfid();
				if (checkRFID_IN_DB(rfid)) {
					break;
				}

			}
			// if rfid is unique, insert record
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carparking", "root",
						"idkwhy@123");
				String sqlString = "INSERT INTO user VALUES(" + "\"" + carRegistration + "\"," + "\"" + fname + "\","
						+ "\"" + lname + "\"," + "\"" + carMake + "\"," + "\"" + carModel + "\"," + "\""
						+ creditCardNumber + "\"," + "\"" + cvv + "\"," + "\"" + expireMM + "\"," +  "\"" + expireYY + "\"," + "\"" + rfid + "\");";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sqlString);
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
