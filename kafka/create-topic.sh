#!/bin/bash

kafka-topics \
  --create \
  --if-not-exists \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1 \
  --topic device_events
