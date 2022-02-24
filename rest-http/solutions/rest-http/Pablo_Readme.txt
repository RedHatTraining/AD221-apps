=== Starting a AMQ broker:

podman run --name artemis  -e AMQ_USER=admin -e AMQ_PASSWORD=admin -p 8161:8161 -p 61616:61616 -d redhattraining/ad221-amq-broker

=== Starting the soap-server:

podman run --name soap_server -p 8080:8080 -p 8443:8443 -d quay.io/psolarvi/ad221-soap-server:latest

=== Acquire the WSDL file:

curl http://localhost:8080/footprints.php?wsdl >> Footprint.wsdl


=== Manually query the soap service:

curl -v --request POST --header "Content-Type: text/xml;charset=UTF-8" \
--data \
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="FootprintService"> \
<soapenv:Header/> \ 
<soapenv:Body> \ 
<gs:GetFootprint> <gs:ID>1</gs:ID> </gs:GetFootprint> \ 
</soapenv:Body> \ 
</soapenv:Envelope>' \
http://localhost:8080/footprints.php


=== Running the solution:

./mvnw clean spring-boot:run


=== To generate Java source classes from the WSDL

./mvnw clean generate-sources


=== Note:
After running the solution you can query the log of the soap-server with "podman logs soap-server" to verify that it is returning a 200 OK response.
So the error is occuring when the cxf component attempts to use the current client side Java class to unmarshal the XML returned into a Java object.
