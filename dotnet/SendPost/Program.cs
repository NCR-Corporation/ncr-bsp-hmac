using System;
using System.Security.Cryptography;
using System.Text;
using RestSharp;

namespace SendPost
{
    class Program
    {
        /**
        *Main
        *@param args
        */
        static void Main(string[] args)
        {
            callPost("INSERT_SECRET","INSERT_SHARED","INSERT_ORGANIZATION");
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
        public static void callPost(
            String secretKey, 
            String sharedKey, 
            String nepOrganization)
        {
            DateTime utcDate = DateTime.UtcNow;
            String url = "https://gateway-staging.ncrcloud.com/site/sites/find-by-criteria?pageNumber=0&pageSize=10";
            String httpMethod = "POST";
            String contentType = "application/json";
            String hmacAccessKey = CreateHMAC(sharedKey, secretKey, DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ss"), httpMethod, url, contentType, "", "", "", nepOrganization, "");

            var client = new RestClient(url);
            var request = new RestRequest(Method.POST);
            var gmtDate = utcDate.DayOfWeek.ToString().Substring(0,3) + ", " + utcDate.ToString("dd MMM yyyy HH:mm:ss") + " GMT";

            request.AddHeader("nep-organization", nepOrganization);
            request.AddHeader("content-type", "application/json");
            request.AddHeader("date", gmtDate);
            request.AddHeader("authorization", "AccessKey " + hmacAccessKey);
            request.AddParameter("undefined", "{\"sort\":[{\"column\":\"siteName\",\"direction\":\"asc\"}]}", ParameterType.RequestBody);

            IRestResponse response = client.Execute(request);

            Console.WriteLine("{\"status\": " + response.StatusCode + ", \"data\": " + response.Content+ "}");
        }
    }
}
