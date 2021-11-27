package eu.jpereira.trainings.designpatterns.creational.builder.json;

import eu.jpereira.trainings.designpatterns.creational.builder.ReportBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;

//specific builder
public class JSONReportBodyBuilder implements ReportBuilder {

	private JSONReportBody jsonReportBody;

	public JSONReportBodyBuilder() {
		this.jsonReportBody = new JSONReportBody();
	}

	@Override
	public void setName(String name) {
		jsonReportBody.putContent("sale:{customer:{");
		jsonReportBody.putContent("name:\"");
		jsonReportBody.putContent(name);
		jsonReportBody.putContent("\",");
	}

	@Override
	public void setPhone(String phone) {
		jsonReportBody.putContent("phone:\"");
		jsonReportBody.putContent(phone);
		jsonReportBody.putContent("\"},");
	}

	@Override
	public void addItem(String itemName, int quantity, double price) {
		jsonReportBody.putContent("{name:\"");
		jsonReportBody.putContent(itemName);
		jsonReportBody.putContent("\",quantity:");
		jsonReportBody.putContent(String.valueOf(quantity));
		jsonReportBody.putContent(",price:");
		jsonReportBody.putContent(String.valueOf(price));
		jsonReportBody.putContent("},");
	}

	@Override
	public void startItems() {
		jsonReportBody.putContent("items:[");
	}

	@Override
	public void endItems() {
		jsonReportBody.removeComma();
		jsonReportBody.putContent("]}");

	}

	@Override
	public ReportBody getReportBody() {
		return jsonReportBody;
	}
}
