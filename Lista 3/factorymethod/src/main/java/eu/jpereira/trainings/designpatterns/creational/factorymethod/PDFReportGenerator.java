package eu.jpereira.trainings.designpatterns.creational.factorymethod;

public class PDFReportGenerator extends ReportGenerator {
	@Override
	protected Report create() {
		return new PDFReport();
	}
}
