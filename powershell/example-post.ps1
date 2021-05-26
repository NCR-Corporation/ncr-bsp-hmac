Import-Module "./hmac.psm1"

$sharedKey = "INSERT SHARED KEY"
$secretKey = "INSERT SECRET KEY"
$organization = "INSERT ORGANIZATION"

$url = "https://api.ncr.com/security/authorization"
$now = Get-Date

$accessKey = Get-AccessKey -sharedKey $sharedKey `
    -secretKey $secretKey `
    -httpMethod POST `
    -url $url `
    -organization $organization `
    -date $now

$body = ConvertTo-Json @{ }

$headers = @{
    'Date' = $now
    'Authorization' = "AccessKey $accessKey"
    'nep-organization' = $organization
}

try {
    $resp = Invoke-WebRequest -Uri $url `
        -Method POST `
        -ContentType 'application/json' `
        -Headers $headers `
        -Body $body
        
    Write-Output $resp.Content
} catch [System.SystemException] {
    Write-Warning "Exception $_" 
    Write-Warning $_.ScriptStackTrace
} catch {
    Write-Error "Unknown exception! PANIC!!"
}
