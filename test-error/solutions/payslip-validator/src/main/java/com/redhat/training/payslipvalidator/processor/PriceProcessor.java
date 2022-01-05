package com.redhat.training.payslipvalidator.processor;

import static org.apache.camel.builder.xml.XPathBuilder.xpath;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PriceProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		double payslipTotal = Double.parseDouble(
				xpath(
					"/payslip/totalPayslip/text()"
				).evaluate(exchange, String.class)
		);

		NodeList payslipItems = xpath(
				"/payslip/payslipItems/node()"
		).evaluate(exchange, NodeList.class);

		double calculatedTotal = 0;

		for (int i = 0; i < payslipItems.getLength(); i++) {
			if (payslipItems.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element payslipItem = (Element) payslipItems.item(i);

				int qty = Integer.parseInt(
						payslipItem.getElementsByTagName("payslipItemQty")
								.item(0).getTextContent()
				);

				double price = Double.parseDouble(
						payslipItem.getElementsByTagName("payslipItemPrice")
								.item(0).getTextContent()
				);

				calculatedTotal += (qty * price);
			}
		}

		if (payslipTotal != calculatedTotal) {
			throw new WrongTotalPayslipCalculation();
		}

		exchange.getIn().setHeader("totalPrice", calculatedTotal);
	}
}
