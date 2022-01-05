package com.redhat.training.payslipvalidator.processor;

import static org.apache.camel.builder.xml.XPathBuilder.xpath;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.NodeList;

public class AmountProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		NodeList result = xpath(
				"/payslip/payslipItems/payslipItem/payslipItemQty/text()"
		).evaluate(exchange, NodeList.class);

		int total = 0;

		for (int i = 0; i < result.getLength(); i++) {
			int value = Integer.parseInt(result.item(i).getNodeValue());
			total += value;
		}

		exchange.getIn().setHeader("totalPayslipUnits", total);
	}
}
