define USAGE
Commands:
	mvn			run mvn install to build docker images
	registry	start local docker registry
	push		push locak images to local docker registry
	up			docker compose up
	down		docker compose down
	up-dev		docker compose up dev only
	down-dev	docker compose down dev only
	list		list service urls

endef
export USAGE

define SERVICE_LIST
Services:
	zipkin			http://localhost:9411/zipkin/
	keycloak		http://localhost:8445
	admin			http://localhost:8093
	eureka			http://localhost:8761

endef
export SERVICE_LIST


help:
	@echo "$$USAGE"

mvn:
	mvn -P prod -DskipTests=true -f ./microservice/pom.xml install

up:
	docker compose -f ./docker/docker-compose.yml --profile common --profile app up -d

down:
	docker compose -f ./docker/docker-compose.yml --profile common --profile app down
	
up-dev:
	docker compose -f ./docker/docker-compose.yml --profile common up -d

down-dev:
	docker compose -f ./docker/docker-compose.yml --profile common down

list:
	@echo "$$SERVICE_LIST"
