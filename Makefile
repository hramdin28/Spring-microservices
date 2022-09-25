define USAGE
Commands:
	mvn			run mvn install to build docker images
	registry    start local docker registry
	push        push locak images to local docker registry
	up			docker-compose up
	down		docker-compose down
	list		list service urls

endef
export USAGE

define SERVICE_LIST
Services:
	zipkin			http://localhost:9411/zipkin/
	keycloak		http://localhost:8445

endef
export SERVICE_LIST


help:
	@echo "$$USAGE"

mvn:
	mvn -P prod -DskipTests=true -f ./discovery/pom.xml install
	mvn -P prod -DskipTests=true -f ./spring-cloud-config/pom.xml install
	mvn -P prod -DskipTests=true -f ./admin/pom.xml install
	mvn -P prod -DskipTests=true -f ./consumer/pom.xml install
	mvn -P prod -DskipTests=true -f ./producer/pom.xml install

up:
	docker-compose -f ./docker/docker-compose.yml  up -d

down:
	docker-compose -f ./docker/docker-compose.yml down

list:
	@echo "$$SERVICE_LIST"
