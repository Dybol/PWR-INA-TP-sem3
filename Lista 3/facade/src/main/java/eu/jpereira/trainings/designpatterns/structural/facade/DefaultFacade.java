package eu.jpereira.trainings.designpatterns.structural.facade;

import eu.jpereira.trainings.designpatterns.structural.facade.model.Book;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Customer;
import eu.jpereira.trainings.designpatterns.structural.facade.model.DispatchReceipt;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Order;
import eu.jpereira.trainings.designpatterns.structural.facade.service.*;

public class DefaultFacade implements BookstoreFacade {

	CustomerDBService customerService;
	BookDBService bookService;
	OrderingService orderingService;
	WharehouseService warehouseService;
	CustomerNotificationService customerNotificationService;


	@Override
	public void placeOrder(String customerId, String isbn) {

		Book book = bookService.findBookByISBN(isbn);
		Customer customer = customerService.findCustomerById(customerId);
		Order order = orderingService.createOrder(customer, book);
		customerNotificationService.notifyClient(order);
		DispatchReceipt dummyDispatchReceipt = warehouseService.dispatch(order);
		customerNotificationService.notifyClient(dummyDispatchReceipt);
		

	}

	@Override
	public void setCustomerService(CustomerDBService customerService) {
		this.customerService = customerService;

	}

	@Override
	public void setBookService(BookDBService bookService) {
		this.bookService = bookService;

	}

	@Override
	public void setOrderingService(OrderingService orderingService) {

		this.orderingService = orderingService;
	}

	@Override
	public void setWarehouseService(WharehouseService wharehouseService) {
		this.warehouseService = wharehouseService;

	}

	@Override
	public void setNotificationService(CustomerNotificationService customerNotificationService) {
		this.customerNotificationService = customerNotificationService;

	}
}