from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from rest_framework.parsers import JSONParser
from .models import Game
from .serializers import GameSerializer
# from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status, generics, mixins
from rest_framework.views import APIView
from rest_framework import viewsets
from django.shortcuts import get_object_or_404

# Create your views here.

class GamesViewSet(viewsets.ViewSet):

    def list(self, request):
        queryset = Game.objects.all()
        serializer = GameSerializer(queryset, many=True)
        return Response(serializer.data)

    def create(self, request):
        serializer = GameSerializer(data = request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status = status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
        
    def retrieve(self, request, pk=None):
        queryset = Game.objects.all()
        game = get_object_or_404(queryset, pk=pk)
        serializer = GameSerializer(game)
        return Response(serializer.data)

    def update(self, request, pk=None):
        game = Game.objects.get(pk=pk)
        serializer = GameSerializer(game, data = request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
    

class GamesGenericAPIView(generics.ListCreateAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()

    def get(self, request):
        return self.list(request)
    
    def post(self, request):
        return self.create(request)
    
class GameDetailGenericAPIView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()
    lookup_field = 'id'

    def get(self, request, id):
        return self.retrieve(request)

    def put(self, request, id):
        return self.update(request, id)
    
    def delete(self, request, id):
        return self.destroy(request, id)

class GamesAPIView(APIView): 

    def get(self, request):
        games = Game.objects.all()
        serializer = GameSerializer(games, many = True)
        return Response(serializer.data)

    def post(self, request):
        serializer = GameSerializer(data = request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status = status.HTTP_201_CREATED)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)


class GameDetailAPIView(APIView):

    def get_object(self, id):
        try:
            return Game.objects.get(id = id)
        except Game.DoesNotExist:
            return None
    
    def get(self, request, id):
        game = self.get_object(id)
        if game is None:
            return Response(status = status.HTTP_404_NOT_FOUND)
        serializer = GameSerializer(game)
        return Response(serializer.data)
    
    def put(self, request, id):
        game = self.get_object(id)
        if game is None:
            return Response(status = status.HTTP_404_NOT_FOUND)
        serializer = GameSerializer(game, data = request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
    
    def delete(self, request, id):
        game = self.get_object(id)
        if game is None:
            return Response(status = status.HTTP_404_NOT_FOUND)
        game.delete()
        return Response(status = status.HTTP_204_NO_CONTENT)


# @api_view(['GET', 'POST'])
# def game_list(request):
#     if request.method == 'GET':
#         games = Game.objects.all()
#         serializer = GameSerializer(games, many = True)
#         return Response(serializer.data)

#     elif request.method == 'POST':
#         serializer = GameSerializer(data = request.data)

#         if serializer.is_valid():
#             serializer.save()
#             return Response(serializer.data, status = status.HTTP_201_CREATED)
#         return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

# @api_view(['GET', 'PUT', 'DELETE'])
# def game_detail(request, pk):
#     try:
#         game = Game.objects.get(pk = pk)
#     except Game.DoesNotExist:
#         return Response(status = status.HTTP_404_NOT_FOUND)
    
#     if request.method == 'GET':
#         serializer = GameSerializer(game)
#         return Response(serializer.data)

#     elif request.method == 'PUT':
#         serializer = GameSerializer(game, data = request.data)
#         if serializer.is_valid():
#             serializer.save()
#             return Response(serializer.data)
#         return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)

#     elif request.method == 'DELETE':
#         game.delete()
#         return Response(status = status.HTTP_204_NO_CONTENT)