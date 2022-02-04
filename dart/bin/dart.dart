import 'package:args/args.dart';
import 'package:uuid/uuid.dart' as uuid;

import 'bsp_hmac_client.dart';

const String _sharedKey = 'shared-key';
const String _secretKey = 'secret-key';
const String _baseUrl = "base-url";
const String _org = "org";

void main(List<String> arguments) {
  final parser = ArgParser()
    ..addOption(_sharedKey, mandatory: true)
    ..addOption(_secretKey, mandatory: true)
    ..addOption(_baseUrl, defaultsTo: 'https://api.ncr.com')
    ..addOption(_org, defaultsTo: 'dev-ex');

  final argResults = parser.parse(arguments);

  final client = BspHmacClient(argResults[_sharedKey], argResults[_secretKey]);

  exampleGet(client, argResults[_baseUrl], argResults[_org]);

  examplePost(client, argResults[_baseUrl], argResults[_org]);
}

void exampleGet(BspHmacClient client, String baseUrl, String org) {
  client.get(
      Uri.parse("$baseUrl/security/role-grants/user-grants/self/effective-roles"),
      headers: {
        'Content-Type': 'application/json',
        'nep-organization': org,
        'nep-correlation-id': 'ncr-bsp-hmac-dart-${uuid.Uuid}',
        'nep-application-key': '${uuid.Uuid}'
      })
      .then((resp) => print(resp.body));
}

void examplePost(BspHmacClient client, String baseUrl, String org) {
  client.post(
      Uri.parse("$baseUrl/security/authentication/login"),
      headers: { 'nep-organization': org })
      .then((resp) => print(resp.body));
}
