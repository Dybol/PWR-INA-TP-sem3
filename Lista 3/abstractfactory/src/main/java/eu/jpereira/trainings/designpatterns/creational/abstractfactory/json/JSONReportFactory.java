package eu.jpereira.trainings.designpatterns.creational.abstractfactory.json;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.Report;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportHeader;

public class JSONReportFactory extends Report {

	public JSONReportFactory() {
		super("JSON");
	}

	@Override
	public ReportBody createBody() {
		return new JSONReportBody();
	}

	@Override
	public ReportFooter createFooter() {
		return new JSONReportFooter();
	}

	@Override
	public ReportHeader createHeader() {
		return new JSONReportHeader();
	}
}
