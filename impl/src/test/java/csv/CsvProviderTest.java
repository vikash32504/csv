/*
 * Copyright © 2016 me and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package csv;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.Elements;

import util.CsvUtil;

public class CsvProviderTest {

	@Test
    public void testFolderCreatedCreated() {
		String folder = CsvUtil.createOutPutFolder();
		File file = new File(folder);
		assertTrue(file.exists());
	  }

	@Test
    public void testXMLCreatedCreated() throws Exception{
		Elements csvElements = CsvUtil.readCSVfile();
		String folder = CsvUtil.createOutPutFolder();
		CsvUtil.createXmlFile(csvElements,folder);
		String filePath = new StringBuilder().append(folder).append(File.separator).append("elements.xml")
				.toString();
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream( file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line =reader.readLine();
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertTrue(line.contains("Hydrogen"));
		reader.close();

	  }

	@Test
    public void testJsonCreatedCreated() throws Exception{
		Elements csvElements = CsvUtil.readCSVfile();
		String folder = CsvUtil.createOutPutFolder();
		CsvUtil.createJsonFile(csvElements,folder);
		String filePath = new StringBuilder().append(folder).append(File.separator).append("elements.json")
				.toString();
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream( file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line =reader.readLine();
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertTrue(line.contains("Hydrogen"));
		reader.close();

	  }
}
