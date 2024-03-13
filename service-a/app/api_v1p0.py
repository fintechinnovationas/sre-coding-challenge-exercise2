from flask import Blueprint, jsonify
import requests

blueprint = Blueprint(name='api_v1p0', import_name=__name__, url_prefix="/api/v1", template_folder='templates')


@blueprint.route('/hey-ho', methods=['GET'])
def get_hey_ho():
    r = requests.get('http://service-b.default:8888/api/v1/get-hey-ho')

    print("Response Status Code: {}".format(r.status_code))

    if r.status_code == 200:
        return jsonify(r.json())
    else:
        response = {
            "error": "Service B doesn't work!",
            "upstream_error": r.text
        }
        return jsonify(response), 500

