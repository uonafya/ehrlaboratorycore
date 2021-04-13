/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.db.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.openmrs.module.ehrlaboratory.db.LaboratoryDAO;
import org.openmrs.module.ehrlaboratory.util.LaboratoryConstants;

public class HibernateLaboratoryDAO implements LaboratoryDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//
	// LABORATORY DEPARTMENT
	//
	public Lab saveLaboratoryDepartment(Lab department) {
		return (Lab) sessionFactory.getCurrentSession().merge(department);
	}

	public Lab getLaboratoryDepartment(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Lab.class);
		criteria.add(Restrictions.eq("labId", id));
		return (Lab) criteria.uniqueResult();
	}

	public void deleteLabDepartment(Lab department) {
		sessionFactory.getCurrentSession().delete(department);
	}

	public Lab getDepartment(Role role) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Lab.class);
		criteria.add(Restrictions.eq("role", role));
		return (Lab) criteria.uniqueResult();
	}

	//
	// ORDER
	//
	public Integer countOrders(Date orderStartDate, OrderType orderType,
			Set<Concept> tests, List<Patient> patients) throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Order.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(orderStartDate) + " 00:00:00";
		String endDate = sdf.format(orderStartDate) + " 23:59:59";
		criteria.add(Restrictions.eq("orderType", orderType));
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("dateActivated",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));
		criteria.add(Restrictions.eq("voided", false));
		criteria.add(Restrictions.in("concept", tests));
		if (!CollectionUtils.isEmpty(patients))
			criteria.add(Restrictions.in("patient", patients));
		Number rs = (Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrders(Date dateActivated, OrderType orderType,
			Set<Concept> tests, List<Patient> patients, int page)
			throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Order.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(dateActivated) + " 00:00:00";
		String endDate = sdf.format(dateActivated) + " 23:59:59";
		criteria.add(Restrictions.eq("orderType", orderType));
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("dateActivated",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));
		criteria.add(Restrictions.eq("voided", false));
		criteria.add(Restrictions.in("concept", tests));
		//ghanshyam-kesav 16-08-2012 Bug #323 [BILLING] When a bill with a lab\radiology order is edited the order is re-sent
		criteria.add(Restrictions.isNull("dateVoided"));
		if (!CollectionUtils.isEmpty(patients)) {
			criteria.add(Restrictions.in("patient", patients));
			criteria.addOrder(org.hibernate.criterion.Order.asc("dateActivated"));
		}
		int firstResult = (page - 1) * LaboratoryConstants.PAGESIZE;
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(LaboratoryConstants.PAGESIZE);
		return criteria.list();
	}

	//
	// LABORATORY TEST
	//
	public LabTest saveLaboratoryTest(LabTest test) {
		return (LabTest) sessionFactory.getCurrentSession().merge(test);
	}

	public LabTest getLaboratoryTest(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		criteria.add(Restrictions.eq("labTestId", id));
		return (LabTest) criteria.uniqueResult();
	}

	public void deleteLaboratoryTest(LabTest test) {
		sessionFactory.getCurrentSession().delete(test);
	}

	public LabTest getLaboratoryTest(Order order) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		criteria.add(Restrictions.eq("order", order));
		return (LabTest) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLaboratoryTests(Date date, String status,
			Set<Concept> concepts, List<Patient> patients)
			throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("acceptDate",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.in("concept", concepts));
		if (!CollectionUtils.isEmpty(patients))
			criteria.add(Restrictions.in("patient", patients));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLaboratoryTestsByDate(Date date,
			Set<Concept> tests, List<Patient> patients) throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		criteria.add(Restrictions.in("concept", tests));

		if (!CollectionUtils.isEmpty(patients))
			criteria.add(Restrictions.in("patient", patients));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("acceptDate",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLaboratoryTestsByDiscontinuedDate(Date date,
			Set<Concept> tests, List<Patient> patients) throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		criteria.add(Restrictions.in("concept", tests));
		if (!CollectionUtils.isEmpty(patients)) {
			criteria.add(Restrictions.in("patient", patients));
		}
		Criteria orderCriteria = criteria.createCriteria("order");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		orderCriteria.add(Restrictions.between("dateStopped",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLaboratoryTestsByDateAndPatient(Date date,
			Patient patient) throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		Criteria orderCriteria = criteria.createCriteria("order");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		orderCriteria.add(Restrictions.between("dateStopped",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));
		criteria.add(Restrictions.eq("patient", patient));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLaboratoryTests(Date date, String sampleIdPattern)
			throws ParseException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("acceptDate",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));

		if (!StringUtils.isEmpty(sampleIdPattern)) {
			criteria.add(Restrictions.like("sampleNumber", sampleIdPattern));
		}

		criteria.addOrder(org.hibernate.criterion.Order.desc("labTestId"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrders(Patient patient, Date date, Concept concept)
			throws ParseException {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";

		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		criteria.add(Restrictions.between("dateActivated",
				dateTimeFormatter.parse(startDate),
				dateTimeFormatter.parse(endDate)));
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("concept", concept));
		return criteria.list();
	}

	public LabTest getLaboratoryTest(Encounter encounter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		criteria.add(Restrictions.eq("encounter", encounter));
		return (LabTest) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getAllTest() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				LabTest.class);
		return criteria.list();
	}
}
