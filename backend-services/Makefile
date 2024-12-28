review-service-build-image:
	./gradlew review-service:bootBuildImage

review-service-start-image:
	docker run \
	-e RABBITMQ_HOST=localhost \
	-e RABBITMQ_PORT= \
	-e RABBITMQ_USERNAME= \
	-e RABBITMQ_PASSWORD= \
	-e R2DBC_HOST= \
	-e R2DBC_PORT= \
	-e R2DBC_DB_NAME= \
	-e R2DBC_USERNAME= \
	-e R2DBC_PASSWORD= \
	-e KEYCLOAK_HOST= \
	-e KEYCLOAK_PORT= \
	-e SPRING_PROFILES_ACTIVE=dev \
	-p 8080:8002 \
	review-service:0.0.1-SNAPSHOT

