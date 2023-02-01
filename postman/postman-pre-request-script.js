//Replaces variables in header value and trims the resulting string
const processHeader = function(header) {
    if (header && header.length > 0) {
        return pm.variables.replaceIn(header).trim();
    } else {
        return header;
    }
}

//Extracts the signable content from the request
const signableContent = function() {
    var sdk = require('postman-collection');
    var url = new sdk.Url(pm.variables.replaceIn(pm.request.url.toString()));
    var uri = encodeURI(url.getPathWithQuery());
    var headers = pm.request.getHeaders({enabled: true, ignoreCase: true});

    const params = [
        pm.request.method,
        uri,
        processHeader(headers['content-type']),
        processHeader(headers['content-md5']),
        processHeader(headers['nep-correlation-id']),
        processHeader(headers['nep-organization']),
        processHeader(headers['nep-service-version'])
    ];
    return params.filter(p => p && p.length > 0).join('\n');
}

//Pull shared-key and secret-key from variables
var shared = pm.variables.get("shared-key");
var secret = pm.variables.get("secret-key");

//Generate date for signing and set milliseconds to 0
var date = new Date();
date.setMilliseconds(0);

//Compile the data to sign
var oneTimeSecret = secret +  date.toISOString();
var content = signableContent()

//Create signature and access key value
var signature = CryptoJS.enc.Base64.stringify(CryptoJS.HmacSHA512(content, oneTimeSecret));
var accessKey = shared + ":" + signature;

//Inject headers into request
pm.request.upsertHeader({key: "Authorization", value: "AccessKey " + accessKey, disabled: false});
pm.request.upsertHeader({key: "Date", value: date.toUTCString(), disabled: false});

//Set environment variables with outputs
pm.environment.set('access-key-signature', signature);
pm.environment.set('unsigned-content-escaped', content.replace(/\n/g, '\\n'));
pm.environment.set('timestamp', date.toISOString());