package com.javacoin.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javacoin.main.jmsUtilities.MessageReceiver;
import com.javacoin.main.jmsUtilities.MessageSender;
import com.javacoin.main.models.OrderRequest;

@SpringBootTest
class MainJavacoinApplicationTests {

	@Autowired
	MessageSender sender;

	@Autowired
	MessageReceiver receiver;
	
	// Test a modo de ejemplo
		/*
		 * Billetera javacoin
		 * DNI/JAVACOIN
		 *("98765432",2)
		 *("12345678",15)
		 *("41916580",40)
		 *
		 *Billetera dolares 
		 *DNI/DOLARES/OPERACIONES
		 *("41916580", 10, 5)
		 *("12345678", 30, 2)
		 *("87654321", 69, 5)
		 */
		
		// Con ambas aplicaciones encendidas ir probando los distintos test, es importante que tanto el banco como la billetera esten previamente prendidas ya que primero se envia un topico.
		// Los test estan pensados para ir corriendolos de a uno para que pasen exitosamente. Si el sistema ya cambio los valores podrian no funcionar
		// Test operacion exitosa 

	@Test
	public void testExitoso() {
		String dniComprador = "41916580";
		String dniVendedor = "12345678";
		double javacoinAComprar = 10;
		double cotizacionEnDolares = 5;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(100);

			// Vendedor recibe la oferta y responde
			orderRequest = receiver.getReceivedOrderRequest();
			if (orderRequest != null) {
				sender.sendSellerResponse(orderRequest.getId(), dniVendedor);
				System.out.println("vendedor responde");
			}

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
/*
	// Vendedor comprador no tiene dolares suficientes para comnprar
	@Test
	public void testFallaFaltanDolares() {
		String dniComprador = "41916580";
		String dniVendedor = "12345678";
		double javacoinAComprar = 10;
		double cotizacionEnDolares = 11; // Dato de color si se usa 10 tambien falla porque no contempla la comisión

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(100);

			// Vendedor recibe la oferta y responde
			orderRequest = receiver.getReceivedOrderRequest();
			if (orderRequest != null) {
				sender.sendSellerResponse(orderRequest.getId(), dniVendedor);
				System.out.println("vendedor responde");
			}

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Vendedor no tiene javacoin suficientes
	@Test
	public void testFallaVendedorJavacoinInsuficientes() {
		String dniComprador = "41916580";
		String dniVendedor = "12345678";
		double javacoinAComprar = 20;
		double cotizacionEnDolares = 2;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(100);

			// Vendedor recibe la oferta y responde
			orderRequest = receiver.getReceivedOrderRequest();
			if (orderRequest != null) {
				sender.sendSellerResponse(orderRequest.getId(), dniVendedor);
				System.out.println("vendedor responde");
			}

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Vendedor Inexistente (va hacer un rollback tanto en el banco como eliminara
	// la orden en la billetera)
	@Test
	public void testFallaVendedorInexistente() {
		String dniComprador = "41916580";
		String dniVendedor = "12345679";
		double javacoinAComprar = 10;
		double cotizacionEnDolares = 5;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(100);

			// Vendedor recibe la oferta y responde
			orderRequest = receiver.getReceivedOrderRequest();
			if (orderRequest != null) {
				sender.sendSellerResponse(orderRequest.getId(), dniVendedor);
				System.out.println("vendedor responde");
			}

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Se prueba asincornismo con dos trx en simultaneo (Ordenado)
	@Test
	public void testdobleTransaccion() {
		String dniComprador = "41916580";
		String dniVendedor = "12345678";
		double javacoinAComprar = 3;
		double cotizacionEnDolares = 2;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(1000);

			sender.sendSellerResponse(1, dniVendedor);
			sender.sendSellerResponse(2, dniVendedor);

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Se prueba asincornismo con dos trx en simultaneo (el vendedor responde en
	// desorden)
	@Test
	public void testdobleTransaccionDesorden() {
		String dniComprador = "41916580";
		String dniVendedor = "12345678";
		double javacoinAComprar = 3;
		double cotizacionEnDolares = 2;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			orderRequest = new OrderRequest(dniComprador, javacoinAComprar, cotizacionEnDolares);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(1000);

			sender.sendSellerResponse(2, dniVendedor);
			sender.sendSellerResponse(1, dniVendedor);

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Prueba doble transaccion
	@Test
	public void testdobleTransaccionDistintasCuentas() {
		String dniComprador1 = "41916580";
		String dniVendedor1 = "12345678";
		String dniComprador2 = "87654321";
		String dniVendedor2 = "41916580";
		double javacoinAComprar1 = 3;
		double cotizacionEnDolares1 = 2;
		double javacoinAComprar2 = 4;
		double cotizacionEnDolares2 = 10;

		try {
			OrderRequest orderRequest = new OrderRequest(dniComprador1, javacoinAComprar1, cotizacionEnDolares1);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			orderRequest = new OrderRequest(dniComprador2, javacoinAComprar2, cotizacionEnDolares2);
			// Comprador envia oferta
			System.out.println("envio" + orderRequest);
			sender.sendPurchaseRequest(orderRequest);

			// Espera la respuesta

			Thread.sleep(1000);

			sender.sendSellerResponse(1, dniVendedor1);
			sender.sendSellerResponse(2, dniVendedor2);

			// Espera la respuesta

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	*/
}
