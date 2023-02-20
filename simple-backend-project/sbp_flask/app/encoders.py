import simplejson as json
from sqlalchemy.ext.declarative import DeclarativeMeta
from .models import Publisher, Game
from decimal import Decimal
from datetime import date

class PublisherEncoder(json.JSONEncoder):

    def default(self, o):
        # check if object `o` is of custom declared model instance
        if isinstance(o, Publisher):
            data = {}
            fields = o.__json__() if hasattr(o, '__json__') else dir(o)
            for field in [f for f in fields if not f.startswith('_') and
                          f not in ['metadata', 'query', 'query_class', 'registry', 'games']]:
                value = o.__getattribute__(field)
                try:
                    if isinstance(value, date):
                        data[field] = value.isoformat()
                    elif json.dumps(value):
                        data[field] = value
                except TypeError:
                    data[field] = None
            return data
        # rest of objects are handled by default JSONEncoder like 'Datetime', 
        # 'UUID', 'Markdown' and various others
        return json.JSONEncoder.default(self, o)


class GameEncoder(json.JSONEncoder):

    def default(self, o):
        # check if object `o` is of custom declared model instance
        if isinstance(o, Game):
            data = {}
            fields = o.__json__() if hasattr(o, '__json__') else dir(o)
            for field in [f for f in fields if not f.startswith('_') and
                          f not in ['metadata', 'query', 'query_class', 'registry', 'publisher_id']]:
                value = o.__getattribute__(field)
                try:
                    if field == 'publisher':
                        data[field] = {'id': value.id, 'name': value.name}
                    elif isinstance(value, date):
                        data[field] = value.isoformat()
                    elif json.dumps(value):
                        data[field] = value
                except TypeError:
                    data[field] = None
            return data
        # rest of objects are handled by default JSONEncoder like 'Datetime', 
        # 'UUID', 'Markdown' and various others
        return json.JSONEncoder.default(self, o)


class StoreGameEncoder(json.JSONEncoder):

    def default(self, o):
        # check if object `o` is of custom declared model instance
        if isinstance(o, Game):
            data = {}
            fields = o.__json__() if hasattr(o, '__json__') else dir(o)
            for field in [f for f in fields if not f.startswith('_') and
                          f not in ['metadata', 'query', 'query_class', 'registry', 'publisher_id', 'date_created', 'date_modified']]:
                value = o.__getattribute__(field)
                try:
                    if field == 'publisher':
                        data[field] = value.name
                    elif isinstance(value, date):
                        data[field] = value.isoformat()
                    elif json.dumps(value):
                        data[field] = value
                except TypeError:
                    data[field] = None
            return data
        # rest of objects are handled by default JSONEncoder like 'Datetime', 
        # 'UUID', 'Markdown' and various others
        return json.JSONEncoder.default(self, o)