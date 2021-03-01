import urllib.parse
import base64
import hmac
import base64
import hashlib


def hmac(url, date, method, contentType, organization, secretKey, contentMD5):
    toSign = method + "\n" + urllib.parse.urlsplit(url).path
    if contentType is not None:
        toSign += "\n" + contentType

    if contentMD5 is not None:
        toSign += "\n" + contentMD5

    if organization is not None:
        toSign += "\n" + organization

    key = bytes(secretKey + date, 'utf-8')

    message = bytes(toSign, 'utf-8')

    digest = hmac.new(key, msg=bytes(message),
                      digestmod=hashlib.sha512).digest()

    signature = base64.b64encode(digest)

    return signature.decode('ascii')
