using System;
using System.Net.Http;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace SendPost
{
    public class Program
    {
        /**
        *Main
        *@param args
        */
        static async Task Main()
        {
            await CallPost("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
        }

        /**
        * @param sharedKey  A user's Shared Key
        * @param secretKey A user's Secret Key
        * @param date An unformatted date string
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
            string requestUrl,
            string contentType = null,
            string contentMd5 = null,
            string nepApplicationKey = null,
            string nepCorrelationId = null,
            string nepOrganization = null,
            string nepServiceVersion = null)
        {
            var url = new Uri(requestUrl);
            var pathAndQuery = url.PathAndQuery;

            var secretDate = date + ".000Z";
            var oneTimeSecret = secretKey + secretDate;

            var toSign = httpMethod + "\n" + pathAndQuery;

            if (!string.IsNullOrEmpty(contentType))
            {
                toSign += "\n" + contentType;
            }

            if (!string.IsNullOrEmpty(contentMd5))
            {
                toSign += "\n" + contentMd5;
            }

            if (!string.IsNullOrEmpty(nepApplicationKey))
            {
                toSign += "\n" + nepApplicationKey;
            }

            if (!string.IsNullOrEmpty(nepCorrelationId))
            {
                toSign += "\n" + nepCorrelationId;
            }

            if (!string.IsNullOrEmpty(nepOrganization))
            {
                toSign += "\n" + nepOrganization;
            }

            if (!string.IsNullOrEmpty(nepServiceVersion))
            {
                toSign += "\n" + nepServiceVersion;
            }

            var data = Encoding.UTF8.GetBytes(toSign);
            var key = Encoding.UTF8.GetBytes(oneTimeSecret);
            byte[] hash;

            using (var shaM = new HMACSHA512(key))
            {
                hash = shaM.ComputeHash(data);
            }

            var accessKey = sharedKey + ":" + Convert.ToBase64String(hash);
            return accessKey;
        }

        /**
        * The method to build the POST request
        * @param secretKey  A user's Secret Key
        * @param sharedKey A user's Shared Key
        * @param nepOrganization A user's organization
        */
        public static async Task<int> CallPost(
            string secretKey,
            string sharedKey,
            string nepOrganization)
        {
            var utcDate = DateTime.UtcNow;
            var url = "https://api.ncr.com/security/authorization";
            var httpMethod = "POST";
            var contentType = "application/json";
            var hmacAccessKey = CreateHmac(sharedKey, secretKey, DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ss"),
                httpMethod, url, contentType, "", "", "", nepOrganization, "");

            var client = new HttpClient();
            var request = new HttpRequestMessage(HttpMethod.Post, url);
            var gmtDate = utcDate.DayOfWeek.ToString().Substring(0, 3) + ", " +
                          utcDate.ToString("dd MMM yyyy HH:mm:ss") + " GMT";

            request.Headers.Add("nep-organization", nepOrganization);
            request.Headers.Add("date", gmtDate);
            request.Headers.Add("authorization", "AccessKey " + hmacAccessKey);

            var content = new StringContent(String.Empty, Encoding.UTF8, "application/json");
            content.Headers.Remove("Content-Type"); 
            content.Headers.Add("Content-Type", "application/json");
            request.Content = content;

            var response = await client.SendAsync(request);
            var responseContentString = await response.Content.ReadAsStringAsync();

            var responseContent = JsonSerializer.Deserialize<ContentModel>(responseContentString);
            var options = new JsonSerializerOptions()
            {
                WriteIndented = true
            };

            var formattedJson = JsonSerializer.Serialize(responseContent, options);

            Console.WriteLine("{ \"status\": " + response.StatusCode + " }\n" + formattedJson);
            return (int)response.StatusCode;
        }
    }
}