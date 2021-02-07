## SRE Coding Challenge - Exercise 2

This Exercise is about debugging.

After running the preparation commands, you will start with a broken cluster that can potentially have
issues with Kubernetes configuration, network issues and application issues.

You will need to debug to try to fix both services.

### About the exercise
Remember that you might need to change anything related with this deployment, from the cluster itself, to the network configuration or the code running the services.

Document every step in your testing.

Remember that, even though it is not mandatory, you get extra points for recording all or parts of your debugging in video.

You will get a score based on the number of issues resolved in this deployment.

You will get extra points for a successful refactoring of the code in service-a and service-b to be more readable, scalable, secure, and less prune to errors found in production.


### Preparation steps

**Pre-Requesities**
* minikube: https://minikube.sigs.k8s.io/docs/start/
    * You can use any other way to have a Kubernetes Cluster as you want, the only hard requisite is that it must have Network Policies enabled
* helm v3: https://helm.sh/docs/intro/install/
* kubectl: https://kubernetes.io/docs/tasks/tools/install-kubectl/

**Set up the environment:**
* Set up the Minikube cluster or any other kind of Kubernetes cluster with Network Policies enabled. You can use Calico.
> minikube -p sre-coding-challenge start --network-plugin=cni --cni=calico
* Apply our default configurations for our "default" namespace. This will apply default Network Policies that will deny all ingress and egress traffic to this namespace by default.
> k apply -f kubernetes/default-namespace.yaml
* Deploy both service-a and service-b using helm

Inside service_a folder:
> helm upgrade --install --namespace default -f ./values.yaml service-a ../kubernetes/helm-chart/

Inside service_b folder:
> helm upgrade --install --namespace default -f ./values.yaml service-b ../kubernetes/helm-chart/

After this, the environment will be configured.

You will have 2 services:
* service-a: A python service running the Flask framework on port 5000
* service-b: A java service running with Springboot on port 8888

### How to start debugging
You will need to port-forward to service-a:
> kubectl port-forward -n default <pod-name-of-service-a> 5000:5000

And you can then send GET requests to:
> curl http://localhost:5000/api/v1/hey-ho

### How do I know that everything is working?
Once you've figure out all the issues affecting these deployments, you will be able to get the following output when calling the API in service-a.

![Success Response](data/success-response.png?raw=true "Success Response")

Your requests should go to service-a with port-forwarding, and then service-a will send a request to service-b and will print the response.

A successful request processed in service-b will create a file inside the service-b container and put the content of a json obtained from a remote location in it.
