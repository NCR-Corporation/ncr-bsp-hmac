# HMAC Powershell Examples

The example helper function lives in `hmac.psm1` which contains the HMAC `AccessKey` implementation.

### Getting Started

1. Inside of `example-get.py` and `example-post.py` update the `$organization`, `$secretKey`, and `$sharedKey` with your values:

   ```powershell
   $secretKey = 'INSERT SECRET KEY'
   $sharedKey = 'INSERT SHARED KEY'
   $organization = 'INSERT ORGANIZATION'
   ```

2. To test:

   ```console
   PS C:\ncr-bsp-hmac\powershell> .\example-get.ps1

   // {'sites': [], 'totalResults': 0}
   ```

   ```console
   PS C:\ncr-bsp-hmac\powershell> .\example-post.ps1

   // {"lastPage":true,"pageNumber":0,"totalPages":0,"totalResults":0,"pageContent":[]}
   ```
   