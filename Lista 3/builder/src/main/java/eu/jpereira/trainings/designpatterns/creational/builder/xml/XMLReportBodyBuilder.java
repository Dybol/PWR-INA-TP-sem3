package eu.jpereira.trainings.designpatterns.creational.builder.xml;

import eu.jpereira.trainings.designpatterns.creational.builder.ReportBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;

//specific builder
public class XMLReportBodyBuilder implements ReportBuilder {

	private XMLReportBody xmlReportBody;

	public XMLReportBodyBuilder() {
		this.xmlReportBody = new XMLReportBody();
	}

	@Override
	public void setName(String name) {
		xmlReportBody.putContent("<sale><customer><name>");
		xmlReportBody.putContent(name);
		xmlReportBody.putContent("</name>");
	}

	@Override
	public void setPhone(String phone) {
		xmlReportBody.putContent("<phone>");
		xmlReportBody.putContent(phone);
		xmlReportBody.putContent("</phone></customer>");
	}

	@Override
	public void addItem(String itemName, int quantity, double price) {
		xmlReportBody.putContent("<item><name>");
		xmlReportBody.putContent(itemName);
		xmlReportBody.putContent("</name><quantity>");
		xmlReportBody.putContent(quantity);
		xmlReportBody.putContent("</quantity><price>");
		xmlReportBody.putContent(price);
		xmlReportBody.putContent("</price></item>");
	}

	@Override
	public void startItems() {
		xmlReportBody.putContent("<items>");

	}

	@Override
	public void endItems() {
		xmlReportBody.putContent("</items></sale>");
	}

	@Override
	public ReportBody getReportBody() {
		return xmlReportBody;
	}
}
