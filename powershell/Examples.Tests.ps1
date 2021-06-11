Describe "API validation" {
    BeforeAll {
        $getResponse = .\Example-Get.ps1
        $postResponse = .\Example-Post.ps1
    }

    It "GET response status code = '200'" {
        $getResponse.statusCode | Should -Be '200'
    }

    It "POST response status code = '200'" {
        $postResponse.statusCode | Should -Be '200'
    }
}
