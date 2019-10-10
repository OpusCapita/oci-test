.DEFAULT_GOAL := help

.PHONY: refresh-dependencies
refresh-dependencies: ## Install dependencies
	./build/grails/refresh-dependencies.sh

.PHONY: maven-deploy
maven-deploy: refresh-dependencies ## Build and deploy Maven artifacts
	./build/grails/maven-deploy.sh

.PHONY: docker-auth
docker-auth: ## Login to Dockerhub
	./build/docker/docker-auth.sh

.PHONY: build-docker-ci
build-docker-ci: docker-auth ## Build CI Docker image
	./build/docker/ci/build.sh

.PHONY: publish-docker-ci
publish-docker-ci: docker-auth ## Publish CI Docker image
	./build/docker/ci/push.sh

.PHONY: build-docker
build-docker: docker-auth ## Build application Docker image
	./build/docker/application/build.sh

.PHONY: publish-docker
publish-docker: docker-auth ## Publish application Docker image
	./build/docker/application/push.sh

.PHONY: help
help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' && echo "\nNOTE: You can find Makefile goals implementation stored in \"./build\" directory"
