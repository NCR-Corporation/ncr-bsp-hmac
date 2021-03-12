
from exampleGet import exampleGet
from examplePost import examplePost


def test_exampleGet():
    response = exampleGet()
    assert response["status"] == 200


def test_examplePost():
    response = examplePost()
    assert response["status"] == 200
