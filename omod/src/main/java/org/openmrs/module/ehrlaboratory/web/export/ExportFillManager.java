/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.ehrlaboratory.web.export;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.openmrs.module.ehrlaboratory.util.TestModel;

public class ExportFillManager {
	/**
	 * Fills the report with content
	 * 
	 * @param worksheet
	 * @param startRowIndex
	 *            starting row offset
	 * @param startColIndex
	 *            starting column offset
	 * @param datasource
	 *            the data source
	 */
	public static void fillReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, List<TestModel> datasource) {
		// Row offset
		startRowIndex += 2;

		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);

		// Create body
		for (int i = startRowIndex; i + startRowIndex - 2 < datasource.size() + 2; i++) {

			// Create a new row
			HSSFRow row = worksheet.createRow((short) i + 1);

			// Retrieve the Accepted Date
			HSSFCell cell1 = row.createCell(startColIndex + 0);
			cell1.setCellValue(datasource.get(i - 2).getAcceptedDate());
			cell1.setCellStyle(bodyCellStyle);

			// Retrieve the Patient Identifier
			HSSFCell cell2 = row.createCell(startColIndex + 1);
			cell2.setCellValue(datasource.get(i - 2).getPatientIdentifier());
			cell2.setCellStyle(bodyCellStyle);

			// Retrieve the Name of Patient
			HSSFCell cell3 = row.createCell(startColIndex + 2);
			cell3.setCellValue(datasource.get(i - 2).getPatientName());
			cell3.setCellStyle(bodyCellStyle);

			// Retrieve the Age of Patient
			HSSFCell cell4 = row.createCell(startColIndex + 3);
			cell4.setCellValue(datasource.get(i - 2).getAge());
			cell4.setCellStyle(bodyCellStyle);

			// Retrieve the Gender of Patient
			HSSFCell cell5 = row.createCell(startColIndex + 4);
			cell5.setCellValue(datasource.get(i - 2).getGender());
			cell5.setCellStyle(bodyCellStyle);

			// Retrieve the Sample Id
			HSSFCell cell6 = row.createCell(startColIndex + 5);
			cell6.setCellValue(datasource.get(i - 2).getSampleId());
			cell6.setCellStyle(bodyCellStyle);

			// Retrieve the Name of Investigation
			HSSFCell cell7 = row.createCell(startColIndex + 6);
			cell7.setCellValue(datasource.get(i - 2).getInvestigation());
			cell7.setCellStyle(bodyCellStyle);

			// Retrieve the Name of Test
			HSSFCell cell8 = row.createCell(startColIndex + 7);
			cell8.setCellValue(datasource.get(i - 2).getTest().getName()
					.getName());
			cell8.setCellStyle(bodyCellStyle);

			// Retrieve the Name of Test name
			HSSFCell cell9 = row.createCell(startColIndex + 8);
			cell9.setCellValue(datasource.get(i - 2).getTestName().getName()
					.getName());
			cell9.setCellStyle(bodyCellStyle);

			// Retrieve the Test Result
			HSSFCell cell10 = row.createCell(startColIndex + 9);
			cell10.setCellValue(datasource.get(i - 2).getValue());
			cell10.setCellStyle(bodyCellStyle);

		}
	}
}
