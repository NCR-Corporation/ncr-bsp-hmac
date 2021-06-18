using System;
using Xunit;
using SendGet;
using SendPost;

namespace SendHMACTest
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {
            var result = SendGet.Program.callGet(Environment.GetEnvironmentVariable("SECRET_KEY"), Environment.GetEnvironmentVariable("SHARED_KEY"), Environment.GetEnvironmentVariable("ORGANIZATION"));
            Assert.Equal(200, result);
        }
    }
    public class UnitTest2
    {
        [Fact]
        public void Test2()
        {
            var result = SendPost.Program.callPost(Environment.GetEnvironmentVariable("SECRET_KEY"), Environment.GetEnvironmentVariable("SHARED_KEY"), Environment.GetEnvironmentVariable("ORGANIZATION"));
            Assert.Equal(200, result);
        }
    }
}
