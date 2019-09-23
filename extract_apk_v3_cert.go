package main

import (
	"crypto/sha256"
	"fmt"
	"github.com/avast/apkverifier"
	"github.com/grantae/certinfo"
	"io/ioutil"
	"os"
)

func main() {
	fmt.Printf("APK v3 Cert Extract by Justin Perez\n")

	if len(os.Args) != 2 {
		fmt.Printf("Usage: apk_v3_cert_extractor [apk path]\n")
		os.Exit(-1)
	}

	fileName := os.Args[1]

	cert, err := apkverifier.ExtractCerts(fileName, nil)

	if err != nil {
		panic(err)
	}

	rawCert := cert[0][0].Raw
	err = ioutil.WriteFile("out_cert", rawCert, 0644)

	shaAlgo := sha256.New()
	shaAlgo.Write(rawCert)
	fmt.Printf("Sha256 HASH of Cert: %x\n", shaAlgo.Sum(nil))

	if err != nil {
		panic(err)
	}
	res, err := certinfo.CertificateText(cert[0][0])

	if err != nil {
		panic(err)
	}

	fmt.Printf("Cert Info:\n%s\n", res)
}
