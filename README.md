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
        "errorLevel": "HIGT",
        "imageSize": 400
    }

### EPC-QR-Code (Girocode)

    {
        "data": "BCD\n001\n1\nSCT\nBFSWDE33BER\nWikimedia Foerdergesellschaft\nDE33100205000001194700\nEUR123.45\n\n\nSpende fuer Wikipedia\n",
        "errorLevel": "MEDIUM",
        "imageSize": 400
    }

More informations about EPC-Code at https://en.wikipedia.org/wiki/EPC_QR_code
