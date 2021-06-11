Import-Module "./hmac.psm1"

$sharedKey = "INSERT SHARED KEY"
$secretKey = "INSERT SECRET KEY"
$organization = "INSERT ORGANIZATION"

if ($env:SHARED_KEY -And  $env:SECRET_KEY -And $env:ORGANIZATION) {
    $sharedKey = $env:SHARED_KEY
    $secretKey = $env:SECRET_KEY
    $organization = $env:ORGANIZATION
}

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
        
    $resp.Content
    return $resp
} catch [System.SystemException] {
    Write-Warning "Exception $_" 
    Write-Warning $_.ScriptStackTrace
} catch {
    Write-Error "Unknown exception! PANIC!!"
}
