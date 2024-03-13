CLUSTER_NAME ?= default
# Image URL to use all building/pushing image targets
SVC_A ?= service-a
SVC_B ?= service-b

# Setting SHELL to bash allows bash commands to be executed by recipes.
# Options are set to exit when a recipe line exits non-zero or a piped command fails.
SHELL = /usr/bin/env bash -o pipefail
.SHELLFLAGS = -ec

## Tool Binaries
KUBECTL ?= $(shell which kubectl)
CILIUM ?= $(shell which cilium)
K3S ?= $(shell which k3s)

.PHONY: help
help: ## Display this help message
	@echo "Usage: make [target] [PORT=<port>] [SVC=<service>]"
	@echo "Parameters:"
	@echo "  PORT=<port>    Port number to use (default: 80)"
	@echo "  SVC=<service>  Service name (default: service-a)"

.PHONY:
create-cluster: ## Create a k3s cluster
	curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC='--flannel-backend=none --disable-network-policy' sh -

.PHONY:
render-service: ## Render Helm chart for the specified service
	helm template $(SVC) ./kubernetes/helm-chart --namespace default -f $(SVC)/values.yaml > ./kubernetes/rendered-manifests/$(SVC).yaml

.PHONY:
build-jar: ## Build JAR file
	mvn package -f $(SVC)/pom.xml

.PHONY: docker-build
docker-build: ## Build Docker images
	docker build -t $(SVC):test -f $(SVC)/Dockerfile $(SVC) \
	&& docker save --output kubernetes/images/$(SVC):test.tar $(SVC)

.PHONY: import-images
import-image: docker-build ## Import Docker images into k3s for local testing
	for img in ${SVC_B}; do \
		sudo k3s ctr images import ./kubernetes/images/$$img:test.tar -c ${CLUSTER_NAME}; \
	done

.PHONY: fwd
fwd: ## Port-forward to a service based on its label
	kubectl port-forward $$(kubectl get pods -n default -l app=$(SVC) -o name | head -n 1) $(PORT):$(PORT)

.PHONY: call 
call: 
	@if [ "$(SVC)" = "${SVC_A}" ]; then \
    	curl -v http://localhost:5000/api/v1/hey-ho; \
	elif [ "$(SVC)" = "${SVC_B}" ]; then \
    	curl -v http://localhost:8888/api/v1/get-hey-ho; \
	else \
		echo "Unknown service: $(SVC)"; \
	fi

.PHONY: debug
debug: #run a debug container in the namespace
	kubectl run ubuntu --image=ubuntu --command -- sleep infinity && kubectl exec -it ubuntu -- sh

.PHONY: logs
logs: # Trail logs for the specific service
	kubectl logs -f -l app=$(SVC) -n default



