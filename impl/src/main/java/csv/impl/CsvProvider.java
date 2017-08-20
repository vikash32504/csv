/*
 * Copyright © 2016 me and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package csv.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.CsvUtil;

public class CsvProvider {

	private static final Logger LOG = LoggerFactory.getLogger(CsvProvider.class);

	private final DataBroker dataBroker;



	public CsvProvider(final DataBroker dataBroker) {
		this.dataBroker = dataBroker;
	}

	/**
	 * Method called when the blueprint container is created.
	 */
	public void init() {
		LOG.info("CsvProvider Session Initiated");
		Elements csvElements = CsvUtil.readCSVfile();
		String folderPath = CsvUtil.createOutPutFolder();
		CsvUtil.createXmlFile( csvElements,folderPath);
		CsvUtil.createJsonFile( csvElements,folderPath);
	}


	/**
	 * Method called when the blueprint container is destroyed.
	 */
	public void close() {
		LOG.info("CsvProvider Closed");
	}
}