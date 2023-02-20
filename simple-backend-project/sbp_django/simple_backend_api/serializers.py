from rest_framework import serializers
from .models import Game, Publisher

class PublisherSerializer(serializers.ModelSerializer):
    class Meta:
        model = Publisher
        fields = '__all__'

class GameSerializer(serializers.ModelSerializer):
    class Meta:
        model = Game
        fields = ['id', 'name', 'pub_date', 'inventory_count', 'publisher']

    def to_representation(self, instance):
        self.fields['publisher'] = PublisherSerializer(read_only = True)
        return super(GameSerializer, self).to_representation(instance)

class GameProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Game
        fields = ['id', 'name', 'pub_date', 'inventory_count', 'publisher']

    def to_representation(self, instance):
        self.fields['publisher'] = PublisherSerializer(read_only = True)
        return super(GameProductSerializer, self).to_representation(instance)