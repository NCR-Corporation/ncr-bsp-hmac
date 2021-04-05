# HMAC Python Examples

The example helper function lives in `hmacHelper.py` which containst the details on how to use HMAC to generate an `AccessKey`

### Getting Started

_This code uses Python 3_

1. Make sure `requests` is installed with `pip3 install requests`
2. Inside of `example-get.py` and `example-post.py` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```py
   secretKey = 'INSERT SECRET'
   sharedKey = 'INSERT SHARED'
   nepOrganization = 'INSERT ORGANIZATION'
   ```

3. To test:

   ```console
   $ python3 exampleGet.py
   // GET Request to find sites nearby:
   // {'sites': [], 'totalResults': 0}
   ```

   ```console
   $ python3 examplePost.py
   // POST Request to find sites by criteria
   ```
