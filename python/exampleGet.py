from hmacHelper import hmacHelper
from datetime import datetime
from datetime import timezone
import requests


def exampleGet(secretKey="INSERT_SECRET", sharedKey="INSERT_SHARED", nepOrganization="INSERT_ORGANIZATION"):

    now = datetime.now(tz=timezone.utc)
    now = datetime(now.year, now.month, now.day, hour=now.hour,
                   minute=now.minute, second=now.second)

    requestURL = "https://gateway-staging.ncrcloud.com/site/sites/find-nearby/88.05,46.25?radius=10000"
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

    print(res)

    return res


exampleGet()
