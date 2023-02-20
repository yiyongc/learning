from django.shortcuts import render
from .models import Game, Publisher
from .serializers import GameSerializer, PublisherSerializer, GameProductSerializer
from rest_framework.views import APIView
from rest_framework.generics import RetrieveUpdateDestroyAPIView, ListCreateAPIView, UpdateAPIView, ListAPIView
from rest_framework.mixins import DestroyModelMixin
from rest_framework.response import Response
from rest_framework import status

class PublisherGenericAPIView(ListCreateAPIView):
    serializer_class = PublisherSerializer
    queryset = Publisher.objects.all()

    def get(self, request):
        return self.list(request)
    
    def post(self, request):
        return self.create(request)    

class PublisherDetailGenericAPIView(RetrieveUpdateDestroyAPIView):
    serializer_class = PublisherSerializer
    queryset = Publisher.objects.all()

    def get(self, request, pk):
        return self.retrieve(request)

    def put(self, request, pk):
        return self.update(request, pk)

    def delete(self, request, pk):
        Game.objects.filter(publisher_id=pk).delete()
        return self.destroy(request, pk)

class InventoryGenericAPIView(ListCreateAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()

    def get(self, request):
        return self.list(request)

    def post(self, request):
        return self.create(request)

class InventoryProductGenericAPIView(RetrieveUpdateDestroyAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()

    def get(self, request, pk):
        return self.retrieve(request)

    def put(self, request, pk):
        return self.update(request, pk)

    def delete(self, request, pk):
        return self.destroy(request, pk)

class StoreProductsGenericAPIView(ListAPIView):
    serializer_class = GameProductSerializer
    queryset = Game.objects.all()

    def get(self, request):
        return self.list(request)

class StoreProductsFilterGenericAPIView(APIView):
    def get(self, request, publisher_id):
        games = Game.objects.filter(publisher_id = publisher_id)
        serializer = GameProductSerializer(games, many = True)
        return Response(serializer.data)

class StoreInteractionGenericAPIView(APIView):
    def post(self, request, pk):
        try:
            game = Game.objects.get(id = pk)
        except Game.DoesNotExist:
            return Response(status = status.HTTP_404_NOT_FOUND)
        if game.inventory_count == 0:
            return Response({'error': 'NO_STOCK_LEFT', 'description': 'There is no available stock for the product'}, status = status.HTTP_400_BAD_REQUEST)
        game.inventory_count -= 1
        game.save(update_fields = ['inventory_count'])
        return Response(GameProductSerializer(game).data)

