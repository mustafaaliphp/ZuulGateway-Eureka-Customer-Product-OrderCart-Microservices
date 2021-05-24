package com.customer.app.util;

import java.util.Collections;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

public class EurekaCustomerServiceInfoContributor implements InfoContributor {

	@Override
	public void contribute(Info.Builder builder) {
		builder.withDetail("details",
				Collections.singletonMap("description", "This is the Customer service, which is discovery server aware, and this service will Call product Microservice, for products details, which is again dicovery server aware!!! "));
	}
}