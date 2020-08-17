package com.redhat.bobbycar.carsim.cars;

import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.bobbycar.carsim.routes.RoutePoint;
import javax.annotation.Generated;

public class Car {
	private final String model;
	private final String manufacturer;
	private final Engine engine;
	private final UUID driverId;
	private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);

	@Generated("SparkTools")
	private Car(Builder builder) {
		this.model = builder.model;
		this.manufacturer = builder.manufacturer;
		this.driverId = builder.driverId;
		try {
			this.engine = new TimedEngine(5, builder.startingPoint, new JsonEngineConfiguration(), builder.metrics);
		} catch (FileNotFoundException e) {
			throw new EngineException("Engine configuration could not be loaded", e);
		}
		
	}

	public void start(Executor executor) {
		LOGGER.debug("Starting engine");
		CompletableFuture.runAsync(this.engine, executor);
	}
	
	public Car driveTo(RoutePoint target) {
		LOGGER.debug("Driving to {}", target);
		engine.nextRoutePoint(target);
		return this;
	}

	public String getModel() {
		return model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public UUID getDriverId() {
		return driverId;
	}

	/**
	 * Creates builder to build {@link Car}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String model;
		private String manufacturer;
		private UUID driverId;
		private RoutePoint startingPoint;
		private EngineMetrics metrics;

		private Builder() {
		}

		public Builder withModel(String model) {
			this.model = model;
			return this;
		}

		public Builder withManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public Builder withDriverId(UUID driverId) {
			this.driverId = driverId;
			return this;
		}
		
		public Builder withStartingPoint(RoutePoint startingPoint) {
			this.startingPoint = startingPoint;
			return this;
		}
		
		public Builder withMetrics(EngineMetrics metrics) {
			this.metrics = metrics;
			return this;
		}

		public Car build() {
			return new Car(this);
		}
	}
	
	
}