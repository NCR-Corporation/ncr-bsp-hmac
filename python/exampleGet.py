from hmacHelper import hmacHelper
from datetime import datetime
from datetime import timezone
import json
import requests


def exampleGet(secretKey="9a27e38da2194950a04fcba431ad9b99", sharedKey="139aa99b486c4848821929b85b0b5b03", nepOrganization="test-drive-ed002b0e2b234b32858eb"):
    
    now = datetime.now(tz=timezone.utc)
    now = datetime(now.year, now.month, now.day, hour=now.hour,
                   minute=now.minute, second=now.second)

    requestURL = "https://api.ncr.com/security/role-grants/user-grants/self/effective-roles"
    httpMethod = 'GET'
    contentType = 'application/json'

    hmacAccessKey = hmacHelper(sharedKey, secretKey, now, httpMethod,
                               requestURL, contentType, None, None, None, nepOrganization, None)

    utcDate = now.strftime('%a, %d %b %Y %H:%M:%S GMT')
    headers = {
        "Date": utcDate,
        "Content-Type": contentType,
        "Authorization": "AccessKey " + hmacAccessKey,
        "nep-organization": nepOrganization
    }

    request = requests.get(requestURL, headers=headers)

    res = dict()
    res['status'] = request.status_code
    res['data'] = request.json()

    json_formatted = json.dumps(res, indent=2)
    print(json_formatted)

    return res


exampleGet()
