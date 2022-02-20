using System;
using System.Threading.Tasks;
using Xunit;

namespace SendHMACTest
{
    public class UnitTest1
    {
        [Fact]
        public async Task Test1()
        {
            var result = await SendGet.Program.CallGet(Environment.GetEnvironmentVariable("SECRET_KEY"),
                Environment.GetEnvironmentVariable("SHARED_KEY"), Environment.GetEnvironmentVariable("ORGANIZATION"));
            Assert.Equal(200, result);
        }
    }

    public class UnitTest2
    {
        [Fact]
        public async Task Test2()
        {
            var result = await SendPost.Program.CallPost(Environment.GetEnvironmentVariable("SECRET_KEY"),
                Environment.GetEnvironmentVariable("SHARED_KEY"), Environment.GetEnvironmentVariable("ORGANIZATION"));
            Assert.Equal(200, result);
        }
    }
}