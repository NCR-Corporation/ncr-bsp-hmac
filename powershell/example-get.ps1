Import-Module "./hmac.psm1"

$sharedKey = "INSERT SHARED KEY"
$secretKey = "INSERT SECRET KEY"
$organization = "INSERT ORGANIZATION"

$url = "https://api.ncr.com/site/sites/find-nearby/88.05,46.25?radius=10000"
$now = Get-Date

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
        -Headers $headers
        
    Write-Output $resp.Content
} catch [System.SystemException] {
    Write-Warning "Exception $_" 
    Write-Warning $_.ScriptStackTrace
} catch {
    Write-Error "Unknown exception! PANIC!!"
}
