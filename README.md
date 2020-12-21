# Benchmarks ingress controllers

## Pre-Requisites
* Create a Kubernetes Cluster
* Install Helm (https://helm.sh/docs/intro/install)
* Install Sbt (https://www.scala-sbt.org/download.html).

## Setup App

Deploy hello app to the cluster
```bash
kubectl create deployment hello-server --image=gcr.io/google-samples/hello-app:1.0
```

Deploy ClusterIP service for the application
```
kubectl expose deployment hello-server --port 8080 --target-port 8080
```

## Setup Ingress Controllers

### Nginx

Install Nginx ingress controller
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
helm install nginx-ingress ingress-nginx/ingress-nginx
```

Deploy Ingress
```
kubectl apply -f k8s/nginx/ingress-resource.yaml
```

### Traefik 2

Install Traefik 2 ingress controller
```
helm repo add traefik https://helm.traefik.io/traefik
helm repo update
helm install traefik traefik/traefik
```

Deploy IngressRoute
```
kubectl apply -f k8s/traefik2/ingressroute-resource.yaml
```

### Optional

Use Let's Encrypt certificate
```bash
kubectl create secret tls cert-nginx-ssl --key privkey.pem --cert fullchain.pem
```

```bash
kubectl create secret tls cert-traefik-ssl --key tls.key --cert tls.crt
```

Please note specific file name for traefik 2

Enable Traefik debug log
```bash
helm upgrade -f k8s/traefik2/values-debug.yaml traefik traefik/traefik
```

## Usage

Start SBT
---------
```bash
$ INGRESS_BASE_URL=http://external_lb_ip/hello sbt
```

Run all simulations
-------------------

```bash
> gatling:test
```

Run a single simulation
-----------------------

```bash
> gatling:testOnly helloapp.IngressSimulationLoadTest20Users
```

List all tasks
--------------------

```bash
> tasks -v gatling
```