from django.urls import path, include
# from .views import game_list, game_detail
# from .views-learning import GamesAPIView, GameDetailAPIView
# from .views-learning import GamesGenericAPIView, GameDetailGenericAPIView
# from .views-learning import GamesViewSet
# from rest_framework.routers import DefaultRouter
from .views import PublisherGenericAPIView, PublisherDetailGenericAPIView, InventoryGenericAPIView, InventoryProductGenericAPIView, StoreProductsGenericAPIView, StoreProductsFilterGenericAPIView, StoreInteractionGenericAPIView

# router = DefaultRouter()

# router.register('games', GamesViewSet, basename='games')

# urlpatterns = [
#     # path('games', game_list),
#     path('games', GamesAPIView.as_view()),
#     # path('games/<int:pk>', game_detail),
#     path('games/<int:id>', GameDetailAPIView.as_view()),
#     path('generic/games', GamesGenericAPIView.as_view()),
#     path('generic/games/<int:id>', GameDetailGenericAPIView.as_view()),
#     path('viewset/', include(router.urls)),
#     path('viewset/<int:pk>', include(router.urls)),
# ]

urlpatterns = [
    path('publishers', PublisherGenericAPIView.as_view()),
    path('publishers/<int:pk>', PublisherDetailGenericAPIView.as_view()),
    path('inventory', InventoryGenericAPIView.as_view()),
    path('inventory/<int:pk>', InventoryProductGenericAPIView.as_view()),
    path('store', StoreProductsGenericAPIView.as_view()),
    path('store/find/publisher/<int:publisher_id>', StoreProductsFilterGenericAPIView.as_view()),
    path('store/purchase/<int:pk>', StoreInteractionGenericAPIView.as_view()),
]