package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;

//specific builder
public class HTMLReportBodyBuilder implements ReportBuilder{

	private HTMLReportBody htmlReportBody;

	public HTMLReportBodyBuilder() {
		this.htmlReportBody = new HTMLReportBody();
	}

	//uzupelnione wg tresci z ReportAssember (byly ify na kazdy typ dokumentu i okreslenie, jak ma sie zachowywac)
	@Override
	public void setName(String name) {
		htmlReportBody.putContent("<span class=\"customerName\">");
		htmlReportBody.putContent(name);
		htmlReportBody.putContent("</span>");
	}

	@Override
	public void setPhone(String phone) {
		htmlReportBody.putContent("<span class=\"customerPhone\">");
		htmlReportBody.putContent(phone);
		htmlReportBody.putContent("</span>");
	}

	@Override
	public void addItem(String itemName, int quantity, double price) {
		htmlReportBody.putContent("<item><name>");
		htmlReportBody.putContent(itemName);
		htmlReportBody.putContent("</name><quantity>");
		htmlReportBody.putContent(quantity);
		htmlReportBody.putContent("</quantity><price>");
		htmlReportBody.putContent(price);
		htmlReportBody.putContent("</price></item>");
	}

	@Override
	public void startItems() {
		htmlReportBody.putContent("<items>");
	}

	@Override
	public void endItems() {
		htmlReportBody.putContent("</items>");

	}

	@Override
	public ReportBody getReportBody() {
		return htmlReportBody;
	}
}
