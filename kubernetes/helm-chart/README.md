# Helm chart for bankbridge applications

You need to [install helm](https://github.com/kubernetes/helm/blob/master/docs/install.md) to use this chart.

## Features
* Allows easy deployment of an application that needs:
    * A kubernetes deployment
    * A kubernetes config map
    * A kubernetes service
* It by default:
    * Enables log collection of the pod
    * Enable metrics scraping of the pod
    * Adds a readiness probe to the deployment (if service is enabled)
    * Adds a liveness probe to deployment (if service and metrics is enabled)
    * Allocates resources to the pod
    * Exposes a service on port `4567` (by default)

## Usage

Create you own `<YOUR_VALUES_FILE>.yaml` file.

To deploy:

```bash
helm install \
	--namespace <KUBERNETES_NAMESPACE> \
	--name <DEPLOYMENT_NAME> \
	-f <YOUR_VALUES_FILE> \
	.
```

Note: The dot (`.`) in the command above is the path to the chart.

## Default chart values

To see the default chart values look at [values.yaml](./values.yaml)

## Labeling

You can define your own labels:

```yaml
label:
  some: label
  another: stuff
```

## Environment

You can use different methods to inject environmental variables to the container run by this chart.

### Using literal strings

This example adds `DEBUG=true` environmental variable to the container

```yaml
env:
  variables:
    DEBUG: "true"
```

### Using config maps

This example set the environmental variable `MY_ENVIRONMENTAL_VARIABLE` to the value of the element that has the key `config-map-key` from a configmap named `config-map-name`.

```yaml
env:
  configmap:
    config-map-name: 
      MY_ENVIRONMENTAL_VARIABLE: config-map-key
```

### Using secrets

This example is almost identical to the config map example. It sets the environmental variable `MY_ENVIRONMENTAL_VARIABLE` to the value of the element that has the key `secret-key` from a secret named `secret-name`.

```yaml
env:
  secrets:
    secret-name:
      MY_ENVIRONMENTAL_VARIABLE: secret-key
```

## Creating config map

The chart can automatically create a configmap and mount it inside the container. This example mounts a file `application.properties` into the default mount directory (`/etc/bankbridge/`).

```yaml
config:
  enabled: true
  files:
    application.properties: |-
      some=config
      more=stuff
```

## Examples

To see more concrete examples of usage look at:
* [Connector deployments](../namespaces/connectors)
* [UAPI deployments](../namespaces/uapi)