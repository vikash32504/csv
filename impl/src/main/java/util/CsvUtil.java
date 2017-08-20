/*
 * Copyright © 2016 me and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.Elements;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.ElementsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.elements.Element;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.csv.rev150105.elements.ElementBuilder;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifierWithPredicates;
import org.opendaylight.yangtools.yang.data.api.schema.ContainerNode;
import org.opendaylight.yangtools.yang.data.api.schema.MapEntryNode;
import org.opendaylight.yangtools.yang.data.api.schema.MapNode;
import org.opendaylight.yangtools.yang.data.api.schema.stream.NormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.api.schema.stream.NormalizedNodeWriter;
import org.opendaylight.yangtools.yang.data.impl.codec.xml.XMLStreamNormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.impl.schema.Builders;
import org.opendaylight.yangtools.yang.data.impl.schema.ImmutableNodes;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.api.CollectionNodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import csv.impl.CsvProvider;

public class CsvUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CsvProvider.class);

	private static final org.opendaylight.yangtools.yang.common.QName IL_ATOMIC_NUMBER = QName.create(Element.QNAME,
			"atomic_number");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ELEMENT = QName.create(Element.QNAME,
			"element_name");
	private static final org.opendaylight.yangtools.yang.common.QName IL_SYMBOL = QName.create(Element.QNAME, "symbol");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ATOMIC_WEIGHT = QName.create(Element.QNAME,
			"atomic_weight");
	private static final org.opendaylight.yangtools.yang.common.QName IL_PERIOD = QName.create(Element.QNAME, "period");

	private static final org.opendaylight.yangtools.yang.common.QName IL_GROUP = QName.create(Element.QNAME, "group");
	private static final org.opendaylight.yangtools.yang.common.QName IL_PHASE = QName.create(Element.QNAME, "phase");

	private static final org.opendaylight.yangtools.yang.common.QName IL_MOST_STABLE_CRYSTAL = QName
			.create(Element.QNAME, "most_stable_crystal");
	private static final org.opendaylight.yangtools.yang.common.QName IL_IONIC_RADIUS = QName.create(Element.QNAME,
			"ionic_radius");
	private static final org.opendaylight.yangtools.yang.common.QName IL_TYPE = QName.create(Element.QNAME, "type");

	private static final org.opendaylight.yangtools.yang.common.QName IL_ATOMIC_RADIUS = QName.create(Element.QNAME,
			"atomic_radius");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ELECTRONEGATIVITY = QName.create(Element.QNAME,
			"electronegativity");
	private static final org.opendaylight.yangtools.yang.common.QName IL_FIRST_IONIZATION_POTENTIAL = QName
			.create(Element.QNAME, "first_ionization_potential");

	private static final org.opendaylight.yangtools.yang.common.QName IL_DENSITY = QName.create(Element.QNAME,
			"density");
	private static final org.opendaylight.yangtools.yang.common.QName IL_MELTING_POINT = QName.create(Element.QNAME,
			"melting_point");
	private static final org.opendaylight.yangtools.yang.common.QName IL_BOILING_POINT = QName.create(Element.QNAME,
			"boiling_point");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ISOTOPES = QName.create(Element.QNAME,
			"isotopes");

	private static final org.opendaylight.yangtools.yang.common.QName IL_DISCOVERER = QName.create(Element.QNAME,
			"discoverer");
	private static final org.opendaylight.yangtools.yang.common.QName IL_YEAR_OF_DISCOVERY = QName.create(Element.QNAME,
			"year_of_discovery");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ISPECIFIC_HEAT_CAPACITY = QName
			.create(Element.QNAME, "specific_heat_capacity");

	private static final org.opendaylight.yangtools.yang.common.QName IL_DISPLAY_COLUMN = QName.create(Element.QNAME,
			"display_column");
	private static final org.opendaylight.yangtools.yang.common.QName IL_DISPLAY_ROW = QName.create(Element.QNAME,
			"display_row");
	private static final org.opendaylight.yangtools.yang.common.QName IL_ELECTRON_CONFIGURATION = QName
			.create(Element.QNAME, "electron_configuration");

	public static void createJsonFile(Elements csvElements, String folderPath) {
		try {
			String filePath = new StringBuilder().append(folderPath).append(File.separator).append("elements.json")
					.toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(filePath), csvElements);
		} catch (IOException e) {
			LOG.error("Exception while writing to the JSON File", e);
		}

	}

	public static String createOutPutFolder() {
		String folderPath = new StringBuilder().append("src").append(File.separator).append("main")
				.append(File.separator).append("resources").append(File.separator).toString();

		File folder = new File(folderPath);
		folder.mkdirs();
		return folderPath;
	}

	public static void createXmlFile(Elements csvElements, String folderPath) {
		try {
			String filePath = new StringBuilder().append(folderPath).append(File.separator).append("elements.xml")
					.toString();

			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
			OutputStream writer = new FileOutputStream(new File(filePath));
			XMLStreamWriter xmlwriter = outputFactory.createXMLStreamWriter(writer);
			final NormalizedNodeStreamWriter nodeStreamWrite = XMLStreamNormalizedNodeStreamWriter
					.createSchemaless(xmlwriter);
			final NormalizedNodeWriter normalizedNodeWriter = NormalizedNodeWriter.forStreamWriter(nodeStreamWrite);

			CollectionNodeBuilder<MapEntryNode, MapNode> cnb = Builders.mapBuilder()
					.withNodeIdentifier(new NodeIdentifier(Element.QNAME));
			for (Element elm : csvElements.getElement()) {
				MapEntryNode n = ImmutableNodes.mapEntryBuilder()
						.withNodeIdentifier(new NodeIdentifierWithPredicates(Element.QNAME, IL_ATOMIC_NUMBER,
								elm.getAtomicNumber()))
						.withChild(ImmutableNodes.leafNode(IL_ATOMIC_NUMBER, elm.getAtomicNumber()))
						.withChild(ImmutableNodes.leafNode(IL_ELEMENT, elm.getElementName()))
						.withChild(ImmutableNodes.leafNode(IL_ATOMIC_RADIUS, elm.getAtomicRadius()))
						.withChild(ImmutableNodes.leafNode(IL_ATOMIC_WEIGHT, elm.getAtomicWeight()))
						.withChild(ImmutableNodes.leafNode(IL_BOILING_POINT, elm.getBoilingPoint()))
						.withChild(ImmutableNodes.leafNode(IL_DENSITY, elm.getDensity()))
						.withChild(ImmutableNodes.leafNode(IL_DISCOVERER, elm.getDiscoverer()))
						.withChild(ImmutableNodes.leafNode(IL_DISPLAY_COLUMN, elm.getDispColmn()))
						.withChild(ImmutableNodes.leafNode(IL_DISPLAY_ROW, elm.getDispRow()))
						.withChild(ImmutableNodes.leafNode(IL_ELECTRONEGATIVITY, elm.getElecNeg()))
						.withChild(ImmutableNodes.leafNode(IL_ELECTRON_CONFIGURATION, elm.getElecConfig()))
						.withChild(ImmutableNodes.leafNode(IL_FIRST_IONIZATION_POTENTIAL, elm.getFirstIonPot()))
						.withChild(ImmutableNodes.leafNode(IL_GROUP, elm.getGroup()))
						.withChild(ImmutableNodes.leafNode(IL_IONIC_RADIUS, elm.getIonicRadius()))
						.withChild(ImmutableNodes.leafNode(IL_ISOTOPES, elm.getIsotopes()))
						.withChild(ImmutableNodes.leafNode(IL_ISPECIFIC_HEAT_CAPACITY, elm.getSpeHeatCap()))
						.withChild(ImmutableNodes.leafNode(IL_MELTING_POINT, elm.getMeltingPoint()))
						.withChild(ImmutableNodes.leafNode(IL_MOST_STABLE_CRYSTAL, elm.getMstStableCryst()))
						.withChild(ImmutableNodes.leafNode(IL_PERIOD, elm.getPeriod()))
						.withChild(ImmutableNodes.leafNode(IL_PHASE, elm.getPhase()))
						.withChild(ImmutableNodes.leafNode(IL_SYMBOL, elm.getSymbol()))
						.withChild(ImmutableNodes.leafNode(IL_TYPE, elm.getTyp()))
						.withChild(ImmutableNodes.leafNode(IL_YEAR_OF_DISCOVERY, elm.getYearOfDiscovery())).build();

				cnb.withChild(n);

			}

			MapNode elemListNode = cnb.build();
			ContainerNode mainNode = Builders.containerBuilder().withNodeIdentifier(new NodeIdentifier(Elements.QNAME))
					.withChild(elemListNode).build();
			normalizedNodeWriter.write(mainNode);
			normalizedNodeWriter.flush();

		} catch (Exception e) {
			LOG.error("Exception occured while creating xml file",e);
		}
	}

	public static Elements readCSVfile() {
		LOG.info("Reading CSV FILE");

		String filename = "Periodic Table of Elements.csv";
		List<Element> input = new ArrayList<Element>();
		try {
			InputStream inputFS = CsvUtil.class.getClassLoader().getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
			input = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			br.close();
		} catch (IOException e) {
			LOG.error("Error Occured while reading the csv file", e);
		}
		LOG.info("DONE Reading CSV FILE");
		Elements result = new ElementsBuilder().setElement(input).build();
		LOG.info((new StringBuilder().append("Printing data =").append(result)).toString());
		return (result);
	}

	private static Function<String, Element> mapToItem = (line) -> {
		String[] p = line.split(",");
		ElementBuilder element = new ElementBuilder();
		element.setAtomicNumber(p[0].trim());
		element.setElementName(p[1].trim());
		element.setSymbol(p[2].trim());
		element.setAtomicWeight(p[3].trim());
		element.setPeriod(p[4].trim());
		element.setGroup(p[5].trim());
		element.setPhase(p[6].trim());
		element.setMstStableCryst(p[7].trim());
		element.setTyp(p[8].trim());
		element.setIonicRadius(p[9].trim());
		element.setAtomicRadius(p[10].trim());
		element.setElecNeg(p[11].trim());
		element.setFirstIonPot(p[12].trim());
		element.setDensity(p[13].trim());
		element.setMeltingPoint(p[14].trim());
		element.setBoilingPoint(p[15].trim());
		element.setIsotopes(p[16].trim());
		element.setDiscoverer(p[17].trim());
		element.setYearOfDiscovery(p[18].trim());
		element.setSpeHeatCap(p[19].trim());
		element.setElecConfig(p[20].trim());
		element.setDispRow(p[21].trim());
		element.setDispColmn(p[22].trim());
		return element.build();
	};

}
