from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from .views import publisher_bp, inventory_bp, store_bp
from .extensions import db

def create_app(config_file='config.py'):
    app = Flask(__name__)
    app.config.from_pyfile(config_file)
    
    app.register_blueprint(publisher_bp)
    app.register_blueprint(inventory_bp)
    app.register_blueprint(store_bp)

    db.init_app(app)

    with app.app_context():
        db.create_all()

    return app