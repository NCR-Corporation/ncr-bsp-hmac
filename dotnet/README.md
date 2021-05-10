# HMAC Dotnet Examples

The example helper function and the functionality of the request live in `Program.cs` which contains the method Main, the appropriate call, and the hmac helper, as well as the details on how to generate the 'AccessKey''

### Getting Started

_This code uses Dotnet 5_

1. Navigate inside the dotnet directory. Once in navigate to the directory of the call you would liek to send.
2. Inside of `Program.cs`, update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```py
   secretKey = 'INSERT SECRET'
   sharedKey = 'INSERT SHARED'
   nepOrganization = 'INSERT ORGANIZATION'
   ```

3. To test, Navigate inside of the correct directory and:

   ```console 
   $ dotnet run
   // GET Request to find sites nearby:
   // {'sites': [], 'totalResults': 0}
   ```

   ```console
   $ dotnet run
   // POST Request to find sites by criteria
   ```
