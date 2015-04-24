# ShopFoundry PKI Certificate Authority (CA)

Certificate Authority service provides interface for initialization and certificate management of end entities, i.e. various microservices, platform users and machines.

Although, this service tends to be complient with various RFC's which describe Public Key Infrastructure in general, this may not be short term proprity for this service. Simple implementation is enough if provides functionality which platform needs. 

Short-term features:

 - RSA key support only
 - Database layer introduction
 - Authentication and request integrity check
 - Certificate Signing Request handling
 - Certificate revocation
 - Certificate renewal
 - Certificate hold/unhold
 - Certificate revocation list
 - Certificate repository


Long-term features:

- ECC support
- Proof of posession (POP) introduction
- CMP protocol introduction
- CMP via HTTP tunneling introduction
- OCSP protocol introduction