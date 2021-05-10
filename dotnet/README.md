# HMAC Dotnet Examples

The example helper function and the functionality of the request live in `Program.cs` which contains the method Main, the appropriate call, and the hmac helper, as well as the details on how to generate the 'AccessKey''

### Getting Started

_This code uses Dotnet 5_

1. Navigate to the directory of the call you would like to send via the command line.

2. Add the RestSharp API client libray via the command line while inside call directory(this will need to be done for both of the folders containing the calls):
   ```console
   $cd SendGet
   $dotnet add package RestSharp
   // Adds the package to the SendGet call
   ```

   ```console
   $cd SendGet
   $dotnet add package RestSharp
   // Adds the package to the SendGet call
   ```

3. Inside of `Program.cs` in the current directory, update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```C#
   secretKey = 'INSERT_SECRET'
   sharedKey = 'INSERT_SHARED'
   nepOrganization = 'INSERT_ORGANIZATION'
   ```

4. To test, Navigate inside of the correct directory and:

   ```console 
   $ dotnet run
   // GET Request to find sites nearby:
   // {'sites': [], 'totalResults': 0}
   ```

   ```console
   $ dotnet run
   // POST Request to find sites by criteria
   ```
