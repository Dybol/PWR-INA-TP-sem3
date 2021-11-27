package eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.Report;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportHeader;

public class XMLReportFactory extends Report {

	public XMLReportFactory() {
		super("XML");
	}

	@Override
	public ReportBody createBody() {
		return new XMLReportBody();
	}

	@Override
	public ReportFooter createFooter() {
		return new XMLReportFooter();
	}

	@Override
	public ReportHeader createHeader() {
		return new XMLReportHeader();
	}
}
