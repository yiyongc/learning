from flask import Blueprint, request, abort, Response, jsonify
import simplejson as json
from http import HTTPStatus

from .extensions import db
from .models import Publisher, Game
from .encoders import PublisherEncoder, GameEncoder, StoreGameEncoder


'''
Code blocks below are for publisher blueprint
'''
publisher_bp = Blueprint('publishers', __name__, url_prefix='/publishers')
publisher_bp.json_encoder = PublisherEncoder

@publisher_bp.route('', methods=['GET', 'POST'])
def publishers_list():
    if request.method == 'POST':
        if not request.json or not 'name' in request.json:
            abort(400)
        publisher = Publisher(request.json['name'])
        db.session.add(publisher)
        db.session.commit()
        return jsonify(publisher), HTTPStatus.CREATED
    elif request.method == 'GET':
        publishers = Publisher.query.all()
        return jsonify(publishers)

@publisher_bp.route('/<id>', methods=['GET', 'PUT', 'DELETE'])
def publisher_detail(id):
    publisher = Publisher.query.get(id)
    if publisher is None:
        abort(404)

    if request.method == 'GET':
        return jsonify(publisher)
    elif request.method == 'PUT':
        if not request.json or not 'name' in request.json:
            abort(400)
        publisher.name = request.json['name']
        db.session.commit()
        return jsonify(publisher)
    elif request.method == 'DELETE':
        Game.query.filter_by(publisher_id=id).delete()
        db.session.delete(publisher)
        db.session.commit()
        return '', HTTPStatus.NO_CONTENT


'''
Code blocks below are for inventory blueprint
'''
inventory_bp = Blueprint('inventory', __name__, url_prefix='/inventory')
inventory_bp.json_encoder = GameEncoder

@inventory_bp.route('', methods=['GET', 'POST'])
def inventory_game_list():
    if request.method == 'POST':
        if (not request.json or not 'name' in request.json 
            or not 'published_date' in request.json or not 'publisher_id' in request.json):
            abort(400)
        game_name = request.json['name']
        game_pub_date = request.json['published_date']
        game_pub_id = request.json['publisher_id']
        game_inv_count = request.json['inventory_count'] if 'inventory_count' in request.json else None
        if Publisher.query.get(game_pub_id) is None:
            abort(400)
        game = Game(game_name, game_pub_date, game_pub_id, game_inv_count)
        db.session.add(game)
        db.session.commit()
        return jsonify(game), HTTPStatus.CREATED

    elif request.method == 'GET':
        games = Game.query.all()
        return jsonify(games)

@inventory_bp.route('/<id>', methods=['GET', 'PUT', 'DELETE'])
def inventory_game_detail(id):
    game = Game.query.get(id)
    if game is None:
        abort(404)

    if request.method == 'GET':
        return jsonify(game)
    elif request.method == 'PUT':
        if (not request.json or not 'name' in request.json or not 'published_date' in request.json 
            or not 'publisher_id' in request.json or not 'inventory_count' in request.json):
            abort(400)
        game_name = request.json['name']
        game_pub_date = request.json['published_date']
        game_pub_id = request.json['publisher_id']
        game_inv_count = request.json['inventory_count']

        if Publisher.query.get(game_pub_id) is None:
            abort(400)

        game.name = game_name
        game.published_date = game_pub_date
        game.publisher_id = game_pub_id
        game.inventory_count = game_inv_count

        db.session.commit()
        return jsonify(game)
    elif request.method == 'DELETE':
        db.session.delete(game)
        db.session.commit()
        return '', HTTPStatus.NO_CONTENT


'''
Code blocks below are for store blueprint
'''
store_bp = Blueprint('store', __name__, url_prefix='/store')
store_bp.json_encoder = StoreGameEncoder

@store_bp.route('', methods=['GET'])
def view_store():
    games = Game.query.all()
    return jsonify(games)

@store_bp.route('/find/publisher/<publisher_id>', methods=['GET'])
def filter_store_by_publisher(publisher_id):
    games = Game.query.filter_by(publisher_id=publisher_id).all()
    return jsonify(games)

@store_bp.route('/purchase/<game_id>', methods=['POST'])
def purchase_game(game_id):
    game = Game.query.get(game_id)
    if game is None:
        abort(404)
    if game.inventory_count == 0:
        return {'error': 'NO_STOCK_LEFT', 'description': 'There is no available stock for the product'}, 400
    game.inventory_count -= 1
    db.session.commit()
    return jsonify(game)