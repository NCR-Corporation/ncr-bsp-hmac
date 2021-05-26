namespace SendPost{
    public class ContentModel{
        public string token {get; set;}
        public int maxIdleTime {get; set;}
        public int maxSessionTime {get; set;}
        public int remainingTime {get; set;}
        public string[] authorities {get; set;}
        public string[] consentScopes {get; set;}
        public bool credentialExpired {get; set;}
        public string organizationName {get; set;}
        public string username {get; set;}
        public string[] authenticationMethods {get; set;}
        public int exchangesCompleted {get; set;}
        public string[] customClaims {get; set;}
        public bool singleUse {get; set;}
    }
}