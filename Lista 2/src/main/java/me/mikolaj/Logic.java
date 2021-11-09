package me.mikolaj;

import me.mikolaj.client.Client;
import me.mikolaj.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//klasa odpowiadajaca za logike aplikacji
@Component
public class Logic {

	private final ClientService clientService;

	private final Scanner scanner;

	//wstrzykujemy skaner i serwis dla klienta
	@Autowired
	public Logic(final ClientService clientService, final Scanner scanner) {
		this.clientService = clientService;
		this.scanner = scanner;
	}

	//metoda odpowiadajaca za dzialanie aplikacji, wybieranie opcji itp.
	public void loop() {
		createClient(scanner);
		while (true) {
			System.out.println("Wybierz akcje, ktore checsz podjac");
			System.out.println("1 - Wprowadz kolejnego klienta");
			System.out.println("2 - Stworz fakture");
			System.out.println("3 - Wyswietl faktury danego klienta");
			System.out.println("4 - Zakoncz dzialanie programu");
			final int choose = scanner.nextInt();
			scanner.nextLine();

			switch (choose) {
				case 1:
					createClient(scanner);
					break;
				case 2:
					System.out.println("Podaj ID klienta, ktoremu chcesz przypisac dana fakture");
					createInvoice(scanner, clientService.findById(scanner.nextInt()));
					break;
				case 3:
					System.out.println("Podaj ID klienta, dla ktorego chcesz wyswietlic faktury");
					System.out.println(clientService.findAllClientInvoices(scanner.nextInt()));
					scanner.nextLine();
					break;
				case 4:
					System.out.println("Zakonczono dzialanie.");
					return;
				default:
					System.out.println("Wybierz poprawną opcję!");
					return;
			}
		}
	}

	//tworzymy nowego klienta
	public static void createClient(final Scanner scanner) {

		System.out.println("Podaj imie klienta: ");
		final String firstName = scanner.nextLine();
		System.out.println("Podaj nazwisko klienta: ");
		final String lastName = scanner.nextLine();
		final Client client = new Client(firstName, lastName);
		System.out.println("Stworzyles klienta o id: " + client.getId());
	}

	//tworzymy nowa fakture
	public static void createInvoice(final Scanner scanner, final Client client) {
		scanner.nextLine();
		System.out.println("Podaj nazwe produktu");
		String productName = scanner.nextLine();
		final List<LineItem> items = new ArrayList<>();
		int flag = 1;
		while (flag != 0) {
			System.out.println("Podaj cene produktu");
			final Double productPrice = scanner.nextDouble();
			//scanner.nextLine();
			final Product product = new Product(productName, productPrice);
			System.out.println("Podaj ilosc produktow");
			final Integer quantity = scanner.nextInt();
			scanner.nextLine();

			final LineItem lineItem = new LineItem(quantity, product);
			items.add(lineItem);

			System.out.println("Podaj nazwe kolejnego produktu. Jezeli chcesz zakonczyc wprowadzanie, wpisz 0");
			productName = scanner.nextLine();
			if (productName.equals("0")) {
				flag = 0;
			}
		}

		final Invoice invoice = new Invoice(items, client);
		client.addInvoice(invoice);
	}
}
