using System;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using RestSharp;

namespace SendGet
{
    public class Program
    {
        /**
        * Main
        * @param args
        */
        static void Main(string[] args)
        {
            callGet("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
        }

        /**
        * @param sharedKey  A user's Shared Key
        * @param secretKey A user's Secret Key
        * @param date An unformated date string
        * @param httpMethod GET/POST/PUT
        * @param requestUrl The API url requesting against
        * @param contentType Optional
        * @param contentMD5 Optional
        * @param nepApplicationKey Optional
        * @param nepCorrelationId Optional
        * @param nepOrganization A user's organization
        * @param nepServiceVersion Optional
        * @return sharedKey:hmac
        */
         public static string CreateHMAC(
            string sharedKey,
            string secretKey,
            string date,
            string httpMethod,
            string requestURL,
            string contentType = null,
            string contentMD5 = null,
            string nepApplicationKey = null,
            string nepCorrelationID = null,
            string nepOrganization = null,
            string nepServiceVersion = null)
        {
            Uri url = new Uri(requestURL);

            string pathAndQuery = url.PathAndQuery;

            string secretDate = date + ".000Z";

            string oneTimeSecret = secretKey + secretDate;
            string toSign = httpMethod + "\n" + pathAndQuery;

            if (!String.IsNullOrEmpty(contentType))
            {
                toSign += "\n" + contentType;
            }
            if (!String.IsNullOrEmpty(contentMD5))
            {
                toSign += "\n" + contentMD5;
            }
            if (!String.IsNullOrEmpty(nepApplicationKey))
            {
                toSign += "\n" + nepApplicationKey;
            }
            if (!String.IsNullOrEmpty(nepCorrelationID))
            {
                toSign += "\n" + nepCorrelationID;
            }
            if (!String.IsNullOrEmpty(nepOrganization))
            {
                toSign += "\n" + nepOrganization;
            }
            if (!String.IsNullOrEmpty(nepServiceVersion))
            {
                toSign += "\n" + nepServiceVersion;
            }

            var data = Encoding.UTF8.GetBytes(toSign);
            var key = Encoding.UTF8.GetBytes(oneTimeSecret);
            byte[] hash = null;

            using (HMACSHA512 shaM = new HMACSHA512(key))
            {
                hash = shaM.ComputeHash(data);
            }

            string accessKey = sharedKey + ":" + System.Convert.ToBase64String(hash);
            return accessKey;
        }

        /**
        * The method to build the POST request
        * @param secretKey  A user's Secret Key
        * @param sharedKey A user's Shared Key
        * @param nepOrganization A user's organization
        */
        public static int callGet(String secretKey, String sharedKey, String nepOrganization){
            String url = "https://api.ncr.com/security/roles?roleNamePattern=*&pageNumber=0&pageSize=10";
            String httpMethod = "GET";
            String contentType = "application/json";
            DateTime utcDate = DateTime.UtcNow;

            String hmacAccessKey = CreateHMAC(sharedKey, secretKey, DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ss"), httpMethod, url, contentType, "", "", "", nepOrganization, "");

            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);
            var gmtDate = utcDate.DayOfWeek.ToString().Substring(0,3) + ", " + utcDate.ToString("dd MMM yyyy HH:mm:ss") + " GMT";

            request.AddHeader("nep-organization", nepOrganization);
            request.AddHeader("content-type", contentType);
            request.AddHeader("date", gmtDate);
            request.AddHeader("authorization", "AccessKey " + hmacAccessKey);

            IRestResponse response = client.Execute(request);

            var responseContent = JsonSerializer.Deserialize<ContentModel>(response.Content);
            
            var options = new JsonSerializerOptions(){
                WriteIndented = true
            };

            var formattedJSON = JsonSerializer.Serialize(responseContent, options);    

            Console.WriteLine("{ \"status\": " + response.StatusCode + " }\n{ \"Data\": \n" + formattedJSON + "\n}");
			return (int) response.StatusCode;
        }
    }
}
