package com.lorenzolerate.fizzbuzz_exercise.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lorenzolerate.fizzbuzz_exercise.bean.Fizzbuzz;

@Controller
public class FizzbuzzController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FizzbuzzController.class);

	// with this variable we only read from the configuration file once
	private static Integer maxValue = null;

	@RequestMapping(value = "/fizzbuzz/{input}")
	@ResponseBody
	public String convertToFizzBuzzWithInput(@PathVariable("input") int input) throws IOException {
		Fizzbuzz fizzbuzz = new Fizzbuzz();

		fizzbuzz.setStartValue(input);

		if (maxValue == null) {
			maxValue = getMaxValue();
		}

		fizzbuzz.setEndValue(maxValue);
		LOGGER.debug("Fizzbuzz max value got from configuration file: " + fizzbuzz.getEndValue());

		// if input is greater than max value return 'error' string
		if (input > fizzbuzz.getEndValue()) {
			LOGGER.warn("Start value (" + fizzbuzz.getEndValue() + ")+ is greater than end value ("
					+ fizzbuzz.getEndValue() + ")");
			// return value finished in ", " in order to truncate the result properly at the
			// end of the method
			return "The value of max value (" + fizzbuzz.getEndValue() + ") is smaller than min value ("
					+ String.valueOf(input) + "), ";
		}

		String resultString = "";
		for (Integer i = fizzbuzz.getStartValue(); i <= fizzbuzz.getEndValue(); i++) {

			// at this step input values are correct and proceed to convert numbers to
			// bizzbuzz
			if (i % 3 == 0 && i % 5 == 0) {
				resultString += "fizzbuzz, ";
			} else if (i % 3 == 0) {
				resultString += "fizz, ";
			} else if (i % 5 == 0) {
				resultString += "buzz, ";
			} else {
				resultString += i + ", ";
			}
		}
		// delete the latest 2 characters of the resulting string. the values comma and
		// space
		if (resultString.length() > 3) {
			resultString = resultString.substring(0, resultString.length() - 3);
		}
		return resultString;
	}

	private Integer getMaxValue() throws IOException {
		InputStream inputStream = null;
		Properties prop = new Properties();
		String propFileName = "application.properties";
		try {
			// load the configuration file into an InputStream
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				// load properties from properties file
				prop.load(inputStream);
				LOGGER.debug("Properties loaded successfully");
			} else {
				String errorMessage = "property file '" + propFileName + "' not found in the classpath";
				LOGGER.error(errorMessage);
				throw new FileNotFoundException(errorMessage);
			}

			// get the property value and print it out
			maxValue = Integer.valueOf(prop.getProperty("max-value"));

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return maxValue;
	}
}
