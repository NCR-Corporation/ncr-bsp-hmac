from hmacHelper import hmacHelper
from datetime import datetime
from datetime import timezone
import requests
import json


def examplePost(secretKey="INSERT_SECRET", sharedKey="INSERT_SHARED", nepOrganization="INSERT_ORGANIZATION"):

    now = datetime.now(tz=timezone.utc)
    now = datetime(now.year, now.month, now.day, hour=now.hour,
                   minute=now.minute, second=now.second)

    requestURL = "https://gateway-staging.ncrcloud.com/site/sites/find-by-criteria?pageNumber=0&pageSize=10"
    httpMethod = 'POST'
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

    data = {
        "sort": [{"column": "siteName", "direction": "asc"}]
    }

    payload = json.dumps(data)

    request = requests.post(requestURL, data=payload, headers=headers)

    res = dict()
    res['status'] = request.status_code
    res['data'] = request.json()

    print(res)

    return res


examplePost()
