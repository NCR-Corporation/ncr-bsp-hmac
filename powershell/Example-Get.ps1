Import-Module "./hmac.psm1"

$sharedKey = "INSERT SHARED KEY"
$secretKey = "INSERT SECRET KEY"
$organization = "INSERT ORGANIZATION"

if ($env:SHARED_KEY -And  $env:SECRET_KEY -And $env:ORGANIZATION) {
    $sharedKey = $env:SHARED_KEY
    $secretKey = $env:SECRET_KEY
    $organization = $env:ORGANIZATION
}

$url = "https://api.ncr.com/security/roles?roleNamePattern=*&pageNumber=0&pageSize=10"
$now = Get-Date -Format R

$accessKey = Get-AccessKey -sharedKey $sharedKey `
    -secretKey $secretKey `
    -httpMethod GET `
    -url $url `
    -organization $organization `
    -date $now

    $headers = @{
        'Date' = $now
        'Authorization' = "AccessKey $accessKey"
        'nep-organization' = $organization
    }

try {
    $resp = Invoke-WebRequest -Uri $url `
        -Method Get `
        -ContentType 'application/json' `
        -Headers $headers `
        -SkipHeaderValidation
        
    $resp.Content
    return $resp
} catch [System.SystemException] {
    Write-Warning "Exception $_" 
    Write-Warning $_.ScriptStackTrace
} catch {
    Write-Error "Unknown exception! PANIC!!"
}
