from urllib.parse import urlparse
import base64
import hmac
import hashlib


def hmacHelper(sharedKey, secretKey, dateHeader, httpMethod, requestURL, contentType, contentMD5, nepApplicationKey, nepCorrelationID, nepOrganization, nepServiceVersion):
    '''
    :param str sharedKey: A user's Shared Key
    :param str secretKey: A user's Secret Key
    :param date date: An unformated date object
    :param str httpMethod: GET/POST/PUT
    :param str requestURL: The API url requesting against
    :param str [contentType=application/json]: Optional
    :param str [nepApplicationKey]: Optional
    :param str [nepCorrelationID]: Optional
    :param str [nepOrganization]: Optional
    :param str [nepServiceVersion]: Optional
    :return: sharedKey:hmac
    :rtype: string
    '''
    parsedUrl = urlparse(requestURL)
    pathAndQuery = parsedUrl.path
    if parsedUrl.query:
        pathAndQuery += '?' + parsedUrl.query
    toSign = httpMethod + "\n" + pathAndQuery
    print(toSign)
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

    isoDate = dateHeader.isoformat(timespec='milliseconds') + 'Z'
    key = bytes(
        secretKey + isoDate, 'utf-8')

    message = bytes(toSign, 'utf-8')

    digest = hmac.new(key, msg=bytes(message),
                      digestmod=hashlib.sha512).digest()

    signature = base64.b64encode(digest).decode('ascii')

    return sharedKey + ":" + signature
