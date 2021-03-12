import os
from exampleGet import exampleGet
from examplePost import examplePost


def test_exampleGet():
    secretKey = os.getenv('SECRET_KEY')
    sharedKey = os.getenv('SHARED_KEY')
    organization = os.getenv('ORGANIZATION')
    response = exampleGet(secretKey, sharedKey, organization)
    assert response["status"] == 200


def test_examplePost():
    secretKey = os.getenv('SECRET_KEY')
    sharedKey = os.getenv('SHARED_KEY')
    organization = os.getenv('ORGANIZATION')
    response = exampleGet(secretKey, sharedKey, organization)
    assert response["status"] == 200
