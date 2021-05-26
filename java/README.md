# HMAC Java Examples

The example helper function lives in `HmacGenerator.java` which contains the details on how to use HMAC to generate an `AccessKey`

### Getting Started

_This code uses Java 11.0.11_

1. Inside of `SendGet.java` and `SendPost.java` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```java
   secretKey = 'INSERT_SECRET'
   sharedKey = 'INSERT_SHARED'
   nepOrganization = 'INSERT_ORGANIZATION'
   ```

2. Once you have made your changes, be sure to save the files before you compile in the next step.

3. Compile the two request classes inside of the java directory:

   ```console
   $ javac SendGet.java
   // Compiles and creates the class for the GET API call
   ```

   ```console
   $ javac SendPost.java
   // Compiles and creates the class for the POST API call
   ```

4. To test:

   ```console
   $ java SendGet
   // GET Request to find sites nearby:
   // {'sites': [], 'totalResults': 0}
   ```

   ```console
   $ java SendPost
   // POST Request to find sites by criteria
   ```   