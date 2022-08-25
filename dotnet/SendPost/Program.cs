using System;
using System.Net.Http;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using RestSharp;

namespace SendPost
{
    public class Program
    {
        /**
        * Main
        * @param args
        */
        private static void Main()
        {
            CallPost("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
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
        public static string CreateHmac(
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
        public static void CallPost(
            string secretKey,
            string sharedKey,
            string nepOrganization)
        {
            DateTime utcDate = DateTime.UtcNow;
            var url = "https://api.ncr.com/security/authorization";
            var httpMethod = "POST";
            var contentType = "application/json";
            var hmacAccessKey = CreateHmac(sharedKey, secretKey, utcDate.ToString("yyyy-MM-ddTHH:mm:ss"),
                httpMethod, url, contentType, "", "", "", nepOrganization, "");

            var client = new RestClient(url);
            var request = new RestRequest();
            var gmtDate = utcDate.DayOfWeek.ToString().Substring(0, 3) + ", " + utcDate.ToString("dd MMM yyyy HH:mm:ss") + " GMT";

            request.AddHeader("nep-organization", nepOrganization);
            request.AddHeader("content-type", "application/json");
            request.AddHeader("date", gmtDate);
            request.AddHeader("authorization", "AccessKey " + hmacAccessKey);

            var response = client.Post(request);

            Console.WriteLine("{ \"status\": " + response.StatusCode + " }\n" + response.Content);
        }
    }
}