from .extensions import db

# Base model for other database tables to inherit
class Base(db.Model):

    __abstract__ = True

    id = db.Column(db.Integer, primary_key=True)
    date_created = db.Column(db.DateTime, default=db.func.current_timestamp())
    date_modified = db.Column(db.DateTime, default=db.func.current_timestamp(), onupdate=db.func.current_timestamp())

class Publisher(Base):

    __tablename__ = 'publisher'

    name = db.Column(db.String(128), nullable=False)
    games = db.relationship('Game', backref='publisher', lazy=True)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Publisher %r>' % self.name

class Game(Base):

    __tablename__ = 'game'

    name = db.Column(db.String(128), nullable=False)
    published_date = db.Column(db.Date, nullable=False)
    inventory_count = db.Column(db.Integer, nullable=False, default=0)
    publisher_id = db.Column(db.Integer, db.ForeignKey('publisher.id'), nullable=False)

    def __init__(self, name, published_date, publisher_id, inventory_count):
        self.name = name
        self.published_date = published_date
        self.publisher_id = publisher_id
        if inventory_count is not None:
            self.inventory_count = inventory_count