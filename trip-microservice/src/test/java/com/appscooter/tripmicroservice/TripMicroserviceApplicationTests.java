package com.appscooter.tripmicroservice;

import com.appscooter.tripmicroservice.controllers.TripController;
import com.appscooter.tripmicroservice.services.dtos.trip.responses.TripResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class TripMicroserviceApplicationTests {

	private TripController controller;

	@Autowired
	public TripMicroserviceApplicationTests(TripController tp) {
		controller=tp;
	}

	@Test
	void testGetTrips() {
		List<TripResponseDTO> listResult = controller.findAllTrip();
		Assert.isTrue(listResult.isEmpty(), "vacia minga");
	}

}
