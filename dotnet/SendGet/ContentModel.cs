namespace SendGet{
    public class ContentModel{
        public bool lastPage{get; set;}
        public int pageNumber{get; set;}
        public int totalPages{get; set;}
        public int totalResults{get; set;}
        public PageContent[] pageContent{get; set;}
    }
}