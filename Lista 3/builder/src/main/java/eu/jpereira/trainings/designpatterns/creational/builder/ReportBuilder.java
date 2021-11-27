package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;

//main builder
public interface ReportBuilder {

	public void setName(String name);
	public void setPhone(String phone);
	public void addItem(String itemName, int quantity, double price);
	public void startItems();
	public void endItems();
	public ReportBody getReportBody();


}
