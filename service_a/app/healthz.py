from flask import Blueprint, jsonify, make_response
import requests

blueprint = Blueprint(name='healthz', import_name=__name__, template_folder='templates')


@blueprint.route('/healthz', methods=['GET'])
def healthz():
    return make_response("OK")

