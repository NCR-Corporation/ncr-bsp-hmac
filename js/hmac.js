const hmacSHA512 = require("crypto-js/hmac-sha512");
const Base64 = require("crypto-js/enc-base64");

/**
 * Function to generate HMAC Key.
 * @param {string} sharedKey - A user's Shared Key
 * @param {string} secretKey - A user's Secret Key
 * @param {Date} date - An unformated date object
 * @param {string} httpMethod - GET/POST/PUT
 * @param {string} requestURL - The API url requesting against
 * @param {string} [contentType=application/json] - Optional
 * @param {string} [nepApplicationKey] - Optional
 * @param {string} [nepCorrelationID] - Optional
 * @param {string} [nepOrganization] - Optional
 * @param {string} [nepServiceVersion] - Optional
 * @returns {string} sharedkey:hmac
 */
module.exports = function ({
  sharedKey,
  secretKey,
  date,
  httpMethod,
  requestURL,
  contentType = "application/json",
  contentMD5,
  nepApplicationKey,
  nepCorrelationID,
  nepOrganization,
  nepServiceVersion,
}) {
  let uri = encodeURI(requestURL.replace(/^https?:\/\/[^/]+\//, "/"));

  const nonce = date.toISOString().slice(0, 19) + ".000Z";

  const oneTimeSecret = secretKey + nonce;

  let toSign = httpMethod + "\n" + uri;
  if (contentType) {
    toSign += "\n" + contentType.trim();
  }
  if (contentMD5) {
    toSign += "\n" + contentMD5.trim();
  }
  if (nepApplicationKey) {
    toSign += "\n" + nepApplicationKey.trim();
  }
  if (nepCorrelationID) {
    toSign += "\n" + nepCorrelationID.trim();
  }
  if (nepOrganization) {
    toSign += "\n" + nepOrganization.trim();
  }
  if (nepServiceVersion) {
    toSign += "\n" + nepServiceVersion.trim();
  }

  const key = hmacSHA512(toSign, oneTimeSecret);
  return `${sharedKey}:${Base64.stringify(key)}`;
};
