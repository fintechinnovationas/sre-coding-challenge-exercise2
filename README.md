## general

Had to rename the folders so that I can use for pathing purposes with the image and the folders. 

Using k3s as the cluster, overall much easier to work with than minikube or anything that's on a higher abstraction level.

Started out with calico for CNI, but cilium is superior in every sense so had to redo the entire cluster. This solution needs cilium to be the CNI plugin, k3s specific instructions [here](https://docs.cilium.io/en/stable/gettingstarted/k8s-install-default/#install-cilium).

Use the Makefile to set things up, you can deploy as usual (either with helm or kctl apply directly). I like rendering manifests to see what I'm working with hence the option in the Makefile.

## Tasks
- [X] Makefile for repetitive tasks
- [X] Render the manifests so that we actually what's going on.
- [X] ImagepullPolicy to IfNotPresent
- [X] DNS based networkpolicy for the json placeholder domain
- [X] Path issue on service_b, Dockerfile and code changes so that it's on app/data and it actually has permissions to write to it
- [X] Mem request increase so that GCC doesn't kill the pod periodically
- [ ] Add GCC profiling to service_b
- [ ] Better mem utilization -XX:MaxRAMPercentage=75.0 >> default is 25% on that alpine image
- [ ] Add startupProbe for that initial delay for JAVA apps
- [ ] setting the terminationMessagePolicy to "FallbackToLogsOnError"
- [ ] Fix the readiness probe to probe something actually useful, like the site we're trying to reach
- [ ] RollingUpdate settings don't make a lot of sense witgh replicas set to 1
- [ ] You'd need to scale this most likely for multiple replicas
- [X] Better error propagation in the JAVA codeso it's on the container logs with full stacktrace 
- [X] Fix resource limits for CPU for all deployments
- [ ] Tight coupling in python code, hard-coded url is bad
- [ ] Python docker image doesn't build anymore
- [ ] Separate services to their own namespaces, fix networkpolicies accordingly > this won't work because of baked in url in python code, which doesn't build now due to deprecation
