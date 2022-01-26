# Node.js temperatures service

This service is part of the scenario for the AD221 guided exercise: **Deploying Camel Applications to OpenShift**.

The container image used in the exercise can be found at `https://quay.io/repository/redhattraining/ad221-temperatures-celsius-service`.

For more information about how this image is deployed to OpenShift, refer to the AD221 grading scripts source code.

## Build the image

```
podman build -f Containerfile -t ad221-temperatures-celsius-service
```

## Push the image

```
podman tag ad221-temperatures-celsius-service quay.io/redhattraining/ad221-temperatures-celsius-service
podman push quay.io/redhattraining/ad221-temperatures-celsius-service
```
