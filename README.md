# QR code server

This service will create a qr code from the data which is send via POST to the api. The
result will be an image file (png).

Based on ZXing Project: https://zxing.appspot.com/

## Requirements

- Java 17
- Maven
- Docker

Install with **mvn clean install** and then run it with **java -jar qr-server.jar** within the target folder.

## How to use QR-Server
If application is running, simply send request to the server. If props unchanged,
send your request to

    POST http://localhost:8080/

Path and port can be changed inside the property file.

Example request body:

    {
        "data": "some string",
        "errorLevel": MEDIUM,
        "imageSize": 200
    }

Error level must be one of the following strings: 
- LOW
- MEDIUM
- QUARTILE
- HIGH

Image Size is the dimension of the output image. The returned image is currently
always a png file.

## Examples

Find more examples at https://zxing.appspot.com/generator/.

### URL
    {
        "data": "https://www.github.com",
        "errorLevel": "LOW",
        "imageSize": 200
    }

### WiFi-Code
    {
        "data": "WIFI:S:Some_SSID;T:WPA;P:Some_Passphrase;;",
        "errorLevel": "HIGH",
        "imageSize": 400
    }

### EPC-QR-Code (Girocode)

    {
        "data": "BCD\n001\n1\nSCT\nBFSWDE33BER\nWikimedia Foerdergesellschaft\nDE33100205000001194700\nEUR123.45\n\n\nSpende fuer Wikipedia\n",
        "errorLevel": "MEDIUM",
        "imageSize": 400
    }

More informations about EPC-Code at https://en.wikipedia.org/wiki/EPC_QR_code

## Instructions for Docker

Current docker image can be downloaded from:

https://hub.docker.com/r/fehrenbacher/qrcode-server

### Building on your own

A valid jar must exist in the target folder before building a docker image.

Build the image with the following command:

    docker image build -t .

If image is ready, start the server with:

    docker run --rm -p 8080:8080 -p 80:8080 --name qrcode-server qrcode-server:latest

### Running with custom configuration (with linux)

Create a file named **application.properties** in a separate directory and cd into it. Add some custom config like:

    server.port=9090
    spring.mvc.servlet.path=/code-generator

Now start the container with a new mount:  

    docker run --rm -v "$PWD":/srv/config --name qrcode-server qrcode-server:latest

The executable JAR inside the container exists at **/srv** and a new **config**-Directory will be mounted
next to the JAR.