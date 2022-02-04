import 'package:http/http.dart';
import 'package:crypto/crypto.dart';
import 'package:intl/intl.dart';
import 'dart:convert';

class BspHmacClient extends BaseClient {

  final String sharedKey;
  final String secretKey;

  BspHmacClient(this.sharedKey, this.secretKey);

  @override
  Future<StreamedResponse> send(BaseRequest request) async {
    final path = request.url.path +
        (request.url.query.isEmpty ? '' : '?${request.url.query}');

    // TODO: find a better way to force milliseconds and microseconds to zero
    final now = DateTime.now().toUtc();
    final nowZeroMs = DateTime(now.year, now.month, now.day, now.hour, now.minute, now.second, 0, 0);
    final isoDate = nowZeroMs.toIso8601String() + 'Z';

    final oneTimeSecret = secretKey + isoDate;

    var toSign = [request.method, path].join('\n');

    if (request.headers['Content-Type']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['Content-Type']!;
    }

    if (request.headers['Content-MD5']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['Content-MD5']!;
    }

    if (request.headers['nep-application-key']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['nep-application-key']!;
    }

    if (request.headers['nep-correlation-id']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['nep-correlation-id']!;
    }

    if (request.headers['nep-organization']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['nep-organization']!;
    }

    if (request.headers['nep-service-version']?.isNotEmpty ?? false) {
      toSign += '\n' + request.headers['nep-service-version']!;
    }

    final hmacSha512 = Hmac(sha512, utf8.encode(oneTimeSecret));

    final digest = hmacSha512.convert(utf8.encode(toSign));

    final signature = sharedKey + ':' + base64.encode(digest.bytes);

    request.headers['Authorization'] = 'AccessKey $signature';
    request.headers['Date'] = DateFormat('EEE, dd MMM y HH:mm:ss', 'en_US').format(nowZeroMs) + ' GMT';

    return request.send();
  }

}
