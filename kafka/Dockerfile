FROM confluentinc/cp-kafka:7.5.0

# Copy init script for topic creation
COPY create-topic.sh /usr/bin/create-topic.sh
# RUN chmod +x /usr/bin/create-topic.sh

CMD ["bash", "-c", "/etc/confluent/docker/run & sleep 10 && /usr/bin/create-topic.sh && wait"]