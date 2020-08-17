/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.util;

import java.util.Comparator;

import org.openmrs.Concept;

//ghanshyam 04/07/2012 New Requirement #277
public class TestModel implements Comparator<TestModel>, Comparable<TestModel> {

	private String startDate;
	private String patientIdentifier;
	private String patientName;
	private String gender;
	private Integer age;
	// ghanshyam 19/07/2012 New Requirement #309: [LABORATORY] Show Results in Print WorkList.introduced the column 'Lab' 'Test' 'Test name' 'Result'
	private Concept test;
	private Concept testName;
	private Integer orderId;
	private String status;
	private Integer testId;
	private String acceptedDate;
	private String investigation;
	private Integer encounterId;
	private Integer conceptId;
	private String sampleId;
	public String value;

	// ghanshyam 04/07/2012 New Requirement #277
	public TestModel() {
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPatientIdentifier() {
		return patientIdentifier;
	}

	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Concept getTest() {
		return test;
	}

	public void setTest(Concept test) {
		this.test = test;
	}

	public Concept getTestName() {
		return testName;
	}

	public void setTestName(Concept testName) {
		this.testName = testName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getInvestigation() {
		return investigation;
	}

	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	public Integer getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}

	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// ghanshyam 04/07/2012 New Requirement #277

	// Overriding the compareTo method
	public int compareTo(TestModel t) {
		return (this.patientName).compareTo(t.patientName);
	}

	// Overriding the compare method
	public int compare(TestModel t, TestModel t1) {
		return 0;
	}

}
