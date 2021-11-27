/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.structural.facade;

import eu.jpereira.trainings.designpatterns.structural.facade.service.*;

/**
 * @author windows
 *
 */
public interface BookstoreFacade {

	/**
	 * @param customerId
	 * @param isbn
	 */
	public void placeOrder(String customerId, String isbn);

	public void setBookService(BookDBService bookDBService);

	public void setCustomerService(CustomerDBService customerDBService);

	public void setNotificationService(CustomerNotificationService customerNotificationService);

	public void setWarehouseService(WharehouseService wharehouseService);

	public void setOrderingService(OrderingService orderingService);
}
