package com.demo.spring.Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.DataConfig;
import com.demo.spring.DTO.EmployeeDTO;

@RestController
public class RootController {
	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String url;

	private Logger logger = Logger.getLogger(RootController.class.toString());

	@RequestMapping("/")
	public ModelAndView hello() {
		// return new JSONPObject(HttpResponse.Ok, password);
		return new ModelAndView("redirect:/index.html");
	}

	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDTO>> getData(@RequestParam Integer employeeId) {
		logger.log(Level.INFO, "Getting Data...");
		List<EmployeeDTO> result = this.testGetData(employeeId);
		if (result != null) {
			return new ResponseEntity<List<EmployeeDTO>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<EmployeeDTO>>(new ArrayList<EmployeeDTO>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/saveData", method = RequestMethod.GET)
	public ResponseEntity<String> saveData(@RequestParam Integer employeeId, String firstName, String lastName) {
		Boolean successful = this.testSaveData(employeeId, firstName, lastName);
		if (successful) {
			return new ResponseEntity<String>("Successful save.", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Unable to connect to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<EmployeeDTO> testGetData(Integer employeeId) {
		DataConfig dataConfig = new DataConfig();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();

		try {
			DataSource ds = dataConfig.getDataSource(username, password, url);
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select first_name, last_name from hr.employees where employee_id=" + employeeId);
			while (rs.next()) {
				EmployeeDTO employee = new EmployeeDTO();
				employee.setId(employeeId);
				employee.setFirstName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
				employees.add(employee);
			}
			logger.log(Level.INFO, "Queried DB");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.INFO, "SQL Exception");
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employees;

	}

	private Boolean testSaveData(Integer employeeId, String firstName, String lastName) {
		DataConfig dataConfig = new DataConfig();
		Connection con = null;
		Statement stmt = null;
		try {
			DataSource ds = dataConfig.getDataSource(username, password, url);
			con = ds.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE hr.employees SET first_name = '" + firstName + "', last_name = '" + lastName
					+ "' WHERE employee_id = " + employeeId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}