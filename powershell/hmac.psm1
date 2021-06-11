Function Get-AccessKey {
    [CmdletBinding()]
    Param(
        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [String]$sharedKey,

        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [String]$secretKey,

        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [String]$httpMethod,

        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [String]$url,

        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [String]$organization,

        [ValidateNotNullOrEmpty()]
        [Parameter(ValueFromPipeline = $True, Mandatory = $True)]
        [DateTime]$date
    )
    Process {
        $uri = ([System.Uri] $url).PathAndQuery
        
        try {
            $nowGmt = [System.TimeZoneInfo]::ConvertTimeBySystemTimeZoneId($date, 'Greenwich Standard Time')
        } catch {
            $nowGmt = [System.TimeZoneInfo]::ConvertTimeBySystemTimeZoneId($date, 'Africa/Bissau')
        }
        $iso8601 = $nowGmt.ToString("yyyy-MM-ddTHH:mm:ss") + ".000Z"

        $signableContent = @($httpMethod, $uri, 'application/json', $organization)

        $joinedContent = [System.String]::Join("`n", $signableContent)

        $oneTimeKey = "$($secretKey)$($iso8601)"

        $enc = [system.Text.Encoding]::UTF8

        $oneTimeKeyBytes = $enc.GetBytes($oneTimeKey)

        $joinedContentBytes = $enc.GetBytes($joinedContent)

        $hmacSha512 = [System.Security.Cryptography.HMACSHA512]::New($oneTimeKeyBytes)

        $hashBytes = $hmacSha512.ComputeHash($joinedContentBytes);

        $hmacSignature = [Convert]::ToBase64String($hashBytes)

        $accessKey = "$($sharedKey):$($hmacSignature)"

        Return $accessKey
    }
}
