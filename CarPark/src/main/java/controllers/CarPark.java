package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;




import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
	
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
             InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
    public boolean checkRFID(String rfid) {
        String wsURL = "http://localhost:8080/RFID_WS/services/CheckRFID_ExistsPort";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
        String webServiceResponse = "";
        String xmlInput = "<x:Envelope\r\n"
        		+ "    xmlns:x=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n"
        		+ "    xmlns:con=\"http://controllers/\">\r\n"
        		+ "    <x:Header/>\r\n"
        		+ "    <x:Body>\r\n"
        		+ "        <con:checkRFID_IN_DB>\r\n"
        		+ "            <arg0>"+rfid+ "</arg0>\r\n"
        		+ "        </con:checkRFID_IN_DB>\r\n"
        		+ "    </x:Body>\r\n"
        		+ "</x:Envelope>";
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            System.out.println(outputString);
            System.out.println("");
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:checkRFID_IN_DBResponse");
            webServiceResponse += nodeLst.item(0).getTextContent();
            
              
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return Boolean.valueOf(webServiceResponse);
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
				if (!(checkRFID(rfid))) {
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
