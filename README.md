# scaling-game

# Event Processing System (Practice Project)

## 1. Purpose

This project is designed for practicing **scaling** and **optimizing**
applications.\
It simulates a small event-driven system with several components:

-   **eventProducer** -- generates mock events (device weather data) and
    publishes them into Kafka.
-   **Kafka** -- used as the message broker and event bus.
-   **weatherEventProcessor** -- consumes events from Kafka, processes them,
    and writes results into PostgreSQL.\
    This service is the **main target for scaling and optimization**.
-   **PostgreSQL** -- stores processed events.

The focus of the project is to improve the throughput of the
`weatherEventProcessor` service and experiment with different strategies for
high-load scenarios.

------------------------------------------------------------------------

## 2. How to Run

### Run the Database

``` bash
cd db
./run_db.sh
```

### Run Kafka

``` bash
cd kafka
docker compose up -d
```

This will start Kafka and create topic device_events.

### Run Producer and Consumer

Both `eventProducer` and `weatherEventProcessor` are Spring Boot apps.\
You can run them as normal Spring Boot applications, either from your
IDE or via:

``` bash
./mvnw spring-boot:run
```

or

``` bash
java -jar target/<app-name>.jar
```

------------------------------------------------------------------------

## 3. Goal of the Exercise

The main challenge is to **increase the number of processed events per
second** in the system.

You can experiment with different approaches.

The system is intentionally simple at first so you can apply
increasingly advanced techniques and measure improvements.

------------------------------------------------------------------------

## 4. Monitoring Inserts in Database

Inside the **weatherEventProcessor** module there is a class:

`DatabaseMonitoringScheduler`

-   It periodically **measures how many inserts were performed into the
    database** during a given interval.
-   The interval can be **configured in the application properties
    file**.
-   This helps to evaluate throughput and track optimizations when
    scaling the system.

This component is useful to understand the **performance impact** of
each optimization step.

------------------------------------------------------------------------