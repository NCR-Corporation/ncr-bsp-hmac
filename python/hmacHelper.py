import urllib
import base64
import hmac
import hashlib


def hmacHelper(sharedKey, secretKey, dateHeader, httpMethod, requestURL, contentType, contentMD5, nepApplicationKey, nepCorrelationID, nepOrganization, nepServiceVersion):
    toSign = httpMethod + "\n" + urllib.parse.urlsplit(requestURL).path
    if contentType is not None:
        toSign += "\n" + contentType

    if contentMD5 is not None:
        toSign += "\n" + contentMD5

    if nepOrganization is not None:
        toSign += "\n" + nepOrganization

    if nepApplicationKey is not None:
        toSign += "\n" + nepApplicationKey

    if nepCorrelationID is not None:
        toSign += "\n" + nepCorrelationID

    if nepServiceVersion is not None:
        toSign += "\n" + nepServiceVersion

    print(toSign)
    print('######')
    isoDate = dateHeader.isoformat(timespec='milliseconds') + 'Z'
    key = bytes(
        secretKey + isoDate, 'utf-8')

    message = bytes(toSign, 'utf-8')

    digest = hmac.new(key, msg=bytes(message),
                      digestmod=hashlib.sha512).digest()

    signature = base64.b64encode(digest).decode('ascii')

    return sharedKey + ":" + signature
